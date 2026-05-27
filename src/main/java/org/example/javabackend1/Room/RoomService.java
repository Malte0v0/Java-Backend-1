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

    public List<RoomResponseDTO> findAvailableRooms(String checkInDate, String checkOutDate, int guests) {
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);

        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new RoomException("Check out must be after check in");
        }

        List<RoomResponseDTO> responseRooms = new ArrayList<>();

        for (RoomEntity room : roomRepository.findAvailableRooms(checkIn, checkOut, guests)) {
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
        validateRoom(dto);
        if (dto.getRoomType() == RoomType.SINGLE && dto.getExtraBeds() > 0) {
            throw new RoomException("Single rooms cant have extra beds");
        }
        RoomEntity saved = roomRepository.saveAndFlush(toEntity(dto));
        return toDTO(saved);
    }

    public RoomResponseDTO update(Long roomId, RoomCreateDTO dto) {
        validateRoom(dto);

        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException("Room was not found"));

        room.setRoomType(dto.getRoomType());
        room.setExtraBeds(dto.getExtraBeds());

        RoomEntity saved = roomRepository.saveAndFlush(room);
        return toDTO(saved);
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


    private void validateRoom(RoomCreateDTO dto) {
        if (dto.getRoomType() == RoomType.SINGLE && dto.getExtraBeds() > 0) {
            throw new RoomException("Single rooms can't have extra beds");
        }

        if (dto.getRoomType() == RoomType.DOUBLE && dto.getExtraBeds() > 2) {
            throw new RoomException("Double rooms can have max 2 extra beds");
        }

        if (dto.getExtraBeds() < 0) {
            throw new RoomException("Extra beds cannot be negative");
        }
    }
}
