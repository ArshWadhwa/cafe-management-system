package cafe.cofeeData.db;

import cafe.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDbRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select * from customer where phone_num = ?1", nativeQuery = true)
    public UserEntity findByPhoneNumber(String phoneNumber);

}
