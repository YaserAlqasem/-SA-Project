package productservicereplica2.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import productservicereplica2.domain.Product;

public interface ProductDAO extends MongoRepository<Product, String> {

    Product findByProductNumber(String productNumber);
}
