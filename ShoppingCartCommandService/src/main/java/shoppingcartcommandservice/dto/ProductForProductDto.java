package shoppingcartcommandservice.dto;

import shoppingcartcommandservice.domain.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductForProductDto {
    private List<Product> products;
}
