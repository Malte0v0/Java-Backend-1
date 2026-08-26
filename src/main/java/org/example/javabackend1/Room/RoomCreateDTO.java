package org.example.javabackend1.Room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RoomCreateDTO {
    @NotNull
    private RoomType roomType;

    @Min(0)
    private int extraBeds;

    public RoomCreateDTO(RoomType roomType, int extraBeds) {
        this.roomType = roomType;
        this.extraBeds = extraBeds;
    }

    public RoomCreateDTO() {
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
}
