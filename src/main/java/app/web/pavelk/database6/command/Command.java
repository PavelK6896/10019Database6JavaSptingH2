package app.web.pavelk.database6.command;


import app.web.pavelk.database6.command.body.ShowAreaCar;
import app.web.pavelk.database6.command.body.ShowCarArea;
import app.web.pavelk.database6.command.body.UpdateCar1;
import app.web.pavelk.database6.repo.AreaRepo;
import app.web.pavelk.database6.repo.CarRepo;
import app.web.pavelk.database6.schema.ActionOne;
import app.web.pavelk.database6.schema.Area;
import app.web.pavelk.database6.schema.Car;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
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
    private final Map<String, JpaRepository<?, ?>> jpaMap;
    private final Scanner scanner;
    Map<String, CommandDecor> map = new HashMap<>();

    private void put(List<String> l, Execute e) {
        l.forEach(f -> map.put(f, CommandDecor.builder().execute(e).build()));
    }

    private void put(String description, List<String> l, Execute e) {
        l.forEach(f -> map.put(f, CommandDecor.builder().description(description).execute(e).build()));
    }

    private void put(Long id, String description, List<String> l, Execute e) {
        l.forEach(f -> map.put(f, CommandDecor.builder().id(id).key(f).description(description).execute(e).build()));
    }

    @PostConstruct
    private void generateDefault() {

        long id = 10L;
        for (Map.Entry<String, JpaRepository<?, ?>> entry : jpaMap.entrySet()) {
            String key = entry.getKey();
            JpaRepository<?, ?> value = entry.getValue();

            id++;

            put(id, key, List.of(key.substring(0, 1) + id), () -> {
                value.findAll().forEach(System.out::println);
            });

            put(id, key, List.of(key.substring(0, (key.length() - 4))), () -> {
                System.out.println(key);
                var next = scanner.next();
                if (next.equals("s")) {
                    System.out.println("s");
                    next = scanner.next();
                    if (next.equals("a")) {
                        value.findAll().forEach(System.out::println);
                    } else {
                        var value1 = (JpaRepository<Object, Object>) value;
                        System.out.println(value1.findById(Long.valueOf(next)).orElse(null));
                    }
                } else if (next.equals("i")) {
                    try {
                        var aPackage = ActionOne.class.getPackageName();
                        var listTable = Files.list(Path.of("src/main/java/app/web/pavelk/database6/schema"))
                                .map(m -> {
                                    var s = m.getFileName().toString();
                                    return s.substring(0, s.length() - 5);
                                }).collect(Collectors.toList());

                        for (String table : listTable) {
                            if (table.equalsIgnoreCase(key.substring(0, key.length() - 4))) {
                                var fullClass = aPackage + "." + table;
                                System.out.println(fullClass);
                                Class<?> aClass = Class.forName(fullClass);
                                Object o = aClass.getConstructor().newInstance();
                                Arrays.asList(o.getClass().getDeclaredFields())
                                        .forEach(f -> {
                                            var genericType = f.getType();
                                            var nextField = scanner.next();
                                            System.out.println(": " + nextField);
                                            Object object1 = null;
                                            if (f.getType().equals(Long.class)) {
                                                object1 = Long.valueOf(nextField);
                                            } else if (f.getType().equals(String.class)) {
                                                object1 = nextField;
                                            }
                                            try {
                                                f.setAccessible(true);
                                                f.set(o, object1);
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        });
                                var value1 = (JpaRepository<Object, Object>) value;
                                value1.saveAndFlush(o);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (next.equals("u")) {

                } else if (next.equals("d")) {

                }
            });
        }
    }

    public Map<String, CommandDecor> generate() {
        put(104L, "show car", List.of("c1", "car"), new Execute() {
            @Override
            public void execute() {
                carRepo.findAll().forEach(System.out::println);
            }
        });

        put(105L, "show area", List.of("2", "show area"), () -> areaRepo.findAll().forEach(System.out::println));
        put(List.of("3", "show are in car"), showAreaCar);
        put(List.of("4", "show car in area"), showCarArea);
        put(List.of("4", "show car in area"), updateCar1);

        put(List.of("5", "create car"), () -> {
            PlatformTransactionManager transactionManager = transactionTemplate.getTransactionManager();
            if (transactionManager == null) throw new AssertionError();
            TransactionStatus transactionStatus = transactionManager.getTransaction(transactionTemplate);
            Car aNew = Car.builder()
                    .name("new")
                    .areas(Set.of(Area.builder().code(100L).build(), Area.builder().code(200L).build()))
                    .build();
            carRepo.saveAndFlush(aNew);
            transactionManager.commit(transactionStatus);

        });

        put(List.of("6", "update are in car"), updateCar1);

        put(List.of("7", "show area code in car"), () -> {
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
        });

        put("update car in area", List.of("8", "update car in area"), () -> {
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

        });

        put(1L, "help", List.of("help", "h"), () -> {
            Map<Long, List<CommandDecor>> collect = map.values()
                    .stream()
                    .filter(f -> f.getId() != null)
                    .collect(Collectors.groupingBy(CommandDecor::getId));
            collect.entrySet()
                    .forEach(f -> System.out.println(f.getValue().stream().map(m -> m.getKey()).collect(Collectors.joining(", ")) + " = " +
                            f.getValue().stream().map(m -> m.getDescription()).distinct().collect(Collectors.joining(" "))));

            System.out.println();
        });


        return map;
    }


}
