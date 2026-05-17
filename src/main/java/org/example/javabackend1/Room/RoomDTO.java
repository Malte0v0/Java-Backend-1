package org.example.javabackend1.Room;

import jakarta.validation.constraints.NotNull;

public class RoomDTO {
    @NotNull
    private Long roomId;

    @NotNull
    private RoomType roomType;

    @NotNull
    private int extraBeds;
}
