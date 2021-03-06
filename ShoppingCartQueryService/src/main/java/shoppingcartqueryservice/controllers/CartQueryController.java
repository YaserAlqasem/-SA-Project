package shoppingcartqueryservice.controllers;

import shoppingcartqueryservice.domain.ShoppingCart;
import shoppingcartqueryservice.service.CartQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartQueryController {

    @Autowired
    private CartQueryService cartQueryService;

    private static final Logger log = LoggerFactory.getLogger(CartQueryService.class);

    @GetMapping("/{cartNumber}")
    public ResponseEntity<?> getCart(@PathVariable Long cartNumber){
        log.info("GET request for /carts/" + cartNumber);
        if(cartQueryService.getShoppingCart(cartNumber) !=null){
            return new ResponseEntity<ShoppingCart>(cartQueryService.getShoppingCart(cartNumber), HttpStatus.OK);
        }else{
            log.error("COULDN'T GET CARTS");
            return new ResponseEntity<String>("COULDN'T GET CART", HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping()
    public ResponseEntity<?> getCart(){
        log.info("GET request for /carts");
        if(cartQueryService.getShoppingCarts() !=null){
            return new ResponseEntity<List<ShoppingCart>>(cartQueryService.getShoppingCarts(), HttpStatus.OK);
        }else{
            log.error("COULDN'T GET CARTS");
            return new ResponseEntity<String>("COULDN'T GET CARTS", HttpStatus.BAD_REQUEST);
        }

    }

}
