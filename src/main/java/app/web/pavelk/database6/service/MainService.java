package app.web.pavelk.database6.service;


import app.web.pavelk.database6.command.Command;
import app.web.pavelk.database6.command.CommandDecor;
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
        Map<String, CommandDecor> generate = command.generate();
        while (true) {
            TimeUnit.MILLISECONDS.sleep(500);
            String next = scanner.next();
            if (next.equals("exit")) {
                System.exit(0);
                break;
            }
            Execute execute = generate.getOrDefault(next, generate.get("help")).getExecute();
            if (execute != null) {
                execute.execute();
            }

        }
    }
}