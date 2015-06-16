package com.vspavlov.comport.fxmlcontrollers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

public class FXMLMainController implements Initializable,ApplicationEventPublisherAware{

    private ApplicationEventPublisher eventPublisher;
    private Logger logger = LoggerFactory.getLogger(FXMLMainController.class);



    @Autowired
    private FXMLComPortConfigController fxmlComPortConfigController;

    @Autowired
    private Scene comPortConfigScene;


    @FXML
    private AnchorPane mainView;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem comPortsConfig;




//    @PostConstruct
//    private void init(){
//        logger.info("Initilize port config scene");
//        //scene = new Scene((Parent)fxmlComPortConfigController.getView(), 500, 700,Color.AQUAMARINE);
//    }




    @FXML
    void handleComPortsConfig(ActionEvent event) {


        Stage createComPortConfigStage =  new Stage(StageStyle.UTILITY);
        createComPortConfigStage.setScene(comPortConfigScene);
        createComPortConfigStage.setTitle("COM Port config");
        createComPortConfigStage.show();
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
