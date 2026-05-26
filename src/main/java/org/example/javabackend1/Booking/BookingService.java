package org.example.javabackend1.Booking;

import org.example.javabackend1.Exceptions.BookingException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingResponseDTO update(Long id, BookingCreateDTO createDTO) {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(
                        () -> new BookingException("Booking with id " + String.valueOf(id) + " does not exist")
                );

        return createBookingResponseDTO(createDTO, booking);
    }

    private BookingResponseDTO createBookingResponseDTO(BookingCreateDTO createDTO, BookingEntity booking) {
        booking.setCheckInDate(LocalDate.parse(createDTO.getCheckInDate()));
        booking.setCheckOutDate(LocalDate.parse(createDTO.getCheckOutDate()));
        booking.setCustomer(createDTO.getCustomer());
        booking.setNumberOfGuests(createDTO.getNumberOfGuests());
        booking.setRoom(createDTO.getRoom());
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
        // Skapa en booking
        BookingEntity booking = new BookingEntity();

        return createBookingResponseDTO(createDTO, booking);
    }

    public BookingResponseDTO toResponse(BookingEntity booking) {
        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(booking.getId());

        return response;
    }
}
