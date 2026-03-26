package cat.itacademy.s04.t02.n03.mapper;

import cat.itacademy.s04.t02.n03.dto.OrderItemRequestDto;
import cat.itacademy.s04.t02.n03.dto.OrderItemResponseDto;
import cat.itacademy.s04.t02.n03.dto.OrderRequestDto;
import cat.itacademy.s04.t02.n03.dto.OrderResponseDto;
import cat.itacademy.s04.t02.n03.model.Order;
import cat.itacademy.s04.t02.n03.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDto requestDto) {
        List<OrderItem> items = requestDto.getItems()
                .stream()
                .map(this::toOrderItem)
                .toList();

        return new Order(
                requestDto.getClientName(),
                requestDto.getDeliveryDate(),
                items
        );
    }

    public OrderResponseDto toResponseDto(Order order) {
        List<OrderItemResponseDto> items = order.getItems()
                .stream()
                .map(this::toOrderItemResponseDto)
                .toList();

        return new OrderResponseDto(
                order.getId(),
                order.getClientName(),
                order.getDeliveryDate(),
                items
        );
    }

    public void updateEntity(Order order, OrderRequestDto requestDto) {
        List<OrderItem> items = requestDto.getItems()
                .stream()
                .map(this::toOrderItem)
                .toList();

        order.setClientName(requestDto.getClientName());
        order.setDeliveryDate(requestDto.getDeliveryDate());
        order.setItems(items);
    }

    private OrderItem toOrderItem(OrderItemRequestDto itemDto) {
        return new OrderItem(
                itemDto.getFruitName(),
                itemDto.getQuantityInKilos()
        );
    }

    private OrderItemResponseDto toOrderItemResponseDto(OrderItem item) {
        return new OrderItemResponseDto(
                item.getFruitName(),
                item.getQuantityInKilos()
        );
    }
}