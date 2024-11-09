package cafe.controller;

import cafe.cofeeData.*;
import cafe.cofeeData.db.UserDbRepository;
import cafe.entity.CartEntity;
import cafe.entity.CoffeeEntity;
import cafe.entity.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RequestMapping("/user")

public class UserController {

    private final UserDbRepository userDbRepository;

    public UserController(UserDbRepository userDbRepository) {
        this.userDbRepository = userDbRepository;
        // constructor for initializing the object
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )

    public AddUserResponse addUserResponse(@RequestBody AddUserRequest addUserRequest) {
        UserEntity user = new UserEntity();
        user.setName(addUserRequest.getUser().getName());
        user.setPhone_num(String.valueOf(addUserRequest.getUser().getPhoneNum()));
        user.setUpdatedAt(Instant.now());
        user.setCreatedAt(Instant.now());
        userDbRepository.save(user);

        AddUserResponse response = new AddUserResponse();
        response.setUser(addUserRequest.getUser());

        return response;
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )

    public GetAllUserResponse getAllUser() {
        List<UserEntity> userEntityList = userDbRepository.findAll();

        GetAllUserResponse response = new GetAllUserResponse();
        List<User> userList = new ArrayList<>();
        userEntityList.forEach(userEntity -> {
            User user = new User();
            user.setName(userEntity.getName());
            user.setPhoneNum(Integer.valueOf(userEntity.getPhone_num()));
            userList.add(user);
        });
        response.setUserList(userList);
        return response;
    }

    @GetMapping(
            path = "/phone",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public User getUserByPhoneNumber(@RequestParam Integer phoneNumber){
        UserEntity userEntity = userDbRepository.findByPhoneNumber(phoneNumber.toString());
        User user = new User();
        user.setName(userEntity.getName());
        user.setPhoneNum(Integer.valueOf(userEntity.getPhone_num()));
        return user;

    }

}
