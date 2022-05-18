package shoppingcartcommandservice.dto;

import shoppingcartcommandservice.domain.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductForOrderDto {
    private String customerId;
    private List<Product> products;
}
