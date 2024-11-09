package cafe.cofeeData;

import lombok.Data;

@Data
public class Coffee {
    Long cartId;
    Long id;
    String name;
    Double price;
    String description;
    String imgURL;
}

