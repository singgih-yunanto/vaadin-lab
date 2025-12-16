package id.co.ksei.vaadin.lab.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
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
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        add(new HorizontalLayout(searchField, searchButton));

        DataProvider<Product, String> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> productService.getProducts(query.getFilter().orElse(null), query.getLimit(), query.getOffset()).block().getProducts().stream(),
                query -> productService.getProducts(query.getFilter().orElse(null), 1, 0).block().getTotal()
        );
        ConfigurableFilterDataProvider<Product, Void, String> filterableDataProvider = dataProvider.withConfigurableFilter();

        List<Category> categories = productService.getCategories().block();

        Grid<Product> grid = new Grid<>();
        grid.addColumn(Product::getTitle).setHeader("Title");
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
    }
}
