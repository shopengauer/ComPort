package com.vspavlov.comport;

import com.vspavlov.comport.config.fxml.FXMLControllersConfig;
import com.vspavlov.comport.fxmlcontrollers.FXMLMainController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vspavlov.comport.*"})
public class ComPortApplication extends AbstractJavaFxApplicationSupport{


    @Autowired
    @Qualifier(value = "fxmlMain")
    private FXMLMainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {

         Scene scene = new Scene((Parent)mainController.getView(), 1000, 700);

        primaryStage.setResizable(false);


        //  scene.getStylesheets().add("/styles/fxmlschema.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Energymeter communication client");
        primaryStage.show();


    }

    public static void main(String[] args) {
        launchApp(ComPortApplication.class, args);
    }
}
