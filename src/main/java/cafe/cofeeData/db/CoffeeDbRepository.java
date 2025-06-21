package cafe.cofeeData.db;

import cafe.cofeeData.Coffee;
import cafe.entity.CoffeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeDbRepository extends JpaRepository<CoffeeEntity, Long> {


}