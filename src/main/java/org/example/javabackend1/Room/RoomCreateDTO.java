package org.example.javabackend1.Room;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomCreateDTO {
    @NotNull
    private Long roomId;

    @NotNull
    private RoomType roomType;

    @NotNull
    private int extraBeds;
}
