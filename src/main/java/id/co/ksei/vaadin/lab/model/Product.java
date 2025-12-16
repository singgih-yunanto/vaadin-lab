package id.co.ksei.vaadin.lab.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Product {
    private Long id;
    private String title;
    private String description;
    private String category;
    private BigDecimal price;
    private BigDecimal discountPercentage;
    private Double rating;
    private Integer stock;
    private List<String> tags;
    private String brand;
    private String sku;
    private Double weight;
    private Dimensions dimensions;
    private String warrantyInformation;
    private String shippingInformation;
    private String availabilityStatus;
    private List<Review> reviews;
    private String returnPolicy;
    private Integer minimumOrderQuantity;
    private Meta meta;
    private String thumbnail;
    private List<String> images;
}
