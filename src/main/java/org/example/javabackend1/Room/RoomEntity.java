package org.example.javabackend1.Room;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private int extraBeds;

    public int getMaxCapacity() {
        if (roomType == RoomType.SINGLE) return 1;
        return 2 + extraBeds;
    }

    public RoomEntity(Long roomId, RoomType roomType, int extraBeds) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.extraBeds = extraBeds;
    }

    public RoomEntity() {
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
