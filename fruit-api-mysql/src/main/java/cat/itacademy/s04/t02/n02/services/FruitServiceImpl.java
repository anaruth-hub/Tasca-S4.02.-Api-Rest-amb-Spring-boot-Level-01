package cat.itacademy.s04.t02.n02.services;

import cat.itacademy.s04.t02.n02.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n02.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n02.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.mapper.FruitMapper;
import cat.itacademy.s04.t02.n02.model.Fruit;
import cat.itacademy.s04.t02.n02.model.Provider;
import cat.itacademy.s04.t02.n02.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;
    private final FruitMapper fruitMapper;

    public FruitServiceImpl(FruitRepository fruitRepository,
                            ProviderRepository providerRepository,
                            FruitMapper fruitMapper) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
        this.fruitMapper = fruitMapper;
    }

    @Override
    public FruitResponseDto createFruit(FruitRequestDto requestDto) {
        Provider provider = findProviderByIdOrThrow(requestDto.getProviderId());

        Fruit fruit = fruitMapper.toEntity(
                requestDto.getName(),
                requestDto.getWeightInKilos(),
                provider
        );

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
    public List<FruitResponseDto> getFruitsByProviderId(Long providerId) {
        findProviderByIdOrThrow(providerId);

        return fruitRepository.findByProviderId(providerId)
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
        Provider provider = findProviderByIdOrThrow(requestDto.getProviderId());

        fruitMapper.updateEntity(
                fruit,
                requestDto.getName(),
                requestDto.getWeightInKilos(),
                provider
        );

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

    private Provider findProviderByIdOrThrow(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider with id " + id + " not found"));
    }
}