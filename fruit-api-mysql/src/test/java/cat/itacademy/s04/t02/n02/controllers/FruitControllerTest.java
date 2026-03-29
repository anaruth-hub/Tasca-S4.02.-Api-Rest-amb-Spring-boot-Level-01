package cat.itacademy.s04.t02.n02.controllers;

import cat.itacademy.s04.t02.n02.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n02.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n02.exception.GlobalExceptionHandler;
import cat.itacademy.s04.t02.n02.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.services.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FruitController.class)
@Import(GlobalExceptionHandler.class)
class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FruitService fruitService;

    @Test
    @DisplayName("Should create fruit and return 201 Created")
    void shouldCreateFruit() throws Exception {
        FruitRequestDto requestDto = new FruitRequestDto("Banana", 20, 1L);
        FruitResponseDto responseDto = new FruitResponseDto(1L, "Banana", 20, 1L, "Fresh Fruits Ltd");

        given(fruitService.createFruit(any(FruitRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.providerId").value(1))
                .andExpect(jsonPath("$.providerName").value("Fresh Fruits Ltd"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when fruit input is invalid")
    void shouldReturnBadRequestWhenFruitInputIsInvalid() throws Exception {
        FruitRequestDto requestDto = new FruitRequestDto("", 0, null);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.validationErrors.name").exists())
                .andExpect(jsonPath("$.validationErrors.weightInKilos").exists())
                .andExpect(jsonPath("$.validationErrors.providerId").exists());
    }

    @Test
    @DisplayName("Should return all fruits")
    void shouldReturnAllFruits() throws Exception {
        List<FruitResponseDto> fruits = List.of(
                new FruitResponseDto(1L, "Banana", 20, 1L, "Fresh Fruits Ltd"),
                new FruitResponseDto(2L, "Orange", 15, 1L, "Fresh Fruits Ltd")
        );

        given(fruitService.getAllFruits()).willReturn(fruits);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Banana"));
    }

    @Test
    @DisplayName("Should return fruits filtered by provider id")
    void shouldReturnFruitsByProviderId() throws Exception {
        List<FruitResponseDto> fruits = List.of(
                new FruitResponseDto(1L, "Banana", 20, 1L, "Fresh Fruits Ltd")
        );

        given(fruitService.getFruitsByProviderId(1L)).willReturn(fruits);

        mockMvc.perform(get("/fruits").param("providerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].providerId").value(1));
    }

    @Test
    @DisplayName("Should return fruit by id")
    void shouldReturnFruitById() throws Exception {
        FruitResponseDto fruit = new FruitResponseDto(1L, "Banana", 20, 1L, "Fresh Fruits Ltd");

        given(fruitService.getFruitById(1L)).willReturn(fruit);

        mockMvc.perform(get("/fruits/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Banana"));
    }

    @Test
    @DisplayName("Should return 404 when fruit does not exist")
    void shouldReturnNotFoundWhenFruitDoesNotExist() throws Exception {
        willThrow(new ResourceNotFoundException("Fruit with id 99 not found"))
                .given(fruitService).getFruitById(99L);

        mockMvc.perform(get("/fruits/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Fruit with id 99 not found"));
    }

    @Test
    @DisplayName("Should delete fruit and return 204 No Content")
    void shouldDeleteFruit() throws Exception {
        mockMvc.perform(delete("/fruits/1"))
                .andExpect(status().isNoContent());
    }
}