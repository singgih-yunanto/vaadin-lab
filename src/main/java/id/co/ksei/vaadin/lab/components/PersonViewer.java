package id.co.ksei.vaadin.lab.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import id.co.ksei.vaadin.lab.model.Person;

import java.util.Optional;

public class PersonViewer extends VerticalLayout {
    private Optional<Person> person = Optional.empty();

    public PersonViewer() {
        add(createTextField("First Name", person.map(Person::getFirstName).orElse("")));
        add(createTextField("Last Name", person.map(Person::getLastName).orElse("")));
        add(createTextField("Email", person.map(Person::getEmail).orElse("")));
        add(createTextField("Profession", person.map(Person::getProfession).orElse("")));
    }

    private TextField createTextField(String label, String value) {
        TextField textField = new TextField(label);
        textField.setValue(value);
        textField.setReadOnly(true);
        return textField;
    }

    public void setPerson(Person person) {
        this.person = Optional.ofNullable(person);
    }
}
