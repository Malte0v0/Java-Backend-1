package org.example.javabackend1.Room;

import org.example.javabackend1.Booking.BookingCreateDTO;
import org.example.javabackend1.Exceptions.RoomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        roomRepository.deleteAllInBatch();
    }

    private RoomCreateDTO makeDTO(RoomType roomType, int extraBeds) {
        RoomCreateDTO dto = new RoomCreateDTO();
        dto.setRoomType(roomType);
        dto.setExtraBeds(extraBeds);

        return dto;
    }


    @Test
    void create_shouldSaveDoubleRoomWithExtraBeds() {
        RoomCreateDTO dto = new RoomCreateDTO();
        dto.setRoomType(RoomType.DOUBLE);
        dto.setExtraBeds(1);

        RoomResponseDTO result = roomService.create(dto);

        assertNotNull(result.getRoomId());
        assertEquals(RoomType.DOUBLE, result.getRoomType());
        assertEquals(1, result.getExtraBeds());
    }

    @Test
    void create_shouldSaveSingleRoom() {
        RoomCreateDTO dto = new RoomCreateDTO();
        dto.setRoomType(RoomType.SINGLE);
        dto.setExtraBeds(0);

        RoomResponseDTO result = roomService.create(dto);

        assertEquals(RoomType.SINGLE, result.getRoomType());
        assertEquals(0, result.getExtraBeds());
    }

    @Test
    void create_shouldThrow_whenSingleRoomHasExtraBeds() {
        RoomCreateDTO dto = new RoomCreateDTO();
        dto.setRoomType(RoomType.SINGLE);
        dto.setExtraBeds(1);

        assertThrows(RoomException.class, () -> roomService.create(dto));
    }

    @Test
    void update_shouldThrow_whenChangingToSingleWithExtraBeds() {
        RoomCreateDTO original = new RoomCreateDTO();
        original.setRoomType(RoomType.DOUBLE);
        original.setExtraBeds(1);
        RoomResponseDTO created = roomService.create(original);

        RoomCreateDTO updateDTO = new RoomCreateDTO();
        updateDTO.setRoomType(RoomType.SINGLE);
        updateDTO.setExtraBeds(1);

        assertThrows(RoomException.class, () -> roomService.update(created.getRoomId(), updateDTO));
    }

//    @Test
//    void findById_shouldReturnRoom() {
//        RoomCreateDTO dto = new RoomCreateDTO();
//        dto.setRoomType(RoomType.DOUBLE);
//        dto.setExtraBeds(0);
//        RoomResponseDTO created = roomService.create(dto);
//
//        RoomResponseDTO found = roomService.findById(created.getRoomId());
//        assertEquals(created.getRoomId(), found.getRoomId());
//    }

    @Test
    void delete_shouldRemoveRoom() {
        RoomCreateDTO dto = new RoomCreateDTO();
        dto.setRoomType(RoomType.SINGLE);
        dto.setExtraBeds(0);
        RoomResponseDTO created = roomService.create(dto);

        roomService.delete(created.getRoomId());

        assertThrows(RuntimeException.class, () -> roomService.findById(created.getRoomId()));
    }

    @Test
    void findAvailableRooms_shouldThrow_whenCheckOutBeforeCheckIn() {
        assertThrows(RoomException.class, () ->
                roomService.findAvailableRooms("2025-06-10", "2025-06-05", 2)
        );
    }

    @Test
    void findAvailableRooms_shouldThrow_whenCheckOutEqualsCheckIn() {
        assertThrows(RoomException.class, () ->
                roomService.findAvailableRooms("2025-06-10", "2025-06-10", 1)
        );
    }
}