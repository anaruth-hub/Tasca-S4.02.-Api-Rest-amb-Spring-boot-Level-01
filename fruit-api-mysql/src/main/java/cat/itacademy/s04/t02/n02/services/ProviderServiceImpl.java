package cat.itacademy.s04.t02.n02.services;

import cat.itacademy.s04.t02.n02.dto.ProviderRequestDto;
import cat.itacademy.s04.t02.n02.dto.ProviderResponseDto;
import cat.itacademy.s04.t02.n02.exception.BadRequestException;
import cat.itacademy.s04.t02.n02.exception.DuplicateResourceException;
import cat.itacademy.s04.t02.n02.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.mapper.ProviderMapper;
import cat.itacademy.s04.t02.n02.model.Provider;
import cat.itacademy.s04.t02.n02.repository.FruitRepository;
import cat.itacademy.s04.t02.n02.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final FruitRepository fruitRepository;
    private final ProviderMapper providerMapper;

    public ProviderServiceImpl(ProviderRepository providerRepository,
                               FruitRepository fruitRepository,
                               ProviderMapper providerMapper) {
        this.providerRepository = providerRepository;
        this.fruitRepository = fruitRepository;
        this.providerMapper = providerMapper;
    }

    @Override
    public ProviderResponseDto createProvider(ProviderRequestDto requestDto) {
        if (providerRepository.existsByNameIgnoreCase(requestDto.getName())) {
            throw new DuplicateResourceException("Provider name '" + requestDto.getName() + "' already exists");
        }

        Provider provider = providerMapper.toEntity(requestDto);
        Provider savedProvider = providerRepository.save(provider);

        return providerMapper.toResponseDto(savedProvider);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProviderResponseDto> getAllProviders() {
        return providerRepository.findAll()
                .stream()
                .map(providerMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProviderResponseDto updateProvider(Long id, ProviderRequestDto requestDto) {
        Provider provider = findProviderByIdOrThrow(id);

        boolean duplicatedNameExists = providerRepository.findAll()
                .stream()
                .anyMatch(existingProvider ->
                        existingProvider.getName().equalsIgnoreCase(requestDto.getName())
                                && !existingProvider.getId().equals(id));

        if (duplicatedNameExists) {
            throw new DuplicateResourceException("Provider name '" + requestDto.getName() + "' already exists");
        }

        providerMapper.updateEntity(provider, requestDto);
        Provider updatedProvider = providerRepository.save(provider);

        return providerMapper.toResponseDto(updatedProvider);
    }

    @Override
    public void deleteProvider(Long id) {
        Provider provider = findProviderByIdOrThrow(id);

        if (fruitRepository.existsByProviderId(id)) {
            throw new BadRequestException(
                    "Provider with id " + id + " cannot be deleted because it has associated fruits"
            );
        }

        providerRepository.delete(provider);
    }

    private Provider findProviderByIdOrThrow(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider with id " + id + " not found"));
    }
}