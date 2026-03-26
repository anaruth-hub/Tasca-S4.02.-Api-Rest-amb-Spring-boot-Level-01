package cat.itacademy.s04.t02.n02.dto;

public class ProviderResponseDto {

    private Long id;
    private String name;
    private String country;

    public ProviderResponseDto() {
    }

    public ProviderResponseDto(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}