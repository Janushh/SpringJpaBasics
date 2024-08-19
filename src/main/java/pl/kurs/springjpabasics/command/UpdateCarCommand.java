package pl.kurs.springjpabasics.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCarCommand {
    private int id;
    private String producer;
    private String model;
}
