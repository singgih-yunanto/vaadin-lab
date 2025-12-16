package id.co.ksei.vaadin.lab.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String profession;
}
