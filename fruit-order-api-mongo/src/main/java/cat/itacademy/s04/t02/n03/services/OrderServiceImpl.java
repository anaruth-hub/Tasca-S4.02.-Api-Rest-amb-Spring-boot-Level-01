package cat.itacademy.s04.t02.n03.services;

import cat.itacademy.s04.t02.n03.dto.OrderRequestDto;
import cat.itacademy.s04.t02.n03.dto.OrderResponseDto;
import cat.itacademy.s04.t02.n03.exception.BadRequestException;
import cat.itacademy.s04.t02.n03.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n03.mapper.OrderMapper;
import cat.itacademy.s04.t02.n03.model.Order;
import cat.itacademy.s04.t02.n03.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        validateDeliveryDate(requestDto.getDeliveryDate());

        Order order = orderMapper.toEntity(requestDto);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponseDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }

    @Override
    public OrderResponseDto getOrderById(String id) {
        Order order = findOrderByIdOrThrow(id);
        return orderMapper.toResponseDto(order);
    }

    @Override
    public OrderResponseDto updateOrder(String id, OrderRequestDto requestDto) {
        validateDeliveryDate(requestDto.getDeliveryDate());

        Order order = findOrderByIdOrThrow(id);
        orderMapper.updateEntity(order, requestDto);

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponseDto(updatedOrder);
    }

    @Override
    public void deleteOrder(String id) {
        Order order = findOrderByIdOrThrow(id);
        orderRepository.delete(order);
    }

    private Order findOrderByIdOrThrow(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
    }

    private void validateDeliveryDate(LocalDate deliveryDate) {
        if (deliveryDate.isBefore(LocalDate.now().plusDays(1))) {
            throw new BadRequestException("Delivery date must be at least tomorrow");
        }
    }
}