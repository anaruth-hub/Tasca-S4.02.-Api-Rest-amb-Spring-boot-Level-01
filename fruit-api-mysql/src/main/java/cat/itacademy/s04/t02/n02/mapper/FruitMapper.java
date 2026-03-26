package cat.itacademy.s04.t02.n02.mapper;

import cat.itacademy.s04.t02.n02.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n02.model.Fruit;
import cat.itacademy.s04.t02.n02.model.Provider;
import org.springframework.stereotype.Component;

@Component
public class FruitMapper {

    public Fruit toEntity(String name, int weightInKilos, Provider provider) {
        return new Fruit(name, weightInKilos, provider);
    }

    public FruitResponseDto toResponseDto(Fruit fruit) {
        return new FruitResponseDto(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos(),
                fruit.getProvider().getId(),
                fruit.getProvider().getName()
        );
    }

    public void updateEntity(Fruit fruit, String name, int weightInKilos, Provider provider) {
        fruit.setName(name);
        fruit.setWeightInKilos(weightInKilos);
        fruit.setProvider(provider);
    }
}