package app.web.pavelk.database6.command.body;

import app.web.pavelk.database6.command.Execute;
import app.web.pavelk.database6.repo.AreaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ShowCarArea implements Execute {

    private final AreaRepo areaRepo;

    @Override
    @Transactional
    public void execute() {
        areaRepo.findAll()
                .forEach(f -> {
                    System.out.println(f);
                    System.out.println(f.getCar());
                });
    }


}
