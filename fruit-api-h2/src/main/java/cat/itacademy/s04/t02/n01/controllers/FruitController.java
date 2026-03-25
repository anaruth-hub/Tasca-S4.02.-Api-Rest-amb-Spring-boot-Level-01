package cat.itacademy.s04.t02.n01.controllers;

import cat.itacademy.s04.t02.n01.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.services.FruitService;
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
    public ResponseEntity<List<FruitResponseDto>> getAllFruits() {
        return ResponseEntity.ok(fruitService.getAllFruits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitResponseDto> getFruitById(@PathVariable Long id) {
        return ResponseEntity.ok(fruitService.getFruitById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitResponseDto> updateFruit(
            @PathVariable Long id,
            @Valid @RequestBody FruitRequestDto requestDto
    ) {
        return ResponseEntity.ok(fruitService.updateFruit(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        fruitService.deleteFruit(id);
        return ResponseEntity.noContent().build();
    }
}