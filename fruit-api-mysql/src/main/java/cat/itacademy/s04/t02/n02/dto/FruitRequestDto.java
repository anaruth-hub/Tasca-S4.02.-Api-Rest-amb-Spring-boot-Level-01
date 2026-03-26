package cat.itacademy.s04.t02.n02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FruitRequestDto {

    @NotBlank(message = "Fruit name must not be blank")
    private String name;

    @NotNull(message = "Weight must not be null")
    @Positive(message = "Weight must be greater than zero")
    private Integer weightInKilos;

    @NotNull(message = "Provider id must not be null")
    private Long providerId;

    public FruitRequestDto() {
    }

    public FruitRequestDto(String name, Integer weightInKilos, Long providerId) {
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public Integer getWeightInKilos() {
        return weightInKilos;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeightInKilos(Integer weightInKilos) {
        this.weightInKilos = weightInKilos;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}