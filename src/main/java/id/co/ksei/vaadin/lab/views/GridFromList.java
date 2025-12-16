package id.co.ksei.vaadin.lab.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import id.co.ksei.vaadin.lab.model.Person;
import net.datafaker.Faker;

@Route("grid-from-list")
public class GridFromList extends VerticalLayout {
    private final Faker faker = new Faker();

    public GridFromList() {

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
