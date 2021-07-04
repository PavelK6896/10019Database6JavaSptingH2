package app.web.pavelk.database6.command.body;


import app.web.pavelk.database6.command.Execute;
import app.web.pavelk.database6.repo.CarRepo;
import app.web.pavelk.database6.schema.Area;
import app.web.pavelk.database6.schema.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UpdateCar1 implements Execute {

    private final CarRepo carRepo;
    private final TransactionTemplate transactionTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute() {

        System.out.println(transactionTemplate.getIsolationLevel());
        System.out.println(transactionTemplate.getPropagationBehavior());

        List<Car> all = carRepo.findAll();
        System.out.println(all.size());
        Car car = all.get(1);
        Set<Area> areas = car.getAreas();
        areas.forEach(f -> f.setCode(990099L));
        carRepo.saveAndFlush(car);

    }

}
