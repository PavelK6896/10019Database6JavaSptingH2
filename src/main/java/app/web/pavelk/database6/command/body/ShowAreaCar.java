package app.web.pavelk.database6.command.body;

import app.web.pavelk.database6.command.Execute;
import app.web.pavelk.database6.repo.CarRepo;
import app.web.pavelk.database6.schema.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ShowAreaCar implements Execute {

    private final CarRepo carRepo;

    @Override
    @Transactional
    public void execute() {

        List<Car> all = carRepo.findAll();
        all.forEach(f -> System.out.println(f.getAreas()));

    }
}
