package cat.itacademy.s04.t02.n01.services;

import cat.itacademy.s04.t02.n01.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDto;

import java.util.List;

public interface FruitService {

    FruitResponseDto createFruit(FruitRequestDto requestDto);

    List<FruitResponseDto> getAllFruits();

    FruitResponseDto getFruitById(Long id);

    FruitResponseDto updateFruit(Long id, FruitRequestDto requestDto);

    void deleteFruit(Long id);
}