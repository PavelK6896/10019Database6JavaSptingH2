package app.web.pavelk.database6.repo;


import app.web.pavelk.database6.schema.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Car, Long> {
}
