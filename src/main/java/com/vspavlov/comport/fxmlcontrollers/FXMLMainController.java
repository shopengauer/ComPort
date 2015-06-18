package com.vspavlov.comport.fxmlcontrollers;

import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

//    @FXML
//    private TabPane mainTabPain;

    @FXML
    private Button openPortBtn;


    private double dragOffSetX;
    private double dragOffSetY;

   //private TabPane tabPane;

    @PostConstruct
    private void initM(){
        logger.info(Thread.currentThread().getName());

        String[] serialPortList = SerialPortList.getPortNames();
        for (String s : serialPortList) {
            logger.info(s);
        }

        comPortNameCombo.getItems().addAll(serialPortList);


        borderPane.setCenter(beanTabPane);
        Platform.runLater(() ->
                scene = new Scene((Parent)fxmlComPortConfigController.getView(), 500, 700,Color.AQUAMARINE));


    }


    @FXML
    void handleComPortsConfig(ActionEvent event) {

        logger.info(Thread.currentThread().getName());
        Stage comPortConfigStage =  new Stage(StageStyle.DECORATED);
        comPortConfigStage.initModality(Modality.APPLICATION_MODAL);
        comPortConfigStage.setOpacity(0.8);

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

    public AnchorPane getView() {
        return mainView;
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initilize port config scene");

    }




}
