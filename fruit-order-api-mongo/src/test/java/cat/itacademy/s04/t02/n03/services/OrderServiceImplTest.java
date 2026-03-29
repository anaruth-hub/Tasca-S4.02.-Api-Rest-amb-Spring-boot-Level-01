package cat.itacademy.s04.t02.n03.services;

import cat.itacademy.s04.t02.n03.dto.OrderItemRequestDto;
import cat.itacademy.s04.t02.n03.dto.OrderRequestDto;
import cat.itacademy.s04.t02.n03.exception.BadRequestException;
import cat.itacademy.s04.t02.n03.mapper.OrderMapper;
import cat.itacademy.s04.t02.n03.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceImplTest {

    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final OrderMapper orderMapper = new OrderMapper();
    private final OrderServiceImpl orderService = new OrderServiceImpl(orderRepository, orderMapper);

    @Test
    @DisplayName("Should throw BadRequestException when delivery date is before tomorrow")
    void shouldThrowBadRequestExceptionWhenDeliveryDateIsBeforeTomorrow() {
        OrderRequestDto requestDto = new OrderRequestDto(
                "Ana",
                LocalDate.now(),
                List.of(new OrderItemRequestDto("Apple", 5))
        );

        assertThrows(BadRequestException.class, () -> orderService.createOrder(requestDto));
    }
}