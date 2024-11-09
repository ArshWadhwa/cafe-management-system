package cafe.controller;

import cafe.cofeeData.*;
import cafe.cofeeData.db.CartDbRepository;
import cafe.cofeeData.db.CoffeeDbRepository;
import cafe.entity.CartEntity;
import cafe.entity.CoffeeEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RequestMapping("/cart")
public class CartController {
    private CartDbRepository cartDbRepository;
    private CoffeeDbRepository coffeeDbRepository;

    public CartController(CartDbRepository cartDbRepository, CoffeeDbRepository coffeeDbRepository) {
        this.cartDbRepository = cartDbRepository;
        this.coffeeDbRepository= coffeeDbRepository;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public void AddCartResponse (@RequestBody AddCartRequest addCartRequest){
        System.out.println("Add cart Requested for id: " + addCartRequest.getCart().getCoffee_id());
        CartEntity cart = new CartEntity();

        cart.setQuantity(addCartRequest.getCart().getQuantity());
        cart.setCoffee_id(addCartRequest.getCart().getCoffee_id());
        cart.setUpdatedAt(Instant.now());
        cart.setCreatedAt(Instant.now());
        cartDbRepository.save(cart);

        return;



    }
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public GetAllCartResponse getAllCart() {

        GetAllCartResponse response = new GetAllCartResponse();
        List<CoffeeCart> coffeeCarts = getCoffeeCarts();
        Double bill = getBill(coffeeCarts);
        response.setCoffeeCart(coffeeCarts);
        response.setBill(bill);
        return response;

    }

    private Double getBill(List<CoffeeCart> carts){
        Double bill = 0.0;
        for (int i = 0; i< carts.size();i++){
            if (carts.get(i).getCoffee() != null)
            bill += carts.get(i).getCoffee().getPrice() * carts.get(i).getQuantity();
        }
        return bill;
    }

    private List<CoffeeCart> getCoffeeCarts(){
        List<CoffeeCart> coffeeCarts = new ArrayList<>();
        // 1. Get all carts
       List<CartEntity> cartEntities = cartDbRepository.findAll();

       cartEntities.forEach(cart -> {
          CoffeeCart coffeeCart = getCoffeeCartUsingCoffeeDetails(cart);
          coffeeCarts.add(coffeeCart);

       });
       return coffeeCarts;
    }

    private CoffeeCart getCoffeeCartUsingCoffeeDetails(CartEntity cart) {
        CoffeeCart coffeeCart = new CoffeeCart();

        Optional<CoffeeEntity> coffee = coffeeDbRepository.findById(cart.getCoffee_id());
        if (coffee.isPresent()){

            // set coffee in coffeeCart
            Coffee coffee1 = new Coffee();
            coffee1.setDescription(coffee.get().getDescription());
            coffee1.setId(coffee.get().getId());
            coffee1.setName(coffee.get().getName());
            coffee1.setPrice(coffee.get().getPrice());
            coffee1.setImgURL(coffee.get().getImage());
            coffee1.setCartId(cart.getId());
            coffeeCart.setCoffee(coffee1);

            // set quantity
            coffeeCart.setQuantity(cart.getQuantity());

        }

        return coffeeCart;

    }

    @DeleteMapping()
    public String deleteCoffee(@RequestBody DeleteCoffee deleteCoffee) {
        Long coffee_id = deleteCoffee.getCoffee_id();
        Optional<CartEntity> cartOptional = cartDbRepository.findById(coffee_id);
        if (cartOptional.isPresent()) {
            cartDbRepository.deleteById(coffee_id);
            return "Coffee item with id"+ coffee_id +"deleted successfully";
        }
        else {
            return "Coffee item with ID " + coffee_id + " not found.";
        }
    }

}
