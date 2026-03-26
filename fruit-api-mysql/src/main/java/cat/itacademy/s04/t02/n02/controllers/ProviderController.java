package cat.itacademy.s04.t02.n02.controllers;

import cat.itacademy.s04.t02.n02.dto.ProviderRequestDto;
import cat.itacademy.s04.t02.n02.dto.ProviderResponseDto;
import cat.itacademy.s04.t02.n02.services.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<ProviderResponseDto> createProvider(@Valid @RequestBody ProviderRequestDto requestDto) {
        ProviderResponseDto createdProvider = providerService.createProvider(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProvider);
    }

    @GetMapping
    public ResponseEntity<List<ProviderResponseDto>> getAllProviders() {
        return ResponseEntity.ok(providerService.getAllProviders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponseDto> updateProvider(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProviderRequestDto requestDto
    ) {
        return ResponseEntity.ok(providerService.updateProvider(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable("id") Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}