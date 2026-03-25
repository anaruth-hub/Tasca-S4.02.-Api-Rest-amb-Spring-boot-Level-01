package cat.itacademy.s04.t02.n01.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FruitRequestDto {

    @NotBlank(message = "Fruit name must not be blank")
    private String name;

    @NotNull(message = "Weight must not be null")
    @Positive(message = "Weight must be greater than zero")
    private Integer weightInKilos;

    public FruitRequestDto() {
    }

    public FruitRequestDto(String name, Integer weightInKilos) {
        this.name = name;
        this.weightInKilos = weightInKilos;
    }

    public String getName() {
        return name;
    }

    public Integer getWeightInKilos() {
        return weightInKilos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeightInKilos(Integer weightInKilos) {
        this.weightInKilos = weightInKilos;
    }
}