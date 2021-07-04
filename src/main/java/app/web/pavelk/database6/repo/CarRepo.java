package app.web.pavelk.database6.repo;


import app.web.pavelk.database6.schema.Car;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CarRepo extends Repository<Car, Long> {
    List<Car> findAll();
    Car saveAndFlush(Car car);
}
