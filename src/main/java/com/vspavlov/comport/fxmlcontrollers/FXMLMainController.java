package com.vspavlov.comport.fxmlcontrollers;

import com.vspavlov.comport.serial.SerialPortProperties;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jssc.SerialPort;
import jssc.SerialPortList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.*;

/**
 * Created by vasiliy on 15.06.15.
 */

public class FXMLMainController implements Initializable,ApplicationEventPublisherAware{

    private ApplicationEventPublisher eventPublisher;
    private Logger logger = LoggerFactory.getLogger(FXMLMainController.class);

    private Scene scene;

    @Autowired
    private FXMLComPortConfigController fxmlComPortConfigController;

    @Autowired
    private TabPane beanTabPane;

    @FXML
    private AnchorPane mainView;


    @FXML
    private BorderPane borderPane;

    @FXML
    private ComboBox<String> comPortNameCombo;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem comPortsConfig;

    @FXML
    private Button refreshComPorts;

    @FXML
    private Button openPortBtn;

    @FXML
    private Label infoLabel;

    private double dragOffSetX;
    private double dragOffSetY;

    private ObservableMap<Object,Object> observableComPortMapProps;

    @PostConstruct
    private void initM(){
        logger.info(Thread.currentThread().getName());

        setComPortsToCombo();

        borderPane.setCenter(beanTabPane);
        Platform.runLater(() ->
                scene = new Scene((Parent)fxmlComPortConfigController.getView(), 500, 500,Color.AQUAMARINE));


    }


    @FXML
    void handleComPortsConfig(ActionEvent event) {



        logger.info(Thread.currentThread().getName());

        if (!isComPortSelect()){
            infoLabel.setText("Please, select COM port!");

        }else{
            Stage comPortConfigStage =  new Stage(StageStyle.UTILITY);
            comPortConfigStage.initModality(Modality.APPLICATION_MODAL);
            comPortConfigStage.setOpacity(1);
            String selectedComPort = comPortNameCombo.getValue();

            fxmlComPortConfigController.getComPortLabel().setText(selectedComPort + " port configuration");
            SerialPortProperties serialPortProperties = (SerialPortProperties)observableComPortMapProps.get(selectedComPort);

            fxmlComPortConfigController.getBaudRateCombo().setValue(Integer.toUnsignedString(serialPortProperties.getBaudRate()));
            // todo Сделать заполнение остальных комбо боксов в кофиге компорта

            /**
             *
             */
            scene.setOnMousePressed((MouseEvent e) -> {
                {
                    dragOffSetX = e.getScreenX() - comPortConfigStage.getX();
                    dragOffSetY = e.getScreenY() - comPortConfigStage.getY();
                }
            });

            scene.setOnMouseDragged((MouseEvent e) -> {
                {
                    comPortConfigStage.setX(e.getScreenX() - dragOffSetX);
                    comPortConfigStage.setY(e.getScreenY() - dragOffSetY);
                }
            });

            comPortConfigStage.setScene(scene);
            comPortConfigStage.setTitle("COM Port config");
            comPortConfigStage.show();

        }



    }

    @FXML
    void handleOpenPortBtn(ActionEvent event) {

       // SerialPort serialPort = new SerialPort();
      //  List<SerialPort> serialPortList = SerialPort


       // Tab tab = new Tab( String.valueOf(Math.random()));
        Tab tab = new Tab("COM12");
        ObservableMap<Object, Object> tabProperties = tab.getProperties();


        AnchorPane tabAnchor = new AnchorPane();

        Pane pane  = new Pane();
        Button closeBtn = new Button("Close port");
        closeBtn.setLayoutX(50);
        closeBtn.setLayoutY(50);
        // todo: продумать логику закрытия ком порта
         closeBtn.setOnAction(e -> beanTabPane.getTabs().removeAll(tab));
        pane.getChildren().add(closeBtn);
        tabAnchor.getChildren().add(pane);
        tab.setContent(tabAnchor);
        // todo: close com port when tab closing
       // tab.setOnClosed(event1 -> {});
        //mainTabPain.getTabs().add(tab);
         beanTabPane.getTabs().add(tab);


    }

    @FXML
    void handleCloseItem(ActionEvent event)
    {
        Platform.exit();

    }

    @FXML
    void handleRefreshComPorts(ActionEvent event) {
        infoLabel.setText("");
        setComPortsToCombo();

    }

    public AnchorPane getView() {
        return mainView;
    }

    private void setComPortsToCombo(){
        String[] serialPortArray = SerialPortList.getPortNames();
        ObservableList<String> observableComPortslist = FXCollections.observableArrayList(Arrays.asList(serialPortArray));
        comPortNameCombo.setItems(observableComPortslist);
         // todo Продумать всю логику обновления свойств ком портов
         // todo и сделать listener для combobox в конфигурации ком портов
          //
        comPortNameCombo.getProperties().clear();
        observableComPortMapProps = comPortNameCombo.getProperties();


        if(!observableComPortslist.isEmpty()) {
            comPortNameCombo.setValue(observableComPortslist.get(0));

            for (String s : serialPortArray) {
                if(!observableComPortMapProps.containsKey(s)){
                    SerialPort serialPort = new SerialPort(s);
                    observableComPortMapProps.put(s,new SerialPortProperties(serialPort));
                    infoLabel.setText(( String.valueOf(serialPort.isOpened())));
                }
            }
        }


    }


    private boolean isComPortSelect(){
       return (comPortNameCombo.getValue() != null);
    }



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initilize port config scene");

    }


    public ObservableMap<Object, Object> getObservableComPortMapProps() {
        return observableComPortMapProps;
    }
}
