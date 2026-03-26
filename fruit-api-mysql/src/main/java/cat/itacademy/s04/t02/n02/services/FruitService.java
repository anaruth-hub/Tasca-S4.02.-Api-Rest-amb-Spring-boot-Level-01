package cat.itacademy.s04.t02.n02.services;

import cat.itacademy.s04.t02.n02.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n02.dto.FruitResponseDto;

import java.util.List;

public interface FruitService {

    FruitResponseDto createFruit(FruitRequestDto requestDto);

    List<FruitResponseDto> getAllFruits();

    List<FruitResponseDto> getFruitsByProviderId(Long providerId);

    FruitResponseDto getFruitById(Long id);

    FruitResponseDto updateFruit(Long id, FruitRequestDto requestDto);

    void deleteFruit(Long id);
}