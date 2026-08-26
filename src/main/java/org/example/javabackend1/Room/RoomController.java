package org.example.javabackend1.Room;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//************** för swagger ''''''''''''
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomResponseDTO> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/available")
    public List<RoomResponseDTO> getAvailableRooms(@RequestParam String checkIn,
                                                   @RequestParam String checkOut,
                                                   @RequestParam(defaultValue = "1") int guests) {
        return roomService.findAvailableRooms(checkIn, checkOut, guests);
    }

    @GetMapping("/{id}")
    public RoomResponseDTO getRoomById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @PostMapping
    public RoomResponseDTO createRoom(@RequestBody RoomCreateDTO dto) {
        return roomService.create(dto);
    }

    @PutMapping("/{id}")
    public RoomResponseDTO updateRoom(@PathVariable Long id,
                                      @RequestBody RoomCreateDTO dto) {
        return roomService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
    }
}
// så du kommer åt dem
// GET    /api/rooms
//GET    /api/rooms/{id}
//POST   /api/rooms
//PUT    /api/rooms/{id}
//DELETE /api/rooms/{id}