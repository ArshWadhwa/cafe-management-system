package cafe.cofeeData.db;

import cafe.entity.CartEntity;
import cafe.entity.CoffeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDbRepository extends JpaRepository<CartEntity, Long> {

}