package cat.itacademy.s04.t02.n03.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemRequestDto {

    @NotBlank(message = "Fruit name must not be blank")
    private String fruitName;

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantityInKilos;

    public OrderItemRequestDto() {
    }

    public OrderItemRequestDto(String fruitName, Integer quantityInKilos) {
        this.fruitName = fruitName;
        this.quantityInKilos = quantityInKilos;
    }

    public String getFruitName() {
        return fruitName;
    }

    public Integer getQuantityInKilos() {
        return quantityInKilos;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public void setQuantityInKilos(Integer quantityInKilos) {
        this.quantityInKilos = quantityInKilos;
    }
}