package org.example.javabackend1.Booking;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingResponseDTO> getAllBookings() {
        return this.bookingService.findAll();
    }

    @GetMapping("/{id}")
    public BookingResponseDTO getBookingById(@PathVariable Long id) {
        return this.bookingService.findById(id);
    }

    @PostMapping
    public BookingResponseDTO createBooking(@RequestBody BookingCreateDTO dto) {
        return this.bookingService.create(dto);
    }

    @PutMapping("/{id}")
    public BookingResponseDTO updateBookingById(@PathVariable Long id,
                                                @RequestBody BookingCreateDTO dto) {
        return this.bookingService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBookingById(@PathVariable Long id) {
        this.bookingService.delete(id);
    }
}
