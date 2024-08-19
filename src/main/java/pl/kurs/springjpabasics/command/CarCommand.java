package pl.kurs.springjpabasics.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarCommand {
    private String producer;
    private String model;
}
