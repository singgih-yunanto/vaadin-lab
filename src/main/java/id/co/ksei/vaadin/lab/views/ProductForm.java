package id.co.ksei.vaadin.lab.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import id.co.ksei.vaadin.lab.model.Category;
import id.co.ksei.vaadin.lab.model.Product;
import id.co.ksei.vaadin.lab.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Route("product-form/:id?")
public class ProductForm extends VerticalLayout implements BeforeEnterObserver {
    @Autowired
    private ProductService productService;

    private Product product;

    private List<Category> categories;

    private Binder<Product> binder = new Binder<>();

    @PostConstruct
    public void init() {
        categories = productService.getCategories().block();

        initForm();

        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        saveButton.addClickListener(e -> {
            boolean isNew = product == null;
            Product product = isNew ? new Product() : this.product;
            if (binder.writeBeanIfValid(product)) {
                if (isNew) {
                    product = productService.addProduct(product).block();
                } else {
                    product = productService.addProduct(product).block();
                }
                Notification.show("Product " + product.getTitle() + " Saved Successfully.");
                UI.getCurrent().navigate(GridFromBackend.class);
            }
        });

        Button cancelButton = new Button("Cancel");

        cancelButton.addClickListener(e -> {
            UI.getCurrent().navigate(GridFromBackend.class);
        });

        add(new HorizontalLayout(saveButton, cancelButton));
    }

    private void initForm() {
        FormLayout layout = new FormLayout();
        add(layout);

        TextField title = new TextField("Title");
        binder.forField(title)
                .asRequired("Title is Required")
                .bind(Product::getTitle, Product::setTitle);
        layout.add(title);

        TextField description = new TextField("Description");
        binder.forField(description)
                .bind(Product::getDescription, Product::setDescription);
        layout.add(description);

        Select<Category> category = new Select<>();
        category.setLabel("Category");
        category.setItems(categories);
        category.setItemLabelGenerator(Category::getName);
        binder.forField(category)
                .asRequired("Category is Required")
                .bind(it -> categories.stream().filter(cat -> cat.getSlug().equals(it.getCategory())).findFirst().orElse(null),
                        (it, val) -> it.setCategory(val != null ? val.getSlug() : null));
        layout.add(category);

        TextField brand = new TextField("Brand");
        binder.forField(brand)
                .bind(Product::getBrand, Product::setBrand);
        layout.add(brand);

        BigDecimalField price = new BigDecimalField("Price");
        binder.forField(price)
                .asRequired("Price is Required")
                .bind(Product::getPrice, Product::setPrice);
        layout.add(price);

        IntegerField stock = new IntegerField("Stock");
        binder.forField(stock)
                .asRequired("Stock is Required")
                .bind(Product::getStock, Product::setStock);
        layout.add(stock);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Optional<Integer> id = beforeEnterEvent.getRouteParameters().get("id")
                .map(Integer::valueOf);
        if (id.isPresent()) {
            product = productService.getProductById(id.get()).block();
            if (product != null) {
                binder.readBean(product);
            }
        }
    }
}
