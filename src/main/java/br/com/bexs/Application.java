package br.com.bexs;

import br.com.bexs.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;


@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    RouteService routeService;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length == 1) {
            Scanner scanner = new Scanner(System.in);
            routeService.readFile(args[0]);
            System.out.println("please enter the route: ");
            while (scanner.hasNext()) {
                routeService.process(args[0], scanner.next());
                System.out.println("try again: ");
            }
        }
    }

}
