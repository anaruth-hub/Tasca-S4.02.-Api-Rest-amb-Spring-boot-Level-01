package cat.itacademy.s04.t02.n02.controllers;

import cat.itacademy.s04.t02.n02.dto.ProviderRequestDto;
import cat.itacademy.s04.t02.n02.dto.ProviderResponseDto;
import cat.itacademy.s04.t02.n02.exception.BadRequestException;
import cat.itacademy.s04.t02.n02.exception.GlobalExceptionHandler;
import cat.itacademy.s04.t02.n02.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.services.ProviderService;
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

@WebMvcTest(ProviderController.class)
@Import(GlobalExceptionHandler.class)
class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProviderService providerService;

    @Test
    @DisplayName("Should create provider and return 201 Created")
    void shouldCreateProvider() throws Exception {
        ProviderRequestDto requestDto = new ProviderRequestDto("Fresh Fruits Ltd", "Spain");
        ProviderResponseDto responseDto = new ProviderResponseDto(1L, "Fresh Fruits Ltd", "Spain");

        given(providerService.createProvider(any(ProviderRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fresh Fruits Ltd"))
                .andExpect(jsonPath("$.country").value("Spain"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when provider input is invalid")
    void shouldReturnBadRequestWhenProviderInputIsInvalid() throws Exception {
        ProviderRequestDto requestDto = new ProviderRequestDto("", "");

        mockMvc.perform(post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.validationErrors.name").exists())
                .andExpect(jsonPath("$.validationErrors.country").exists());
    }

    @Test
    @DisplayName("Should return all providers")
    void shouldReturnAllProviders() throws Exception {
        List<ProviderResponseDto> providers = List.of(
                new ProviderResponseDto(1L, "Fresh Fruits Ltd", "Spain"),
                new ProviderResponseDto(2L, "Tropical Export", "Ecuador")
        );

        given(providerService.getAllProviders()).willReturn(providers);

        mockMvc.perform(get("/providers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Fresh Fruits Ltd"))
                .andExpect(jsonPath("$[1].name").value("Tropical Export"));
    }

    @Test
    @DisplayName("Should delete provider and return 204 No Content")
    void shouldDeleteProvider() throws Exception {
        mockMvc.perform(delete("/providers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 400 when deleting provider with associated fruits")
    void shouldReturnBadRequestWhenDeletingProviderWithAssociatedFruits() throws Exception {
        willThrow(new BadRequestException("Provider with id 1 cannot be deleted because it has associated fruits"))
                .given(providerService).deleteProvider(1L);

        mockMvc.perform(delete("/providers/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Provider with id 1 cannot be deleted because it has associated fruits"));
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existing provider")
    void shouldReturnNotFoundWhenDeletingNonExistingProvider() throws Exception {
        willThrow(new ResourceNotFoundException("Provider with id 99 not found"))
                .given(providerService).deleteProvider(99L);

        mockMvc.perform(delete("/providers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Provider with id 99 not found"));
    }
}