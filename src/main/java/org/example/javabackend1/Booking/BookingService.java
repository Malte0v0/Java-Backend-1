package org.example.javabackend1.Booking;

import org.example.javabackend1.Customer.CustomerEntity;
import org.example.javabackend1.Customer.CustomerRepository;
import org.example.javabackend1.Exceptions.BookingException;
import org.example.javabackend1.Exceptions.CustomerException;
import org.example.javabackend1.Room.RoomEntity;
import org.example.javabackend1.Room.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository,
                          CustomerRepository customerRepository,
                          RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }

    public void update(Long id, BookingCreateDTO createDTO) {
        CustomerEntity customer = customerRepository.findById(createDTO.getCustomerId())
                .orElseThrow(() -> new BookingException("Customer not found"));
        RoomEntity room = roomRepository.findById(createDTO.getRoomId())
                .orElseThrow(() -> new BookingException("Room not found"));
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(
                        () -> new BookingException("Booking with id " + String.valueOf(id) + " does not exist")
                );

        createBookingResponseDTO(createDTO, booking, customer, room);
    }

    private BookingResponseDTO createBookingResponseDTO(BookingCreateDTO createDTO,
                                                        BookingEntity booking,
                                                        CustomerEntity customerEntity,
                                                        RoomEntity roomEntity) {
        booking.setCheckInDate(LocalDate.parse(createDTO.getCheckInDate()));
        booking.setCheckOutDate(LocalDate.parse(createDTO.getCheckOutDate()));
        booking.setCustomer(customerEntity);
        booking.setNumberOfGuests(createDTO.getNumberOfGuests());
        booking.setRoom(roomEntity);
        booking.setStatus(createDTO.getStatus());

        BookingEntity saved = bookingRepository.save(booking);

        return toResponse(saved);
    }

    public void delete(Long id) {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(
                        () -> new BookingException("Booking with id " + String.valueOf(id) + " does not exist")
                );

        bookingRepository.delete(booking);
    }

    public BookingResponseDTO findById(Long id) {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(
                        () -> new BookingException("Booking with id " + String.valueOf(id) + " does not exist")
                );

        return toResponse(booking);
    }

    public List<BookingResponseDTO> findAll() {
        List<BookingEntity> bookings = bookingRepository.findAll();

        List<BookingResponseDTO> responseBookings = new ArrayList<>();
        for (BookingEntity booking : bookings) {
            responseBookings.add(toResponse(booking));
        }

        return responseBookings;
    }

    public BookingResponseDTO create(BookingCreateDTO createDTO) {
        CustomerEntity customer = customerRepository.findById(createDTO.getCustomerId())
                .orElseThrow(() -> new BookingException("Customer not found"));
        RoomEntity room = roomRepository.findById(createDTO.getRoomId())
                .orElseThrow(() -> new BookingException("Room not found"));

        // Skapa en booking
        BookingEntity booking = new BookingEntity();

        return createBookingResponseDTO(createDTO, booking, customer, room);
    }

    public BookingResponseDTO toResponse(BookingEntity booking) {
        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(booking.getId());
        response.setCustomerId(booking.getCustomer().getId());
        response.setCustomerEmail(booking.getCustomer().getEmail());
        response.setCustomerFirstName(booking.getCustomer().getFirstName());
        response.setCustomerLastName(booking.getCustomer().getLastName());
        response.setRoomId(booking.getRoom().getRoomId());
        response.setRoomType(booking.getRoom().getRoomType().toString());
        response.setExtraBeds(booking.getRoom().getExtraBeds());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setNumberOfGuests(booking.getNumberOfGuests());
        response.setStatus(booking.getStatus());
        return response;
    }
}
