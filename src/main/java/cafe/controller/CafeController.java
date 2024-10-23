package cafe.controller;

import cafe.data.AddCoffeeRequest;
import cafe.data.AddCoffeeResponse;
import cafe.data.Coffee;
import cafe.data.GetAllCoffeeResponse;
import cafe.data.db.CoffeeDbRepository;
import cafe.entity.CoffeeEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RequestMapping("/coffee")
public class CafeController {
    private final CoffeeDbRepository coffeeDbRepository;

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
    public GetAllCoffeeResponse getAllCoffee(){
        List<CoffeeEntity> coffeeEntityList = coffeeDbRepository.findAll();

        GetAllCoffeeResponse response = new GetAllCoffeeResponse();
        List<Coffee> coffeeList = new ArrayList<>();
        coffeeEntityList.forEach(coffeeEntity -> {
            Coffee coffee = new Coffee();
            coffee.setDescription(coffeeEntity.getDescription());
            coffee.setName(coffeeEntity.getName());
            coffee.setImgURL(coffeeEntity.getImage());
            coffee.setPrice(coffeeEntity.getPrice());
            coffeeList.add(coffee);
        });
        response.setCoffeeList(coffeeList);
        return response;
    }


}
