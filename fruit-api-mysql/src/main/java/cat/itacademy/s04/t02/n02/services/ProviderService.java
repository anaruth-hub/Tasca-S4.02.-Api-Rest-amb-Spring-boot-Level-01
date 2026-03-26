package cat.itacademy.s04.t02.n02.services;

import cat.itacademy.s04.t02.n02.dto.ProviderRequestDto;
import cat.itacademy.s04.t02.n02.dto.ProviderResponseDto;

import java.util.List;

public interface ProviderService {

    ProviderResponseDto createProvider(ProviderRequestDto requestDto);

    List<ProviderResponseDto> getAllProviders();

    ProviderResponseDto updateProvider(Long id, ProviderRequestDto requestDto);

    void deleteProvider(Long id);
}