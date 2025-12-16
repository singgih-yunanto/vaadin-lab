package id.co.ksei.vaadin.lab.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import id.co.ksei.vaadin.lab.model.Person;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PersonViewer extends VerticalLayout {
    private Optional<Person> person = Optional.empty();

    public PersonViewer() {
        loadPerson();
    }

    private TextField createTextField(String label, String value) {
        TextField textField = new TextField(label);
        textField.setValue(value);
        textField.setReadOnly(true);
        return textField;
    }

    public void setPerson(Person person) {
        this.person = Optional.ofNullable(person);
        loadPerson();
    }

    private void loadPerson() {
        removeAll();
        add(createTextField("First Name", person.map(Person::getFirstName).orElse("")));
        add(createTextField("Last Name", person.map(Person::getLastName).orElse("")));
        add(createTextField("Birth Date", person.map(Person::getBirthDate).map(it -> it.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).orElse("")));
        add(createTextField("Email", person.map(Person::getEmail).orElse("")));
        add(createTextField("Profession", person.map(Person::getProfession).orElse("")));
    }
}
