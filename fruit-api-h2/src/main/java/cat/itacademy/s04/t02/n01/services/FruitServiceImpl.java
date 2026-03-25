package cat.itacademy.s04.t02.n01.services;

import cat.itacademy.s04.t02.n01.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.mapper.FruitMapper;
import cat.itacademy.s04.t02.n01.model.Fruit;
import cat.itacademy.s04.t02.n01.repository.FruitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;
    private final FruitMapper fruitMapper;

    public FruitServiceImpl(FruitRepository fruitRepository, FruitMapper fruitMapper) {
        this.fruitRepository = fruitRepository;
        this.fruitMapper = fruitMapper;
    }

    @Override
    public FruitResponseDto createFruit(FruitRequestDto requestDto) {
        Fruit fruit = fruitMapper.toEntity(requestDto);
        Fruit savedFruit = fruitRepository.save(fruit);
        return fruitMapper.toResponseDto(savedFruit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FruitResponseDto> getAllFruits() {
        return fruitRepository.findAll()
                .stream()
                .map(fruitMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FruitResponseDto getFruitById(Long id) {
        Fruit fruit = findFruitByIdOrThrow(id);
        return fruitMapper.toResponseDto(fruit);
    }

    @Override
    public FruitResponseDto updateFruit(Long id, FruitRequestDto requestDto) {
        Fruit fruit = findFruitByIdOrThrow(id);
        fruitMapper.updateEntity(fruit, requestDto);
        Fruit updatedFruit = fruitRepository.save(fruit);
        return fruitMapper.toResponseDto(updatedFruit);
    }

    @Override
    public void deleteFruit(Long id) {
        Fruit fruit = findFruitByIdOrThrow(id);
        fruitRepository.delete(fruit);
    }

    private Fruit findFruitByIdOrThrow(Long id) {
        return fruitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit with id " + id + " not found"));
    }
}