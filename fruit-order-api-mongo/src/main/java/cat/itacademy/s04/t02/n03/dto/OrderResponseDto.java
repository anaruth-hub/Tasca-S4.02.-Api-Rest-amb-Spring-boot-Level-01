package cat.itacademy.s04.t02.n03.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderResponseDto {

    private String id;
    private String clientName;
    private LocalDate deliveryDate;
    private List<OrderItemResponseDto> items;

    public OrderResponseDto() {
    }

    public OrderResponseDto(String id, String clientName, LocalDate deliveryDate, List<OrderItemResponseDto> items) {
        this.id = id;
        this.clientName = clientName;
        this.deliveryDate = deliveryDate;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public List<OrderItemResponseDto> getItems() {
        return items;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setItems(List<OrderItemResponseDto> items) {
        this.items = items;
    }
}