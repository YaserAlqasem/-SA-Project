package shoppingcartqueryservice.dao;

import shoppingcartqueryservice.domain.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartDAO extends MongoRepository<ShoppingCart, Long> {
}
