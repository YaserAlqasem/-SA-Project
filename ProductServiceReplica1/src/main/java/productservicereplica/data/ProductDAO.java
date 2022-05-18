package productservicereplica.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import productservicereplica.domain.Product;

public interface ProductDAO extends MongoRepository<Product, String> {

    Product findByProductNumber(String productNumber);
}
