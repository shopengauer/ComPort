package com.vspavlov.comport.fxmlcontrollers;

import com.vspavlov.comport.serial.Stopbits;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jssc.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vasiliy on 15.06.15.
 */

public class FXMLComPortConfigController implements Initializable,ApplicationEventPublisherAware {

    @FXML
    private AnchorPane view;


    @FXML
    private ComboBox<Integer> baudRateCombo;
    @FXML
    private ComboBox<Integer> dataBitsCombo;
    @FXML
    private ComboBox<String> stopBitsCombo;
    @FXML
    private ComboBox<String> parityCombo;
    @FXML
    private Button closeBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Label comPortLabel;





    @Autowired
    private FXMLMainController mainController;

    private ObservableMap<Object,Object> observableComPortMapProps;

    private Logger logger = LoggerFactory.getLogger(FXMLComPortConfigController.class);

    @FXML
    void handleCloseBtn(ActionEvent event) {
       Stage stage = (Stage)view.getScene().getWindow();
       stage.close();
    }

    @FXML
    void handleResetBtn(ActionEvent event) {
       // System.out.println((String) comPortLabel.getProperties().get("com"));
       // comPortLabel.setText((String) comPortLabel.getProperties().get("com"));
    }



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

    @PostConstruct
    private void init(){
        ObservableList<Integer> olBaudRate = FXCollections.observableArrayList();


        olBaudRate.addAll(SerialPort.BAUDRATE_1200, SerialPort.BAUDRATE_4800,
                SerialPort.BAUDRATE_9600, SerialPort.BAUDRATE_14400, SerialPort.BAUDRATE_19200);
        baudRateCombo.setItems(olBaudRate);

        ObservableList<Integer> olDatabits = FXCollections.observableArrayList();
        olDatabits.addAll(SerialPort.DATABITS_5,SerialPort.DATABITS_6,
                SerialPort.DATABITS_7,SerialPort.DATABITS_8);
        dataBitsCombo.setItems(olDatabits);

//        ObservableList<Integer> olStopbits = FXCollections.observableArrayList();
//        olStopbits.addAll(SerialPort.STOPBITS_1,SerialPort.STOPBITS_1_5,
//                SerialPort.STOPBITS_2);
//        stopBitsCombo.setItems(olStopbits);
 ObservableList<String> olStopbits = FXCollections.observableArrayList();
        olStopbits.addAll(Stopbits.STOPBITS_1.getLabel(),Stopbits.STOPBITS_1_5.getLabel(),
                Stopbits.STOPBITS_2.getLabel());
        stopBitsCombo.setItems(olStopbits);

     logger.error("Stopbits {}",Stopbits.getValueByLabel("2"));

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


    public ComboBox<Integer> getBaudRateCombo() {
        return baudRateCombo;
    }

    public void setBaudRateCombo(ComboBox<Integer> baudRateCombo) {
        this.baudRateCombo = baudRateCombo;
    }

    public ComboBox<Integer> getDataBitsCombo() {
        return dataBitsCombo;
    }

    public ComboBox<String> getStopBitsCombo() {
        return stopBitsCombo;
    }

    public ComboBox<String> getParityCombo() {
        return parityCombo;
    }
}
