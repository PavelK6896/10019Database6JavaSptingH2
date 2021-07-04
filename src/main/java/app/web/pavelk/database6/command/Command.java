package app.web.pavelk.database6.command;


import app.web.pavelk.database6.command.body.ShowAreaCar;
import app.web.pavelk.database6.command.body.ShowCarArea;
import app.web.pavelk.database6.command.body.UpdateCar1;
import app.web.pavelk.database6.repo.AreaRepo;
import app.web.pavelk.database6.repo.CarRepo;
import app.web.pavelk.database6.schema.Area;
import app.web.pavelk.database6.schema.Car;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Component
@RequiredArgsConstructor
public class Command {

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;
    private final AreaRepo areaRepo;
    private final CarRepo carRepo;
    private final ShowAreaCar showAreaCar;
    private final ShowCarArea showCarArea;
    private final UpdateCar1 updateCar1;

    Map<String, Execute> map = new HashMap<>();

    private void put(Execute e, List<String> l) {
        l.forEach(f -> map.put(f, e));
    }

    public Map<String, Execute> generate() {
        put(new Execute() {
            @Override
            public void execute() {
                carRepo.findAll().forEach(System.out::println);
            }
        }, List.of("1", "show car"));

        put(() -> areaRepo.findAll().forEach(System.out::println), List.of("2", "show area"));
        put(showAreaCar, List.of("3", "show are in car"));
        put(showCarArea, List.of("4", "show car in area"));
        put(updateCar1, List.of("4", "show car in area"));

        put(() -> {
            PlatformTransactionManager transactionManager = transactionTemplate.getTransactionManager();
            if (transactionManager == null) throw new AssertionError();
            TransactionStatus transactionStatus = transactionManager.getTransaction(transactionTemplate);
            Car aNew = Car.builder()
                    .name("new")
                    .areas(Set.of(Area.builder().code(100L).build(), Area.builder().code(200L).build()))
                    .build();
            carRepo.saveAndFlush(aNew);
            transactionManager.commit(transactionStatus);

        }, List.of("5", "create car"));

        put(updateCar1, List.of("6", "update are in car"));

        put(() -> {
            System.out.println(transactionTemplate.getIsolationLevel());
            System.out.println(transactionTemplate.getPropagationBehavior());
            PlatformTransactionManager transactionManager = transactionTemplate.getTransactionManager();
            if (transactionManager == null) throw new AssertionError();
            TransactionStatus transactionStatus = transactionManager.getTransaction(transactionTemplate);
            System.out.println(transactionStatus.hasSavepoint());
            System.out.println(transactionStatus.isNewTransaction());
            List<Long> collect = carRepo.findAll().stream().flatMap(f -> f.getAreas().stream()).map(m -> {
                System.out.println(m.getCode());
                return m.getId();
            }).collect(Collectors.toList());
            transactionManager.commit(transactionStatus);
        }, List.of("7", "show area code in car"));

        put(() -> {
            PlatformTransactionManager transactionManager = transactionTemplate.getTransactionManager();
            if (transactionManager == null) throw new AssertionError();
            TransactionStatus transactionStatus = transactionManager.getTransaction(transactionTemplate);
            try {
                Area area = areaRepo.findAll().get(0);
                area.setCar(Car.builder().name("1111111111").build());
                areaRepo.saveAndFlush(area);
            } catch (Exception e) {
                transactionManager.rollback(transactionStatus);
                e.printStackTrace();

                Area area = areaRepo.findAll().get(0);
                area.setIdCar(6000L);
                areaRepo.saveAndFlush(area);
                System.out.println("--------------------------");
                System.out.println(e.getMessage());
            }

        }, List.of("8", "update car in area"));


//        put(() -> {
//
//        }, List.of("8", "update car"));


        return map;
    }


}
