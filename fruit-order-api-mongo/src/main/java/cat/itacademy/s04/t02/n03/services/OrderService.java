package cat.itacademy.s04.t02.n03.services;

import cat.itacademy.s04.t02.n03.dto.OrderRequestDto;
import cat.itacademy.s04.t02.n03.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto requestDto);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderById(String id);

    OrderResponseDto updateOrder(String id, OrderRequestDto requestDto);

    void deleteOrder(String id);
}