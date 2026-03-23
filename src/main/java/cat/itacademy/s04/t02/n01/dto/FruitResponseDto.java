package cat.itacademy.s04.t02.n01.dto;

public class FruitResponseDto {

    private Long id;
    private String name;
    private int weightInKilos;

    public FruitResponseDto() {
    }

    public FruitResponseDto(Long id, String name, int weightInKilos) {
        this.id = id;
        this.name = name;
        this.weightInKilos = weightInKilos;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeightInKilos(int weightInKilos) {
        this.weightInKilos = weightInKilos;
    }
}