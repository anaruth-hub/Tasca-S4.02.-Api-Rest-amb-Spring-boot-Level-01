package cat.itacademy.s04.t02.n02.dto;

import jakarta.validation.constraints.NotBlank;

public class ProviderRequestDto {

    @NotBlank(message = "Provider name must not be blank")
    private String name;

    @NotBlank(message = "Provider country must not be blank")
    private String country;

    public ProviderRequestDto() {
    }

    public ProviderRequestDto(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}