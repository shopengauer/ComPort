package com.vspavlov.comport.config.javafx;

import com.vspavlov.comport.fxmlcontrollers.FXMLComPortConfigController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by vasiliy on 16.06.15.
 */
@Configuration
public class JavaFXComponents {


    @Autowired
    private FXMLComPortConfigController fxmlComPortConfigController;

    @Bean
    public Scene getComPortConfigScene(){

        return new Scene((Parent)fxmlComPortConfigController.getView(), 500, 700, Color.AQUAMARINE);
    }

}
