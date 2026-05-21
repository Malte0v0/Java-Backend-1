package org.example.javabackend1.Booking;

import org.example.javabackend1.Booking.BookingCreateDTO;
import org.example.javabackend1.Booking.BookingDTO;
import org.example.javabackend1.Booking.BookingEntity;
import org.example.javabackend1.Booking.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingDTO> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public BookingDTO findById(Long bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return toDTO(booking);
    }

    public BookingCreateDTO findCreateDtoById(Long bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return toCreateDTO(booking);
    }

    public BookingDTO create(BookingCreateDTO dto) {
        BookingEntity booking = toEntity(dto);
        BookingEntity savedBooking = bookingRepository.save(booking);
        return toDTO(savedBooking);
    }

    public BookingDTO update(Long bookingId, BookingCreateDTO dto) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingEntity savedBooking = bookingRepository.save(booking);
        return toDTO(savedBooking);
    }

    public void delete(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    private BookingDTO toDTO(BookingEntity booking) {
        BookingDTO dto = new BookingDTO();
        return dto;
    }

    private BookingCreateDTO toCreateDTO(BookingEntity booking) {
        BookingCreateDTO dto = new BookingCreateDTO();
        return dto;
    }

    private BookingEntity toEntity(BookingCreateDTO dto) {
        BookingEntity booking = new BookingEntity();
        return booking;
    }
}
