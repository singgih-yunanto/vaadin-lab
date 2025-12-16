package id.co.ksei.vaadin.lab.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import id.co.ksei.vaadin.lab.model.Category;
import id.co.ksei.vaadin.lab.model.Product;
import id.co.ksei.vaadin.lab.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@Route("grid-from-backend")
public class GridFromBackend extends VerticalLayout {
    @Autowired
    private ProductService productService;

    @PostConstruct
    public void init() {
        HorizontalLayout topLayout = new HorizontalLayout();
        add(topLayout);

        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        topLayout.addAndExpand(new HorizontalLayout(searchField, searchButton));

        Button addButton = new Button("Add New Product");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        topLayout.add(addButton);

        DataProvider<Product, String> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> productService.getProducts(query.getFilter().orElse(null), query.getLimit(), query.getOffset()).block().getProducts().stream(),
                query -> productService.getProducts(query.getFilter().orElse(null), 1, 0).block().getTotal()
        );
        ConfigurableFilterDataProvider<Product, Void, String> filterableDataProvider = dataProvider.withConfigurableFilter();

        List<Category> categories = productService.getCategories().block();

        Grid<Product> grid = new Grid<>();
        grid.addColumn(new ComponentRenderer<>(it -> {
            Button button = new Button(it.getTitle());
            button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

            button.addClickListener(e -> {
                UI.getCurrent().navigate(ProductForm.class,
                        new RouteParameters("id", it.getId().toString()));
            });

            return button;
        })).setHeader("Title");
        grid.addColumn(Product::getBrand).setHeader("Brand");
        grid.addColumn(it -> categories.stream()
                        .filter(category -> category.getSlug().equals(it.getCategory()))
                        .findFirst()
                        .map(Category::getName)
                        .orElse(""))
                .setHeader("Category");
        grid.addColumn(Product::getPrice).setHeader("Price").setTextAlign(ColumnTextAlign.END);
        grid.addColumn(Product::getStock).setHeader("Stock").setTextAlign(ColumnTextAlign.END);
        grid.addColumn(Product::getRating).setHeader("Rating").setTextAlign(ColumnTextAlign.END);
        grid.setDataProvider(filterableDataProvider);

        addAndExpand(grid);

        searchButton.addClickListener(e -> {
            filterableDataProvider.setFilter(searchField.getValue());
        });

        addButton.addClickListener(e -> {
            UI.getCurrent().navigate(ProductForm.class);
        });
    }
}
