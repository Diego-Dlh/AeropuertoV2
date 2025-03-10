package edu.unimagdalena.actividadaeropuerto;

import org.springframework.boot.SpringApplication;

public class TestActividadAeropuertoApplication {

    public static void main(String[] args) {
        SpringApplication.from(ActividadAeropuertoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
