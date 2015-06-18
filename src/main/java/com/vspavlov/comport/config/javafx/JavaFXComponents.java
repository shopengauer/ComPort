package com.vspavlov.comport.config.javafx;

import com.vspavlov.comport.fxmlcontrollers.FXMLComPortConfigController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vasiliy on 16.06.15.
 */
@Configuration
public class JavaFXComponents {

//
//    @Autowired
//    private FXMLComPortConfigController fxmlComPortConfigController;
//
//    @Bean
//    public Scene getComPortConfigScene(){
//
//        return null;
//    }
//    @Bean
//    public Map<String, Object> getComPortProperties() {
//           //return null;
//           return FXCollections.observableMap(new HashMap<String, Object>());
//       }

    @Bean
    public TabPane getComPortTabPane(){
        TabPane tabPane = new TabPane();
       // tabPane.set
        return new TabPane();
    }

}
