package org.example.javabackend1.Booking;

import org.springframework.stereotype.Service;

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

        // Spara till databasen, få en booking med id som return value
        BookingEntity saved = bookingRepository.save(booking);

        // Returnera ett response dto
        return toResponse(saved);
    }

    public BookingResponseDTO toResponse(BookingEntity booking) {
        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(booking.getId());

        return response;
    }
}
