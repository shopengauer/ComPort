package com.vspavlov.comport.fxmlcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vasiliy on 06.07.2015.
 */
public class FXMLTabController implements Initializable,ApplicationEventPublisherAware {

    @FXML
    private Tab tab;

    public Tab getTab() {
        return tab;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
