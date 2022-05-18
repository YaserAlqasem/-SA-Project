package productservicereplica.service;

import productservicereplica.data.ProductDAO;
import productservicereplica.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDAO productDao;

    public void addProduct(Product product) {
        productDao.save(product);
    }

    public Product findProductByNumber(String productNumber) {
        return productDao.findByProductNumber(productNumber);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void deleteProduct(Product product) {
        productDao.delete(product);
    }
}
