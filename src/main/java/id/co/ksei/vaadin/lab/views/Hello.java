package id.co.ksei.vaadin.lab.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Route("hello")
public class Hello extends VerticalLayout {
    public Hello() {
        TextField name = new TextField();
        Button button = new Button("Say Hello");
        Button superButton = new Button("Super Hello");
        HorizontalLayout horizontalLayout = new HorizontalLayout(name, button, superButton);
        add(horizontalLayout);

        Label greeting = new Label();
        add(greeting);

        superButton.setEnabled(false);

        button.addClickListener(e -> {
            greeting.setText("Hello " + name.getValue());
        });

        superButton.addClickListener(e -> {
            greeting.setText("Hello " + name.getValue() + ", now is " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        });

        name.addValueChangeListener(e -> {
            superButton.setEnabled("KSEI".equals(e.getValue()));
        });
    }
}
