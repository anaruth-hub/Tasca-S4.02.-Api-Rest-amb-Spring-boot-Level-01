package cat.itacademy.s04.t02.n03.dto;

public class OrderItemResponseDto {

    private String fruitName;
    private int quantityInKilos;

    public OrderItemResponseDto() {
    }

    public OrderItemResponseDto(String fruitName, int quantityInKilos) {
        this.fruitName = fruitName;
        this.quantityInKilos = quantityInKilos;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getQuantityInKilos() {
        return quantityInKilos;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public void setQuantityInKilos(int quantityInKilos) {
        this.quantityInKilos = quantityInKilos;
    }
}