package com.vspavlov.comport.fxmlcontrollers;

import com.vspavlov.comport.serial.*;
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
    private ComboBox<String> baudRateCombo;
    @FXML
    private ComboBox<String> dataBitsCombo;
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
    private FXMLMainController fxmlMainController;

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

    @FXML
    void handleSetBtn(ActionEvent event) {
        /**
         * Set new value to the comport properties
         */
        ObservableMap<Object,Object> observableComPortMapProps =  fxmlMainController.getObservableComPortMapProps();
        String selectedComPort =  (String)getComPortLabel().getProperties().get("selectedComPort");

        Baudrate newBaudrate = Baudrate.getBaudrateByLabel(baudRateCombo.getValue());
        Databits newDatabits = Databits.getDatabitsByLabel(dataBitsCombo.getValue());
        Stopbits newStopbits = Stopbits.getStopbitsByLabel(stopBitsCombo.getValue());
        Parity newParity = Parity.getParityByLabel(parityCombo.getValue());
        SerialPortProperties newSerialPortProperties =
                new SerialPortProperties(new SerialPort(selectedComPort),newBaudrate,newDatabits,newStopbits,newParity);
        observableComPortMapProps.replace(selectedComPort,newSerialPortProperties);

        // System.out.println((String) comPortLabel.getProperties().get("com"));
        // comPortLabel.setText((String) comPortLabel.getProperties().get("com"));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

    @PostConstruct
    private void init(){
        /**
         * Init comport config comboboxes
         */

        ObservableList<String> olBaudRate = FXCollections.observableArrayList();
        olBaudRate.addAll(Baudrate.BAUDRATE_1200.getLabel(),
                Baudrate.BAUDRATE_4800.getLabel(),
                Baudrate.BAUDRATE_9600.getLabel(),
                Baudrate.BAUDRATE_14400.getLabel(),
                Baudrate.BAUDRATE_19200.getLabel());
        baudRateCombo.setItems(olBaudRate);

        ObservableList<String> olDatabits = FXCollections.observableArrayList();
        olDatabits.addAll(Databits.DATABITS_5.getLabel(),
                Databits.DATABITS_6.getLabel(),
                Databits.DATABITS_7.getLabel(),
                Databits.DATABITS_8.getLabel());
        dataBitsCombo.setItems(olDatabits);

        ObservableList<String> olStopbits = FXCollections.observableArrayList();
        olStopbits.addAll(Stopbits.STOPBITS_1.getLabel(),
                          Stopbits.STOPBITS_1_5.getLabel(),
                          Stopbits.STOPBITS_2.getLabel());
        stopBitsCombo.setItems(olStopbits);

        ObservableList<String> olParity = FXCollections.observableArrayList();
        olParity.addAll(Parity.PARITY_NONE.getLabel(),
                Parity.PARITY_ODD.getLabel(),
                Parity.PARITY_SPACE.getLabel(),
                Parity.PARITY_MARK.getLabel(),
                Parity.PARITY_EVEN.getLabel());
        parityCombo.setItems(olParity);


       // parityCombo;
     logger.error("Stopbits {}", Stopbits.getValueByLabel("2"));

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

    public ComboBox<String> getDataBitsCombo() {
        return dataBitsCombo;
    }

    public ComboBox<String> getStopBitsCombo() {
        return stopBitsCombo;
    }

    public ComboBox<String> getParityCombo() {
        return parityCombo;
    }
}
