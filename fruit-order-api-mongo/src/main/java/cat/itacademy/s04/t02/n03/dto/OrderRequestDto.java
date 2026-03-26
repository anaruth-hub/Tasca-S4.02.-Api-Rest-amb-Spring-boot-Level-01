package cat.itacademy.s04.t02.n03.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class OrderRequestDto {

    @NotBlank(message = "Client name must not be blank")
    private String clientName;

    @NotNull(message = "Delivery date must not be null")
    private LocalDate deliveryDate;

    @NotEmpty(message = "Order must contain at least one item")
    private List<@Valid OrderItemRequestDto> items;

    public OrderRequestDto() {
    }

    public OrderRequestDto(String clientName, LocalDate deliveryDate, List<OrderItemRequestDto> items) {
        this.clientName = clientName;
        this.deliveryDate = deliveryDate;
        this.items = items;
    }

    public String getClientName() {
        return clientName;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public List<OrderItemRequestDto> getItems() {
        return items;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setItems(List<OrderItemRequestDto> items) {
        this.items = items;
    }
}