package id.co.ksei.vaadin.lab.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private int rating;
    private String comment;
    private LocalDateTime date;
    private String reviewerName;
    private String reviewerEmail;
}