package id.co.ksei.vaadin.lab.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import id.co.ksei.vaadin.lab.components.PersonViewer;
import id.co.ksei.vaadin.lab.model.Person;
import net.datafaker.Faker;

import java.time.LocalDate;

@Route("person-form")
public class PersonForm extends HorizontalLayout {
    private final PersonViewer personViewer = new PersonViewer();
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final DatePicker birthDate = new DatePicker("Birth Date");
    private final TextField email = new TextField("Email");
    private final TextField profession = new TextField("Profession");
    private final Button saveButton = new Button("Save");
    private final Button dummyButton = new Button("Load Dummy");
    private final Binder<Person> binder = new Binder<>();

    public PersonForm() {
        FormLayout formLayout = new FormLayout();
        VerticalLayout layout = new VerticalLayout(formLayout, new HorizontalLayout(saveButton, dummyButton));
        addAndExpand(layout);

        formLayout.add(firstName, lastName, birthDate, email, profession);

        personViewer.setWidth(null);
        add(personViewer);

        binder.forField(firstName)
                .asRequired("First name is required")
                .bind(Person::getFirstName, Person::setFirstName);

        binder.forField(lastName)
                .bind(Person::getLastName, Person::setLastName);

        binder.forField(birthDate)
                .asRequired("Birth date is required")
                .withValidator(it -> LocalDate.now().isAfter(it),
                        "Birth date must be in the past")
                .bind(Person::getBirthDate, Person::setBirthDate);

        binder.forField(email)
                .asRequired("Email is required")
                .withValidator(new EmailValidator("Email is not valid"))
                .bind(Person::getEmail, Person::setEmail);

        binder.forField(profession)
                .asRequired("Profession is required")
                .bind(Person::getProfession, Person::setProfession);

        saveButton.addClickListener(e -> {
            Person person = new Person();
            if (binder.writeBeanIfValid(person)) {
                personViewer.setPerson(person);
            }
        });

        Faker faker = new Faker();
        dummyButton.addClickListener(e -> {
            Person person = new Person();
            person.setFirstName(faker.name().firstName());
            person.setLastName(faker.name().lastName());
            person.setBirthDate(faker.date().birthday().toLocalDateTime().toLocalDate());
            person.setEmail(faker.internet().emailAddress());
            person.setProfession(faker.job().title());

            binder.readBean(person);
        });
    }
}
