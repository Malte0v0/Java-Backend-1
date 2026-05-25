package org.example.javabackend1.Booking;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String mainMenu() {
        return "booking";
    }

    @GetMapping("/list")
    public String getAllBookings(Model model) {
        model.addAttribute("bookings", this.bookingService.findAll());
        return "booking-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bookingForm", new BookingCreateDTO());
        return "booking-new";
    }

    @PostMapping("/new")
    public String createBooking(@ModelAttribute BookingCreateDTO dto) {
        this.bookingService.create(dto);
        return "redirect:/bookings/list";
    }

    @GetMapping("/edit")
    public String showEditSearch() {
        return "booking-edit";
    }

    @GetMapping("/edit/find")
    public String findBookingToEdit(@RequestParam Long id, Model model) {
        model.addAttribute("booking", this.bookingService.findById(id));
        return "booking-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateBookingById(@PathVariable Long id,
                                    @ModelAttribute BookingCreateDTO dto) {
        bookingService.update(id, dto);
        return "redirect:/bookings/list";
    }

    @GetMapping("/delete")
    public String showDeleteSearch() {
        return "booking-delete";
    }

    @GetMapping("/delete/find")
    public String findBookingToDelete(@RequestParam Long id, Model model) {
        model.addAttribute("booking", this.bookingService.findById(id));
        return "booking-delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteBookingById(@PathVariable Long id) {
        this.bookingService.delete(id);
        return "redirect:/bookings/list";
    }
}
