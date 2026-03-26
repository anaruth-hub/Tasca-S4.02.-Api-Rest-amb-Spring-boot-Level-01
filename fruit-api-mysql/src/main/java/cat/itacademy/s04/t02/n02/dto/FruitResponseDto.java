package cat.itacademy.s04.t02.n02.dto;

public class FruitResponseDto {

    private Long id;
    private String name;
    private int weightInKilos;
    private Long providerId;
    private String providerName;

    public FruitResponseDto() {
    }

    public FruitResponseDto(Long id, String name, int weightInKilos, Long providerId, String providerName) {
        this.id = id;
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.providerId = providerId;
        this.providerName = providerName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeightInKilos() {
        return weightInKilos;
    }

    public Long getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeightInKilos(int weightInKilos) {
        this.weightInKilos = weightInKilos;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}