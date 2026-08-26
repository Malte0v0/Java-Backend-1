package org.example.javabackend1.Room;

import jakarta.validation.constraints.NotNull;

public class RoomResponseDTO {
    @NotNull
    private Long roomId;

    @NotNull
    private RoomType roomType;

    @NotNull
    private int extraBeds;

    private Integer maxCapacity;

    public RoomResponseDTO(Long roomId, RoomType roomType, int extraBeds, Integer maxCapacity) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.extraBeds = extraBeds;
        this.maxCapacity = maxCapacity;
    }

    public RoomResponseDTO() {
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getExtraBeds() {
        return extraBeds;
    }

    public void setExtraBeds(int extraBeds) {
        this.extraBeds = extraBeds;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
