package org.example.javabackend1.Room;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rooms")
public class RoomController {
//**************** för tymeleaf *************
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @GetMapping
    public String mainMenu() {
        return "rooms/room";
    }
    @GetMapping("/available")
    public String showAvailableRooms(@RequestParam(required = false) String checkIn,
                                     @RequestParam(required = false) String checkOut,
                                     @RequestParam(required = false, defaultValue = "1") int guests,
                                     Model model) {
        if (checkIn != null && checkOut != null) {
            model.addAttribute("rooms", roomService.findAvailableRooms(checkIn, checkOut, guests));
        }

        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("guests", guests);

        return "rooms/available";
    }

    // READ all
    @GetMapping("/list")
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "rooms/list";
    }

    // SHOW create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new RoomCreateDTO());
        return "rooms/new";
    }


    @PostMapping("/new")
    public String createRoom(@ModelAttribute("room") RoomCreateDTO dto) {
        roomService.create(dto);
        return "redirect:/rooms/list";
    }


    @GetMapping("/{id}")
    public String getRoom(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.findById(id));
        return "rooms/details";
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.findById(id));
        model.addAttribute("roomTypes",RoomType.values());
        return "rooms/edit";
    }


    @PostMapping("/{id}/edit")
    public String updateRoom(@PathVariable Long id,
                             @ModelAttribute("room") RoomCreateDTO dto) {
        roomService.update(id, dto);
        return "redirect:/rooms/list";
    }
    @GetMapping("/{id}/delete")
    public String showDeletePage(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.findById(id));
        return "rooms/delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return "redirect:/rooms/list";
    }
}