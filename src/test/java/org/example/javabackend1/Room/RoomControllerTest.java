package org.example.javabackend1.Room;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomController roomController;

    @Autowired
    private RoomRepository roomRepository;

    private Long savedRoomId;

    @BeforeEach
    void setUp() {
        RoomEntity r1 = new RoomEntity(null, RoomType.SINGLE, 0);
        RoomEntity r2 = new RoomEntity(null, RoomType.DOUBLE, 0);
        RoomEntity r3 = new RoomEntity(null, RoomType.DOUBLE, 2);
        roomRepository.save(r1);
        roomRepository.save(r2);
        savedRoomId = roomRepository.save(r3).getRoomId();
    }

    @AfterEach
    void tearDown() {
        roomRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertThat(roomController).isNotNull();
    }

    @Test
    void mainMenu_returnsRoomView() throws Exception {
        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms/room"));
    }

    // --- GET /rooms/list ---

    @Test
    void listRooms_returnsListViewWithAllRooms() throws Exception {
        mockMvc.perform(get("/rooms/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms/list"))
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attribute("rooms", hasSize(3)));
    }

    // --- GET /rooms/new ---

    @Test
    void showCreateForm_returnsNewViewWithDTOAndRoomTypes() throws Exception {
        mockMvc.perform(get("/rooms/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms/new"))
                .andExpect(model().attributeExists("room"))
                .andExpect(model().attributeExists("roomTypes"));
    }

    // --- POST /rooms/new ---

    @Test
    void createRoom_savesAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/rooms/new")
                        .param("roomType", "SINGLE")
                        .param("extraBeds", "0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rooms/list"));

        assertThat(roomRepository.findAll()).hasSize(4);
    }

    // --- GET /rooms/{id} ---

    @Test
    void getRoom_returnsDetailsViewWithRoom() throws Exception {
        mockMvc.perform(get("/rooms/{id}", savedRoomId))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms/details"))
                .andExpect(model().attributeExists("room"));
    }

    // --- GET /rooms/{id}/edit ---

    @Test
    void showEditForm_returnsEditViewWithRoomAndTypes() throws Exception {
        mockMvc.perform(get("/rooms/{id}/edit", savedRoomId))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms/edit"))
                .andExpect(model().attributeExists("room"))
                .andExpect(model().attributeExists("roomTypes"));
    }

    // --- POST /rooms/{id}/edit ---

    @Test
    void updateRoom_updatesAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/rooms/{id}/edit", savedRoomId)
                        .param("roomType", "DOUBLE")
                        .param("extraBeds", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rooms/list"));

        RoomEntity updated = roomRepository.findById(savedRoomId).orElseThrow();
        assertThat(updated.getExtraBeds()).isEqualTo(1);
    }

    // --- GET /rooms/{id}/delete ---

    @Test
    void showDeletePage_returnsDeleteViewWithRoom() throws Exception {
        mockMvc.perform(get("/rooms/{id}/delete", savedRoomId))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms/delete"))
                .andExpect(model().attributeExists("room"));
    }

    // --- POST /rooms/{id}/delete ---

    @Test
    void deleteRoom_deletesAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/rooms/{id}/delete", savedRoomId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rooms/list"));

        assertThat(roomRepository.findById(savedRoomId)).isEmpty();
        assertThat(roomRepository.findAll()).hasSize(2);
    }

    // --- GET /rooms/available (without params) ---

    @Test
    void showAvailableRooms_withoutParams_returnsViewWithNoRoomsAttribute() throws Exception {
        mockMvc.perform(get("/rooms/available"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms/available"))
                .andExpect(model().attributeDoesNotExist("rooms"));
    }

    // --- GET /rooms/available (with params) ---

    @Test
    void showAvailableRooms_withParams_returnsViewWithRoomsAndEchosParams() throws Exception {
        mockMvc.perform(get("/rooms/available")
                        .param("checkIn",  "2025-09-01")
                        .param("checkOut", "2025-09-05")
                        .param("guests",   "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms/available"))
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attribute("checkIn",  "2025-09-01"))
                .andExpect(model().attribute("checkOut", "2025-09-05"))
                .andExpect(model().attribute("guests",   2));
    }
}