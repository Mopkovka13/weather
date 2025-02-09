package ru.evgeniy.weather.view;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("")
@Route("")
@CssImport("themes/my-theme/index.css")
public class MainView extends VerticalLayout {
    private WeatherView weatherView;

    public MainView() {
        weatherView = new WeatherView();

        add(weatherView);
    }
}
