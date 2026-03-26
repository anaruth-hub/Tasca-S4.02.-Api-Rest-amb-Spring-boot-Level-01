package cat.itacademy.s04.t02.n03.repository;

import cat.itacademy.s04.t02.n03.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}