package cat.itacademy.s04.t02.n02.mapper;

import cat.itacademy.s04.t02.n02.dto.ProviderRequestDto;
import cat.itacademy.s04.t02.n02.dto.ProviderResponseDto;
import cat.itacademy.s04.t02.n02.model.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper {

    public Provider toEntity(ProviderRequestDto requestDto) {
        return new Provider(requestDto.getName(), requestDto.getCountry());
    }

    public ProviderResponseDto toResponseDto(Provider provider) {
        return new ProviderResponseDto(
                provider.getId(),
                provider.getName(),
                provider.getCountry()
        );
    }

    public void updateEntity(Provider provider, ProviderRequestDto requestDto) {
        provider.setName(requestDto.getName());
        provider.setCountry(requestDto.getCountry());
    }
}