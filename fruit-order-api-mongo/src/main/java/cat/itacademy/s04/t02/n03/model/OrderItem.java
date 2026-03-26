package cat.itacademy.s04.t02.n03.model;

public class OrderItem {

    private String fruitName;
    private int quantityInKilos;

    public OrderItem() {
    }

    public OrderItem(String fruitName, int quantityInKilos) {
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