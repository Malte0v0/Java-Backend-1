package org.example.javabackend1.Booking;

import org.example.javabackend1.Customer.CustomerService;
import org.example.javabackend1.Exceptions.BookingException;
import org.example.javabackend1.Room.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final CustomerService customerService;
    private final RoomService roomService;

    public BookingController(BookingService bookingService,
                             CustomerService customerService,
                             RoomService roomService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.roomService = roomService;
    }

    @GetMapping
    public String mainMenu() {
        return "bookings/booking";
    }

    // --- GET /bookings/list ---
    @GetMapping("/list")
    public String getAllBookings(Model model) {
        model.addAttribute("bookings", this.bookingService.findAll());
        return "bookings/list";
    }



    //              ------------------------------ NEW ------------------------------
    // ↓↓↓↓↓ SKAPA EN NY BOOKING ↓↓↓↓↓
    // --- GET /bookings/new ---
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("booking", new BookingCreateDTO());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "bookings/new";
    }

    // --- POST /bookings/new ---
    @PostMapping("/new")
    public String createBooking(@ModelAttribute("booking") BookingCreateDTO dto,
                                Model model) {
        try {
            this.bookingService.create(dto);
            return "redirect:/bookings/list";
    }catch (BookingException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("booking", dto);
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("rooms", roomService.findAll());
            return "bookings/new";
        }}
    // ↑↑↑↑↑ SKAPA EN NY BOOKING ↑↑↑↑↑



    //              ------------------------------ EDIT ------------------------------
    // --- GET /bookings/edit ---
    @GetMapping("/edit")
    public String showEditSearch() {
        return "bookings/edit";
    }

    // ↓↓↓↓↓ HITTA EN BOOKING VIA ID ↓↓↓↓↓
    // --- GET /bookings/edit/find ---
    @GetMapping("/edit/find")
    public String findBookingToEdit(@RequestParam Long id, Model model) {
        model.addAttribute("booking", this.bookingService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "bookings/edit";
    }
    // ↑↑↑↑↑ HITTA EN BOOKING VIA ID ↑↑↑↑↑

    // ↓↓↓↓↓ REDIGERA EN BOOKING ↓↓↓↓↓
    // --- GET /bookings/{id}/edit ---
    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("booking", this.bookingService.findById(id));
        return "bookings/edit";
    }

    // --- POST /bookings/{id}/edit ---
    @PostMapping("/{id}/edit")
    public String updateBookingById(@PathVariable Long id,
                                    @ModelAttribute BookingCreateDTO dto) {
        bookingService.update(id, dto);
        return "redirect:/bookings/list";
    }
    // ↑↑↑↑↑ REDIGERA EN BOOKING ↑↑↑↑↑



    //              ------------------------------ DELETE ------------------------------
    // --- GET /bookings/delete ---
    @GetMapping("/delete")
    public String showDeleteSearch() {
        return "bookings/delete";
    }

    // ↓↓↓↓↓ RADERA EN BOOKING ↓↓↓↓↓
    // --- POST /bookings/{id}/delete ---
    @PostMapping("/{id}/delete")
    public String deleteBookingById(@PathVariable Long id) {
        this.bookingService.delete(id);
        return "redirect:/bookings/list";
    }
    // ↑↑↑↑↑ RADERA EN BOOKING ↑↑↑↑↑

    // ↓↓↓↓↓ HITTA EN BOOKING VIA ID ↓↓↓↓↓
    // --- GET /bookings/delete/find ---
    @GetMapping("/delete/find")
    public String findBookingToDelete(@RequestParam Long id, Model model) {
        model.addAttribute("booking", this.bookingService.findById(id));
        return "bookings/delete";
    }
    // ↑↑↑↑↑ HITTA EN BOOKING VIA ID ↑↑↑↑↑
}
