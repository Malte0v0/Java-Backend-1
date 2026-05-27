package org.example.javabackend1.Booking;

import org.example.javabackend1.Customer.CustomerService;
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

    @GetMapping("/list")
    public String getAllBookings(Model model) {
        model.addAttribute("bookings", this.bookingService.findAll());
        return "bookings/list";
    }



    //              ------------------------------ NEW ------------------------------
    // ↓↓↓↓↓ SKAPA EN NY BOOKING ↓↓↓↓↓
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("booking", new BookingCreateDTO());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "bookings/new";
    }

    @PostMapping("/new")
    public String createBooking(@ModelAttribute("booking") BookingCreateDTO dto) {
        this.bookingService.create(dto);
        return "redirect:/bookings/list";
    }
    // ↑↑↑↑↑ SKAPA EN NY BOOKING ↑↑↑↑↑



    //              ------------------------------ EDIT ------------------------------
    @GetMapping("/edit")
    public String showEditSearch() {
        return "bookings/edit";
    }

    // ↓↓↓↓↓ HITTA EN BOOKING VIA ID ↓↓↓↓↓
    @GetMapping("/edit/find")
    public String findBookingToEdit(@RequestParam Long id, Model model) {
        model.addAttribute("booking", this.bookingService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "bookings/edit";
    }
    // ↑↑↑↑↑ HITTA EN BOOKING VIA ID ↑↑↑↑↑

    // ↓↓↓↓↓ REDIGERA EN BOOKING ↓↓↓↓↓
    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("booking", this.bookingService.findById(id));
        return "bookings/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateBookingById(@PathVariable Long id,
                                    @ModelAttribute BookingCreateDTO dto) {
        bookingService.update(id, dto);
        return "redirect:/bookings/list";
    }
    // ↑↑↑↑↑ REDIGERA EN BOOKING ↑↑↑↑↑



    //              ------------------------------ DELETE ------------------------------
    @GetMapping("/delete")
    public String showDeleteSearch() {
        return "bookings/delete";
    }

    // ↓↓↓↓↓ RADERA EN BOOKING ↓↓↓↓↓
    @PostMapping("/{id}/delete")
    public String deleteBookingById(@PathVariable Long id) {
        this.bookingService.delete(id);
        return "redirect:/bookings/list";
    }
    // ↑↑↑↑↑ RADERA EN BOOKING ↑↑↑↑↑

    // ↓↓↓↓↓ HITTA EN BOOKING VIA ID ↓↓↓↓↓
    @GetMapping("/delete/find")
    public String findBookingToDelete(@RequestParam Long id, Model model) {
        model.addAttribute("booking", this.bookingService.findById(id));
        return "bookings/delete";
    }
    // ↑↑↑↑↑ HITTA EN BOOKING VIA ID ↑↑↑↑↑
}
