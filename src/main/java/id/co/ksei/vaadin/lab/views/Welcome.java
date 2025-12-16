package id.co.ksei.vaadin.lab.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/")
public class Welcome extends VerticalLayout {
    public Welcome() {
        add(new H1("Welcome to Vaadin!"));
    }
}
