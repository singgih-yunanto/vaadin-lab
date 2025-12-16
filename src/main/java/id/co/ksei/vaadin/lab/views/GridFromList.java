package id.co.ksei.vaadin.lab.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import id.co.ksei.vaadin.lab.model.Person;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;

@Route("grid-from-list")
public class GridFromList extends VerticalLayout {
    private final Faker faker = new Faker();

    public GridFromList() {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            persons.add(createPerson());
        }

        ListDataProvider<Person> dataProvider = new ListDataProvider<>(persons);

        Grid<Person> grid = new Grid<>();
        grid.addColumn(Person::getFirstName).setHeader("First Name");
        grid.addColumn(Person::getLastName).setHeader("Last Name");
        grid.addColumn(Person::getEmail).setHeader("Email");
        grid.addColumn(Person::getProfession).setHeader("Profession");
        grid.setDataProvider(dataProvider);

        Button addPerson = new Button("Add Person");
        add(addPerson);
        addAndExpand(grid);

        addPerson.addClickListener(e -> {
            persons.add(0, createPerson());
            dataProvider.refreshAll();
        });
    }

    private Person createPerson() {
        Person person = new Person();
        person.setFirstName(faker.name().firstName());
        person.setLastName(faker.name().lastName());
        person.setEmail(faker.internet().emailAddress());
        person.setProfession(faker.job().title());
        return person;
    }
}
