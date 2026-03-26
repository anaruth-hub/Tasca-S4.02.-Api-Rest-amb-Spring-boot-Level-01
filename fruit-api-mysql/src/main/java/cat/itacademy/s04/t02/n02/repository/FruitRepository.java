package cat.itacademy.s04.t02.n02.repository;

import cat.itacademy.s04.t02.n02.model.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
    List<Fruit> findByProviderId(Long providerId);
    boolean existsByProviderId(Long providerId);
}