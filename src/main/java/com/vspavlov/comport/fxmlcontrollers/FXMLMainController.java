package com.vspavlov.comport.fxmlcontrollers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jssc.SerialPort;
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

//    @Autowired
//    private TabPane tabPane;

    @FXML
    private AnchorPane mainView;

   
    @FXML
    private BorderPane borderPane;


    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem comPortsConfig;

    @FXML
    private TabPane mainTabPain;

    @FXML
    private Button openPortBtn;


    private double dragOffSetX;
    private double dragOffSetY;

   private TabPane tabPane;

    @PostConstruct
    private void initM(){
        logger.info(Thread.currentThread().getName());

       // Runnable createSceneTask = () -> scene = new Scene((Parent)fxmlComPortConfigController.getView(), 500, 700,Color.AQUAMARINE);
        Platform.runLater(() ->
                scene = new Scene((Parent)fxmlComPortConfigController.getView(), 500, 700,Color.AQUAMARINE));

      tabPane = (TabPane)borderPane.getCenter();
       // tabPane.getChildren().add(tabPane);
    }


    @FXML
    void handleComPortsConfig(ActionEvent event) {

        logger.info(Thread.currentThread().getName());
        Stage comPortConfigStage =  new Stage(StageStyle.UNDECORATED);
        comPortConfigStage.initModality(Modality.APPLICATION_MODAL);
        comPortConfigStage.setOpacity(0.8);

//
//        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                dragOffSetX = event.getScreenX() - comPortConfigStage.getX();
//                dragOffSetY = event.getScreenY() - comPortConfigStage.getY();
//            }
//        });

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
//////////////////////////////////////////////////
//        Group root = new Group();
//        Button btn = new Button("Close");
//        btn.setLayoutX(150);
//        btn.setLayoutY(250);
//
//        btn.setOnAction(event1 -> {createComPortConfigStage.close();});
//
//        root.getChildren().add(btn);
//
//        Scene scene = new Scene(root, 500, 600, Color.WHITE);
//
//        createComPortConfigStage.setResizable(false);
/////////////////////////////////////////////////////////////

        //  scene.getStylesheets().add("/styles/fxmlschema.css");


    }

    @FXML
    void handleOpenPortBtn(ActionEvent event) {

       // SerialPort serialPort = new SerialPort();
      //  List<SerialPort> serialPortList = SerialPort


       // Tab tab = new Tab( String.valueOf(Math.random()));
        Tab tab = new Tab("COM12");

        AnchorPane tabAnchor = new AnchorPane();

        Pane pane  = new Pane();
        pane.getChildren().add(new Button("Active"));
        tabAnchor.getChildren().add(pane);
        tab.setContent(tabAnchor);
        // todo: close com port when tab closing
       // tab.setOnClosed(event1 -> {});
        tabPane.getTabs().add(tab);



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
