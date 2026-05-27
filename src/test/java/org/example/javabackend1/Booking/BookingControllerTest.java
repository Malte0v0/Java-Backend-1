package org.example.javabackend1.Booking;

import org.example.javabackend1.Customer.CustomerEntity;
import org.example.javabackend1.Customer.CustomerRepository;
import org.example.javabackend1.Room.RoomEntity;
import org.example.javabackend1.Room.RoomRepository;
import org.example.javabackend1.Room.RoomType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingController bookingController;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Long savedBookingId;
    private Long customerId;
    private Long roomId;

    @BeforeEach
    void setUp() {
        CustomerEntity customer = customerRepository.save(
                new CustomerEntity(null, "Bert", "Svensson", "bert@gmail.com", "0701234567")
        );
        customerId = customer.getId();

        RoomEntity room = roomRepository.save(
                new RoomEntity(null, RoomType.DOUBLE, 0)
        );
        roomId = room.getRoomId();

        BookingEntity booking = bookingRepository.save(new BookingEntity(
                null,
                customer,
                room,
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 9, 5),
                2
        ));
        savedBookingId = booking.getId();
    }

    @AfterEach
    void tearDown() {
        bookingRepository.deleteAll();
        roomRepository.deleteAll();
        customerRepository.deleteAll();
    }

    // --- Context ---

    @Test
    void contextLoads() {
        assertThat(bookingController).isNotNull();
    }

    // --- GET /bookings ---

    @Test
    void mainMenu_returnsBookingView() throws Exception {
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookings/booking"));
    }

    // --- GET /bookings/list ---

    @Test
    void getAllBookings_returnsListViewWithBookings() throws Exception {
        mockMvc.perform(get("/bookings/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookings/list"))
                .andExpect(model().attributeExists("bookings"))
                .andExpect(model().attribute("bookings", hasSize(1)));
    }

    // --- GET /bookings/new ---

    @Test
    void showCreateForm_returnsNewViewWithAllAttributes() throws Exception {
        mockMvc.perform(get("/bookings/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookings/new"))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attributeExists("rooms"));
    }

    // --- POST /bookings/new ---

    @Test
    void createBooking_savesAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/bookings/new")
                        .param("customerId",     customerId.toString())
                        .param("roomId",         roomId.toString())
                        .param("checkInDate",    "2025-10-01")
                        .param("checkOutDate",   "2025-10-04")
                        .param("numberOfGuests", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings/list"));

        assertThat(bookingRepository.findAll()).hasSize(2);
    }

    // --- GET /bookings/edit/find ---

    @Test
    void findBookingToEdit_returnsEditViewWithBookingCustomersAndRooms() throws Exception {
        mockMvc.perform(get("/bookings/edit/find")
                        .param("id", savedBookingId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("bookings/edit"))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attributeExists("rooms"));
    }

    // --- GET /bookings/{id}/edit ---

    @Test
    void showEditPage_returnsEditViewWithBooking() throws Exception {
        mockMvc.perform(get("/bookings/{id}/edit", savedBookingId))
                .andExpect(status().isOk())
                .andExpect(view().name("bookings/edit"))
                .andExpect(model().attributeExists("booking"));
    }

    // --- POST /bookings/{id}/edit ---

    @Test
    void updateBooking_updatesAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/bookings/{id}/edit", savedBookingId)
                        .param("customerId",     customerId.toString())
                        .param("roomId",         roomId.toString())
                        .param("checkInDate",    "2025-09-01")
                        .param("checkOutDate",   "2025-09-10")  // extended checkout
                        .param("numberOfGuests", "2")
                        .param("status",         "CONFIRMED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings/list"));

        BookingEntity updated = bookingRepository.findById(savedBookingId).orElseThrow();
        assertThat(updated.getCheckOutDate()).isEqualTo(LocalDate.of(2025, 9, 10));
    }

    // --- GET /bookings/delete ---

    @Test
    void showDeleteSearch_returnsDeleteView() throws Exception {
        mockMvc.perform(get("/bookings/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookings/delete"));
    }

    // --- GET /bookings/delete/find ---

    @Test
    void findBookingToDelete_returnsDeleteViewWithBooking() throws Exception {
        mockMvc.perform(get("/bookings/delete/find")
                        .param("id", savedBookingId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("bookings/delete"))
                .andExpect(model().attributeExists("booking"));
    }

    // --- POST /bookings/{id}/delete ---

    @Test
    void deleteBooking_deletesAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/bookings/{id}/delete", savedBookingId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings/list"));

        assertThat(bookingRepository.findById(savedBookingId)).isEmpty();
        assertThat(bookingRepository.findAll()).isEmpty();
    }
}