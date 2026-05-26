package org.example.javabackend1.Room;

import org.example.javabackend1.Exceptions.RoomException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponseDTO> findAvailableRooms(String checkInDate, String checkOutDate) {
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);

        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new RoomException("Check out must be after check in");
        }

        List<RoomResponseDTO> responseRooms = new ArrayList<>();

        for (RoomEntity room : roomRepository.findAvailableRooms(checkIn, checkOut)) {
            responseRooms.add(toDTO(room));
        }

        return responseRooms;
    }

    public List<RoomResponseDTO> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public RoomResponseDTO findById(Long roomId) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Hitta inte rummet"));

        return toDTO(room);
    }

    public RoomCreateDTO findCreateDtoById(Long roomId) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Hitta inte rummet"));

        return toCreateDTO(room);
    }

    public RoomResponseDTO create(RoomCreateDTO dto) {
        RoomEntity room = toEntity(dto);
        RoomEntity savedRoom = roomRepository.save(room);
        return toDTO(savedRoom);
    }

    public RoomResponseDTO update(Long roomId, RoomCreateDTO dto) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("hitta inte rummet"));

        room.setRoomType(dto.getRoomType());
        room.setExtraBeds(dto.getExtraBeds());

        RoomEntity savedRoom = roomRepository.save(room);
        return toDTO(savedRoom);
    }

    public void delete(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    private RoomResponseDTO toDTO(RoomEntity room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setRoomId(room.getRoomId());
        dto.setRoomType(room.getRoomType());
        dto.setExtraBeds(room.getExtraBeds());
        return dto;
    }

    private RoomCreateDTO toCreateDTO(RoomEntity room) {
        RoomCreateDTO dto = new RoomCreateDTO();
        dto.setRoomType(room.getRoomType());
        dto.setExtraBeds(room.getExtraBeds());
        return dto;
    }

    private RoomEntity toEntity(RoomCreateDTO dto) {
        RoomEntity room = new RoomEntity();
        room.setRoomType(dto.getRoomType());
        room.setExtraBeds(dto.getExtraBeds());
        return room;
    }
}
