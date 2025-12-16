package id.co.ksei.vaadin.lab.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Meta {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String barcode;
    private String qrCode;
}