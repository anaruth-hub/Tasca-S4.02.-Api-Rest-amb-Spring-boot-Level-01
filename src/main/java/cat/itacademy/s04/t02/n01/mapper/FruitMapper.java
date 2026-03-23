package cat.itacademy.s04.t02.n01.mapper;

import cat.itacademy.s04.t02.n01.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.model.Fruit;
import org.springframework.stereotype.Component;

@Component
public class FruitMapper {

    public Fruit toEntity(FruitRequestDto requestDto) {
        return new Fruit(
                requestDto.getName(),
                requestDto.getWeightInKilos()
        );
    }

    public FruitResponseDto toResponseDto(Fruit fruit) {
        return new FruitResponseDto(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos()
        );
    }

    public void updateEntity(Fruit fruit, FruitRequestDto requestDto) {
        fruit.setName(requestDto.getName());
        fruit.setWeightInKilos(requestDto.getWeightInKilos());
    }
}