package pl.kurs.springjpabasics.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {
    private int id;
    private String producer;
    private String model;
}
