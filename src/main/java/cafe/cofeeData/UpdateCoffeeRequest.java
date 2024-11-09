package cafe.cofeeData;

import lombok.Data;

@Data
public class UpdateCoffeeRequest {
    private String newName;
    private Double newPrice;
    private String newDescription;
    private String newImgURL;
}
