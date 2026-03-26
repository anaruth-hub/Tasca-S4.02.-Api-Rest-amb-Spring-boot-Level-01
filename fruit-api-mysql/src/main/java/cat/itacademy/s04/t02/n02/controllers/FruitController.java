package cat.itacademy.s04.t02.n02.controllers;

import cat.itacademy.s04.t02.n02.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n02.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n02.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping
    public ResponseEntity<FruitResponseDto> createFruit(@Valid @RequestBody FruitRequestDto requestDto) {
        FruitResponseDto createdFruit = fruitService.createFruit(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFruit);
    }

    @GetMapping
    public ResponseEntity<List<FruitResponseDto>> getFruits(@RequestParam(required = false) Long providerId) {
        if (providerId != null) {
            return ResponseEntity.ok(fruitService.getFruitsByProviderId(providerId));
        }

        return ResponseEntity.ok(fruitService.getAllFruits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitResponseDto> getFruitById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fruitService.getFruitById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitResponseDto> updateFruit(
            @PathVariable("id") Long id,
            @Valid @RequestBody FruitRequestDto requestDto
    ) {
        return ResponseEntity.ok(fruitService.updateFruit(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable("id") Long id) {
        fruitService.deleteFruit(id);
        return ResponseEntity.noContent().build();
    }
}