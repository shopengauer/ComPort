package com.vspavlov.comport.fxmlcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private Button closeBtn;

    @FXML
    private Button resetBtn;

    @FXML
    private ComboBox<String> dataBitsCombo;

    @FXML
    private ComboBox<String> parityCombo;

    @FXML
    private Label comPortLabel;

    @FXML
    private ComboBox<String> baudRateCombo;

    @FXML
    private ComboBox<String> stopBitsCombo;

    @Autowired
    private FXMLMainController mainController;



    @FXML
    void handleCloseBtn(ActionEvent event) {
       Stage stage = (Stage)view.getScene().getWindow();
       stage.close();
    }

    @FXML
    void handleResetBtn(ActionEvent event) {

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

    public Label getComPortLabel() {
        return comPortLabel;
    }


    public ComboBox<String> getBaudRateCombo() {
        return baudRateCombo;
    }

    public void setBaudRateCombo(ComboBox<String> baudRateCombo) {
        this.baudRateCombo = baudRateCombo;
    }
}
