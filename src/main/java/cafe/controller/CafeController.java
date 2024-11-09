package cafe.controller;

import cafe.cofeeData.*;
import cafe.cofeeData.db.CoffeeDbRepository;
import cafe.entity.CartEntity;
import cafe.entity.CoffeeEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RequestMapping("/coffee")
public class CafeController {
    private CoffeeDbRepository coffeeDbRepository;

    public CafeController(CoffeeDbRepository coffeeDbRepository) {
        this.coffeeDbRepository = coffeeDbRepository;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AddCoffeeResponse addCoffee(@RequestBody AddCoffeeRequest addCoffeeRequest){
        CoffeeEntity coffee = new CoffeeEntity();
        coffee.setName(addCoffeeRequest.getCoffee().getName());
        coffee.setDescription(addCoffeeRequest.getCoffee().getDescription());
        coffee.setPrice(addCoffeeRequest.getCoffee().getPrice());
        coffee.setImage(addCoffeeRequest.getCoffee().getImgURL());
        coffee.setUpdatedAt(Instant.now());
        coffee.setCreatedAt(Instant.now());
        coffeeDbRepository.save(coffee);

        AddCoffeeResponse response = new AddCoffeeResponse();
        response.setCoffee(addCoffeeRequest.getCoffee());

        return response;
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public GetAllCoffeeResponse getAllCoffee() {
        List<CoffeeEntity> coffeeEntityList = coffeeDbRepository.findAll();

        GetAllCoffeeResponse response = new GetAllCoffeeResponse();
        List<Coffee> coffeeList = new ArrayList<>();
        coffeeEntityList.forEach(coffeeEntity -> {
            Coffee coffee = new Coffee();
            coffee.setDescription(coffeeEntity.getDescription());
            coffee.setName(coffeeEntity.getName());
            coffee.setImgURL(coffeeEntity.getImage());
            coffee.setPrice(coffeeEntity.getPrice());
            coffee.setId(coffeeEntity.getId());
            coffeeList.add(coffee);
        });
        response.setCoffeeList(coffeeList);
        return response;
    }
    @PutMapping("/update")
    public void updateCoffee(@RequestParam Long id, @RequestBody UpdateCoffeeRequest updateCoffeeRequest) throws Exception {
        Optional<CoffeeEntity> coffeeOptional = coffeeDbRepository.findById(id);
        if (coffeeOptional.isPresent()) {
            CoffeeEntity coffee = coffeeOptional.get();
            coffee.setName(updateCoffeeRequest.getNewName());
            coffee.setPrice(updateCoffeeRequest.getNewPrice());
            coffee.setImage(updateCoffeeRequest.getNewImgURL());
            coffee.setDescription(updateCoffeeRequest.getNewDescription());
            System.out.println(coffee.toString());
            coffeeDbRepository.save(coffee);
        } else {
            throw new Exception("Coffee not present");
        }
    }

    @DeleteMapping("/delete")
    public String deleteCoffee(@RequestBody DeleteCoffee deleteCoffee) {
        Long coffee_id = deleteCoffee.getCoffee_id();
        Optional<CoffeeEntity> coffeeOptional = coffeeDbRepository.findById(coffee_id);
        if (coffeeOptional.isPresent()) {
            coffeeDbRepository.deleteById(coffee_id);
            return "Coffee item with id"+ coffee_id +"deleted successfully";
        }
        else {
            return "Coffee item with ID " + coffee_id + " not found.";
        }
    }





}

