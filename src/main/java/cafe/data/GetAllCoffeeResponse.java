package cafe.data;

import lombok.Data;

import java.util.List;

@Data
public class GetAllCoffeeResponse {
    List<Coffee> coffeeList;
}
