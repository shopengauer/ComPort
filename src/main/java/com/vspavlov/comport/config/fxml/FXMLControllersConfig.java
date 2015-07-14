package com.vspavlov.comport.config.fxml;

import com.vspavlov.comport.fxmlcontrollers.FXMLComPortConfigController;
import com.vspavlov.comport.fxmlcontrollers.FXMLMainController;
import com.vspavlov.comport.fxmlcontrollers.FXMLTabController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vasiliy on 15.06.15.
 */
@Configuration
public class FXMLControllersConfig {

    private Logger logger = LoggerFactory.getLogger(FXMLMainController.class);

    @Bean
    @Qualifier(value = "fxmlMain")
    public FXMLMainController fxmlController() throws IOException {
        System.out.println("Load controller");
         return (FXMLMainController) loadController("/fxmlschemas/FXMLMainSchema.fxml");

    }

    @Bean
    @Qualifier(value = "fxmlComPortConfig")
    //@Scope(value = "prototype")
    public FXMLComPortConfigController fxmlComPortConfigController() throws IOException {
        System.out.println("Load controller");
        return (FXMLComPortConfigController) loadController("/fxmlschemas/FXMLComPortConfigSchema.fxml");
    }

    @Bean
    public FXMLTabController fxmlTabController() throws IOException {

        return (FXMLTabController) loadController("/fxmlschemas/FX.fxml");
    }

    protected Object loadController(String url) throws IOException {
        try
                (InputStream fxmlStream = getClass().getResourceAsStream(url)) {
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return loader.getController();
        }
    }


//    @Bean
//    @Qualifier(value = "comPortConfigStage")
//    public Stage createComPortConfigStage(){
//        return new Stage();
//    }


}
