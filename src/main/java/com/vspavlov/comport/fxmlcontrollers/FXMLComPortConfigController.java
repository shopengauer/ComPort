package com.vspavlov.comport.fxmlcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vasiliy on 15.06.15.
 */

public class FXMLComPortConfigController implements Initializable,ApplicationEventPublisherAware {

    @FXML
    private AnchorPane view;

    @FXML
    private Button btn;
    @Autowired
    private FXMLMainController mainController;


    //
    @FXML
    void handleBtn(ActionEvent event) {

        Stage stage = (Stage)btn.getScene().getWindow();

        stage.close();
     //   Stage stage = (Stage)mainController.getView().getScene().getWindow();
       // view.getScene().getWindow().hide();

    }




    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public AnchorPane getView() {
        return view;
    }
}
