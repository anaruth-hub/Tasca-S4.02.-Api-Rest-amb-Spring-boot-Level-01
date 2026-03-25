package cat.itacademy.s04.t02.n01.controllers;

import cat.itacademy.s04.t02.n01.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.services.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FruitController.class)
class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FruitService fruitService;

    @Test
    @DisplayName("Should create a fruit and return 201 Created")
    void shouldCreateFruit() throws Exception {
        FruitRequestDto requestDto = new FruitRequestDto("Apple", 10);
        FruitResponseDto responseDto = new FruitResponseDto(1L, "Apple", 10);

        given(fruitService.createFruit(any(FruitRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(10));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when fruit request is invalid")
    void shouldReturnBadRequestWhenInvalidInput() throws Exception {
        FruitRequestDto requestDto = new FruitRequestDto("", -5);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.validationErrors.name").value("Fruit name must not be blank"))
                .andExpect(jsonPath("$.validationErrors.weightInKilos").value("Weight must be greater than zero"));
    }

    @Test
    @DisplayName("Should return all fruits")
    void shouldReturnAllFruits() throws Exception {
        List<FruitResponseDto> fruits = List.of(
                new FruitResponseDto(1L, "Apple", 10),
                new FruitResponseDto(2L, "Banana", 8)
        );

        given(fruitService.getAllFruits()).willReturn(fruits);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[0].weightInKilos").value(10))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Banana"))
                .andExpect(jsonPath("$[1].weightInKilos").value(8));
    }

    @Test
    @DisplayName("Should return an empty list when there are no fruits")
    void shouldReturnEmptyListWhenNoFruitsExist() throws Exception {
        given(fruitService.getAllFruits()).willReturn(List.of());

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Should return a fruit by id")
    void shouldReturnFruitById() throws Exception {
        FruitResponseDto responseDto = new FruitResponseDto(1L, "Apple", 10);

        given(fruitService.getFruitById(1L)).willReturn(responseDto);

        mockMvc.perform(get("/fruits/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(10));
    }

    @Test
    @DisplayName("Should return 404 Not Found when fruit does not exist")
    void shouldReturnNotFoundWhenFruitDoesNotExist() throws Exception {
        given(fruitService.getFruitById(99L))
                .willThrow(new ResourceNotFoundException("Fruit with id 99 not found"));

        mockMvc.perform(get("/fruits/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Fruit with id 99 not found"));
    }

    @Test
    @DisplayName("Should update a fruit and return 200 OK")
    void shouldUpdateFruit() throws Exception {
        FruitRequestDto requestDto = new FruitRequestDto("Green Apple", 15);
        FruitResponseDto responseDto = new FruitResponseDto(1L, "Green Apple", 15);

        given(fruitService.updateFruit(eq(1L), any(FruitRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(put("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Green Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(15));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when update input is invalid")
    void shouldReturnBadRequestWhenUpdateInputIsInvalid() throws Exception {
        FruitRequestDto requestDto = new FruitRequestDto("", 0);

        mockMvc.perform(put("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    @DisplayName("Should return 404 Not Found when updating a fruit that does not exist")
    void shouldReturnNotFoundWhenUpdatingNonExistingFruit() throws Exception {
        FruitRequestDto requestDto = new FruitRequestDto("Pear", 6);

        given(fruitService.updateFruit(eq(99L), any(FruitRequestDto.class)))
                .willThrow(new ResourceNotFoundException("Fruit with id 99 not found"));

        mockMvc.perform(put("/fruits/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Fruit with id 99 not found"));
    }

    @Test
    @DisplayName("Should delete a fruit and return 204 No Content")
    void shouldDeleteFruit() throws Exception {
        mockMvc.perform(delete("/fruits/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404 Not Found when deleting a fruit that does not exist")
    void shouldReturnNotFoundWhenDeletingNonExistingFruit() throws Exception {
        willThrow(new ResourceNotFoundException("Fruit with id 99 not found"))
                .given(fruitService)
                .deleteFruit(99L);

        mockMvc.perform(delete("/fruits/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Fruit with id 99 not found"));
    }
}