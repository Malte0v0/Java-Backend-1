package org.example.javabackend1.Room;

import org.example.javabackend1.Exceptions.RoomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomControllerThyme {
    private final RoomService roomService;

    public RoomControllerThyme(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String mainMenu() {
        return "rooms/room";
    }

    // --- GET /rooms/available ---
    @GetMapping("/available")
    public String showAvailableRooms(@RequestParam(required = false) String checkIn,
                                     @RequestParam(required = false) String checkOut,
                                     @RequestParam(required = false) Integer guests,
                                     Model model) {
        if (checkIn != null && checkOut != null) {
            model.addAttribute("rooms", roomService.findAvailableRooms(checkIn, checkOut, guests));
            model.addAttribute("checkIn", checkIn);
            model.addAttribute("checkOut", checkOut);
            model.addAttribute("guests", guests);
        }
        return "rooms/available";
    }

    // --- GET /rooms/list ---
    @GetMapping("/list")
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "rooms/list";
    }



    //              ------------------------------ NEW ------------------------------
    // ↓↓↓↓↓ SKAPA EN NY BOOKING ↓↓↓↓↓
    // --- GET /rooms/new ---
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new RoomCreateDTO());
        model.addAttribute("roomTypes", RoomType.values());
        return "rooms/new";
    }

    // --- POST /rooms/new ---
    @PostMapping("/new")
    public String createRoom(@ModelAttribute("room") RoomCreateDTO dto, Model model) {
        try {
            roomService.create(dto);
            return "redirect:/rooms/list";
        } catch (RoomException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("room", dto);
            model.addAttribute("roomTypes", RoomType.values());
            return "rooms/new";
        }
    }
    // ↑↑↑↑↑ SKAPA EN NY BOOKING ↑↑↑↑↑

    // --- GET /rooms/{id} ---
    @GetMapping("/{id}")
    public String getRoom(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.findById(id));
        return "rooms/details";
    }



    //              ------------------------------ EDIT ------------------------------
    // ↓↓↓↓↓ REDIGERA ETT ROOM ↓↓↓↓↓
    // --- GET /rooms/{id}/edit ---
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.findById(id));
        model.addAttribute("roomTypes",RoomType.values());
        return "rooms/edit";
    }

    // --- POST /rooms/{id}/edit ---
    @PostMapping("/{id}/edit")
    public String updateRoom(@PathVariable Long id,
                             @ModelAttribute("room") RoomCreateDTO dto) {
        roomService.update(id, dto);
        return "redirect:/rooms/list";
    }
    // ↑↑↑↑↑ REDIGERA ETT ROOM ↑↑↑↑↑



    //              ------------------------------ DELETE ------------------------------
    // ↓↓↓↓↓ RADERA ETT ROOM ↓↓↓↓↓
    // --- GET /rooms/{id}/delete ---
    @GetMapping("/{id}/delete")
    public String showDeletePage(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.findById(id));
        return "rooms/delete";
    }

    // --- POST /rooms/{id}/delete ---
    @PostMapping("/{id}/delete")
    public String deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return "redirect:/rooms/list";
    }
    // ↑↑↑↑↑ RADERA ETT ROOM ↑↑↑↑↑
}