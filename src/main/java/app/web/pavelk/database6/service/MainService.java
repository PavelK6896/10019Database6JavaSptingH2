package app.web.pavelk.database6.service;


import app.web.pavelk.database6.command.Command;
import app.web.pavelk.database6.command.Execute;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class MainService implements CommandLineRunner {

    private final Scanner scanner;
    private final Command command;

    @Override
    public void run(String... args) throws Exception {
        Map<String, Execute> generate = command.generate();
        while (true) {
            TimeUnit.MILLISECONDS.sleep(500);
            String next = scanner.next();
            Execute execute = generate.get(next);
            if (execute != null) {
                execute.execute();
            }
        }
    }
}