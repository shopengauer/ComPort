package com.vspavlov.comport.fxmlcontrollers;

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

    @FXML
    private AnchorPane mainView;

    @FXML
    private MenuItem comPortsConfig;


    @FXML
    void handleComPortsConfig(ActionEvent event) {


        Stage createComPortConfigStage =  new Stage(StageStyle.UNDECORATED);


       // Scene scene = new Scene((Parent)fxmlComPortConfigController.getView(), 300, 300);

        Group root = new Group();
        Button btn = new Button("Close");
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 300, Color.BLACK);

        createComPortConfigStage.setResizable(false);


        //  scene.getStylesheets().add("/styles/fxmlschema.css");
        createComPortConfigStage.setScene(scene);
        createComPortConfigStage.setTitle("Energymeter communication client");
        createComPortConfigStage.show();

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
       logger.info("Initilize");

    }




}
