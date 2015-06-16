package com.vspavlov.comport.config.javafx;

import com.vspavlov.comport.fxmlcontrollers.FXMLComPortConfigController;
import com.vspavlov.comport.fxmlcontrollers.FXMLMainController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import org.apache.tomcat.jni.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.lang.Thread;

/**
 * Created by vasiliy on 16.06.15.
 */
@Configuration
public class JavaFXComponents {

//    private Logger logger = LoggerFactory.getLogger(JavaFXComponents.class);
//    @Autowired
//    private FXMLComPortConfigController fxmlComPortConfigController;
//
//    @Bean
//    public Scene getComPortConfigScene(){
//        logger.info(Thread.currentThread().getName());
//        return new Scene((Parent)fxmlComPortConfigController.getView(), 500, 700, Color.AQUAMARINE);
//    }

}
