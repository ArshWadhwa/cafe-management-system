package cafe.cofeeData;

import lombok.Data;

import java.util.List;

@Data
public class GetAllUserResponse {
    List<User> userList;
}
