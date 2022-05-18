package customerservice.data;

import customerservice.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findBycId(String cid);
}
