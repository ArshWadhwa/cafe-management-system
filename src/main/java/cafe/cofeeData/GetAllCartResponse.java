package cafe.cofeeData;

import lombok.Data;

import java.util.List;

@Data
public class GetAllCartResponse {
    List<CoffeeCart> coffeeCart;
    Double bill;
}
