package orderservice.data;

import orderservice.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Order findByOrderNumber(String orderNumber);

    List<Order> findByCustomerID(String customerID);
}
