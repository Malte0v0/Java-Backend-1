package org.example.javabackend1.Booking;

import jakarta.transaction.Transactional;
import org.example.javabackend1.Customer.CustomerCreateDTO;
import org.example.javabackend1.Customer.CustomerService;
import org.example.javabackend1.Exceptions.BookingException;
import org.example.javabackend1.Room.RoomCreateDTO;
import org.example.javabackend1.Room.RoomService;
import org.example.javabackend1.Room.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.javabackend1.Booking.BookingRepository;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoomService roomService;

    private Long customerId;
    private Long roomId;

    @BeforeEach
    void setUp() {
        CustomerCreateDTO customerDTO = new CustomerCreateDTO();
        customerDTO.setFirstName("Test");
        customerDTO.setLastName("User");
        customerDTO.setEmail("test@test.com");
        customerDTO.setPhone("0700000000");
        customerId = customerService.create(customerDTO).getId();

        RoomCreateDTO roomDTO = new RoomCreateDTO();
        roomDTO.setRoomType(RoomType.DOUBLE);
        roomDTO.setExtraBeds(1);
        roomId = roomService.create(roomDTO).getId();
    }

    private BookingCreateDTO makeDTO(String checkIn, String checkOut, int guests) {
        BookingCreateDTO dto = new BookingCreateDTO();
        dto.setCustomerId(customerId);
        dto.setRoomId(roomId);
        dto.setCheckInDate(checkIn);
        dto.setCheckOutDate(checkOut);
        dto.setNumberOfGuests(guests);

        return dto;
    }

    @Test
    void create_shouldSaveAndReturnBooking() {
        BookingCreateDTO dto = makeDTO("2025-08-01", "2025-08-05", 2);

        org.example.javabackend1.Booking.BookingResponseDTO result = bookingService.create(dto);

        assertNotNull(result.getId());
        assertEquals(customerId, result.getCustomerId());
        assertEquals(roomId, result.getRoomId());
        assertEquals(2, result.getNumberOfGuests());
    }

    @Test
    void create_shouldThrow_whenCustomerNotFound() {
        BookingCreateDTO dto = makeDTO("2025-08-01", "2025-08-05", 2);
        dto.setCustomerId(999L);

        assertThrows(BookingException.class, () -> bookingService.create(dto));
    }

    @Test
    void create_shouldThrow_whenRoomNotFound() {
        BookingCreateDTO dto = makeDTO("2025-08-01", "2025-08-05", 2);
        dto.setRoomId(999L);

        assertThrows(BookingException.class, () -> bookingService.create(dto));
    }

    @Test
    void findById_shouldReturnBooking() {
        BookingResponseDTO created = bookingService.create(makeDTO("2025-08-01", "2025-08-05", 2));

        BookingResponseDTO found = bookingService.findById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("test@test.com", found.getCustomerEmail());
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        assertThrows(BookingException.class, () -> bookingService.findById(999L));
    }

    @Test
    void findAll_shouldReturnAllBookings() {
        BookingResponseDTO booking =
                bookingService.create(makeDTO("2025-08-01", "2025-08-05", 2));

        BookingResponseDTO found =
                bookingService.findById(booking.getId());

        assertNotNull(found);
        assertEquals(booking.getId(), found.getId());
    }

    @Test
    void update_shouldChangeBookingFields() {
        BookingResponseDTO created = bookingService.create(makeDTO("2025-08-01", "2025-08-05", 2));

        BookingCreateDTO updateDTO = makeDTO("2025-10-01", "2025-10-10", 3);
        bookingService.update(created.getId(), updateDTO);

        BookingResponseDTO updated = bookingService.findById(created.getId());
        assertEquals(3, updated.getNumberOfGuests());
        assertEquals("2025-10-01", updated.getCheckInDate().toString());
    }

    @Test
    void update_shouldThrow_whenBookingNotFound() {
        assertThrows(BookingException.class, () ->
                bookingService.update(999L, makeDTO("2025-08-01", "2025-08-05", 2))
        );
    }

    @Test
    void delete_shouldRemoveBooking() {
        BookingResponseDTO created = bookingService.create(makeDTO("2025-08-01", "2025-08-05", 2));

        bookingService.delete(created.getId());

        assertThrows(BookingException.class, () -> bookingService.findById(created.getId()));
    }

    @Test
    void delete_shouldThrow_whenBookingNotFound() {
        assertThrows(BookingException.class, () -> bookingService.delete(999L));
    }


}