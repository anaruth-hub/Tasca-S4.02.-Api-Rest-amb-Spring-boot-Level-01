package cat.itacademy.s04.t02.n03.controllers;

import cat.itacademy.s04.t02.n03.dto.OrderItemRequestDto;
import cat.itacademy.s04.t02.n03.dto.OrderItemResponseDto;
import cat.itacademy.s04.t02.n03.dto.OrderRequestDto;
import cat.itacademy.s04.t02.n03.dto.OrderResponseDto;
import cat.itacademy.s04.t02.n03.exception.BadRequestException;
import cat.itacademy.s04.t02.n03.exception.GlobalExceptionHandler;
import cat.itacademy.s04.t02.n03.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n03.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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

@WebMvcTest(OrderController.class)
@Import(GlobalExceptionHandler.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    @DisplayName("Should create order and return 201 Created")
    void shouldCreateOrder() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto(
                "Ana",
                LocalDate.now().plusDays(1),
                List.of(
                        new OrderItemRequestDto("Apple", 5),
                        new OrderItemRequestDto("Banana", 3)
                )
        );

        OrderResponseDto responseDto = new OrderResponseDto(
                "abc123",
                "Ana",
                LocalDate.now().plusDays(1),
                List.of(
                        new OrderItemResponseDto("Apple", 5),
                        new OrderItemResponseDto("Banana", 3)
                )
        );

        given(orderService.createOrder(any(OrderRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.clientName").value("Ana"))
                .andExpect(jsonPath("$.items.length()").value(2));
    }

    @Test
    @DisplayName("Should return 400 when order date is invalid")
    void shouldReturnBadRequestWhenOrderDateIsInvalid() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto(
                "Ana",
                LocalDate.now(),
                List.of(new OrderItemRequestDto("Apple", 5))
        );

        willThrow(new BadRequestException("Delivery date must be at least tomorrow"))
                .given(orderService).createOrder(any(OrderRequestDto.class));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Delivery date must be at least tomorrow"));
    }

    @Test
    @DisplayName("Should return 400 when items list is empty")
    void shouldReturnBadRequestWhenItemsListIsEmpty() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto(
                "Ana",
                LocalDate.now().plusDays(1),
                List.of()
        );

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.validationErrors.items").exists());
    }

    @Test
    @DisplayName("Should return all orders")
    void shouldReturnAllOrders() throws Exception {
        List<OrderResponseDto> orders = List.of(
                new OrderResponseDto(
                        "abc123",
                        "Ana",
                        LocalDate.now().plusDays(1),
                        List.of(new OrderItemResponseDto("Apple", 5))
                )
        );

        given(orderService.getAllOrders()).willReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].clientName").value("Ana"));
    }

    @Test
    @DisplayName("Should return order by id")
    void shouldReturnOrderById() throws Exception {
        OrderResponseDto order = new OrderResponseDto(
                "abc123",
                "Ana",
                LocalDate.now().plusDays(1),
                List.of(new OrderItemResponseDto("Apple", 5))
        );

        given(orderService.getOrderById("abc123")).willReturn(order);

        mockMvc.perform(get("/orders/abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.clientName").value("Ana"));
    }

    @Test
    @DisplayName("Should return 404 when order does not exist")
    void shouldReturnNotFoundWhenOrderDoesNotExist() throws Exception {
        willThrow(new ResourceNotFoundException("Order with id xyz999 not found"))
                .given(orderService).getOrderById("xyz999");

        mockMvc.perform(get("/orders/xyz999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Order with id xyz999 not found"));
    }

    @Test
    @DisplayName("Should update order and return 200 OK")
    void shouldUpdateOrder() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto(
                "Ana Ruth",
                LocalDate.now().plusDays(2),
                List.of(new OrderItemRequestDto("Orange", 7))
        );

        OrderResponseDto responseDto = new OrderResponseDto(
                "abc123",
                "Ana Ruth",
                LocalDate.now().plusDays(2),
                List.of(new OrderItemResponseDto("Orange", 7))
        );

        given(orderService.updateOrder(eq("abc123"), any(OrderRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(put("/orders/abc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value("Ana Ruth"))
                .andExpect(jsonPath("$.items[0].fruitName").value("Orange"));
    }

    @Test
    @DisplayName("Should delete order and return 204 No Content")
    void shouldDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/abc123"))
                .andExpect(status().isNoContent());
    }
}