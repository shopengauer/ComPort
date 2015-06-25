package com.vspavlov.comport.fxmlcontrollers;

import com.vspavlov.comport.serial.SerialPortProperties;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    /**
     * Комбо бокс для выбора ком порта
     */

    @FXML
    private ComboBox<String> comPortNameCombo;

    /**
     *  обьект Map для хранения свойств ком портов
     *  ключ - String "Имя ком порта" значение - SerialPortProperties
     */
    private ObservableMap<Object,Object> observableComPortMapProps; //

    /**
     * объект для хранения актуального списка ком портов
     */
    private ObservableSet<String> observableActualComPortSet;

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



    @PostConstruct
    private void initM(){
        logger.info(Thread.currentThread().getName());
        observableActualComPortSet = FXCollections.observableSet();
        observableComPortMapProps = comPortNameCombo.getProperties();
       // observableActualComPortSet.addListener((SetChangeListener.Change<? extends String> c) -> {});

        observableActualComPortSet.addListener(new SetChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                if (change.wasAdded()) {
                    String wasAddedComPort = change.getElementAdded();
                    SerialPortProperties serialPortProperties = new SerialPortProperties(new SerialPort(wasAddedComPort));
                    observableComPortMapProps.put(wasAddedComPort,serialPortProperties);

                    comPortNameCombo.getItems().add(wasAddedComPort);
                    comPortNameCombo.setValue(comPortNameCombo.getItems().get(0));
                    logger.info("Element {} was added", change.getElementAdded());
                }
                if (change.wasRemoved()) {
                    String wasRemovedComPort = change.getElementRemoved();

                    observableComPortMapProps.remove(wasRemovedComPort);

                    comPortNameCombo.getItems().remove(wasRemovedComPort);
                    if (!comPortNameCombo.getItems().isEmpty()) {
                        comPortNameCombo.setValue(comPortNameCombo.getItems().get(0));
                    }
                    logger.info("Element {} was removed", change.getElementRemoved());
                }

            }
        });

        this.setComPortsToCombo();
//
        /**
         * Hover listener
          */
//        comPortNameCombo.hoverProperty().addListener((observable, oldValue, newValue)
//                -> {
//            if (newValue) {
//                infoLabel.setText(comPortNameCombo.getValue());
//            } else {
//                infoLabel.setText("");
//            }
//        });

        //comPortNameCombo.valueProperty().add

        /**
         * infoLabel and comport binding
         */

      //  infoLabel.textProperty().bind(comPortNameCombo.valueProperty());




       // .bind(infoLabel.textProperty());

     //   comPortNameCombo.valueProperty().addListener((observable, oldValue, newValue) -> infoLabel.setText(newValue));

        borderPane.setCenter(beanTabPane);
        Platform.runLater(() ->
                scene = new Scene((Parent) fxmlComPortConfigController.getView(), 350, 500, Color.AQUAMARINE));

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

            // todo Save data about com port to the label properties
            fxmlComPortConfigController.getComPortLabel().getProperties().put("selectedComPort", selectedComPort);

             SerialPortProperties serialPortProperties = (SerialPortProperties)observableComPortMapProps.get(selectedComPort);

             fxmlComPortConfigController.getBaudRateCombo().setValue(serialPortProperties.getBaudRate().getLabel());
             fxmlComPortConfigController.getDataBitsCombo().setValue( serialPortProperties.getDatabits().getLabel());
             fxmlComPortConfigController.getStopBitsCombo().setValue(serialPortProperties.getStopbits().getLabel());
             fxmlComPortConfigController.getParityCombo().setValue(serialPortProperties.getParity().getLabel());


            /**
             *
             */
             scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                 @Override
                 public void handle(MouseEvent event) {

                 }
             });

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
            comPortConfigStage.setResizable(false);
            comPortConfigStage.setScene(scene);
            comPortConfigStage.setTitle(selectedComPort + " Port config");
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
        /**
         * Get existing com ports
         */

        ObservableSet<String> comPortSet = FXCollections.observableSet(SerialPortList.getPortNames());

        //comPortSet.stream().distinct().

        for (String s : observableActualComPortSet) {
              if(!comPortSet.contains(s)){
                  observableActualComPortSet.remove(s);
              }
        }

        observableActualComPortSet.addAll(comPortSet);



//
//        /**
//         * If non zero com ports found
//         */
//        if(!freshComPortslist.isEmpty()) {
//            /**
//             * Get comPortComboBox property and clear then
//             */
//            observableComPortMapProps = comPortNameCombo.getProperties();
//            comPortNameCombo.getProperties().clear();
//            /**
//             * Set first element in observableComPortslist in com
//             * port combobox
//             */
//            comPortNameCombo.setValue(freshComPortslist.get(0));
//            infoLabel.setText("");
//            /**
//             * Set default properties value to founded com ports
//             */
//            for (String s : serialPortArray) {  // iteration over all serial ports
//                if(!observableComPortMapProps.containsKey(s)){ // if this com port has not config
//                    SerialPort serialPort = new SerialPort(s); //
//                    observableComPortMapProps.put(s, new SerialPortProperties(serialPort));
//                  }
//            }
//
//            /**
//             * Print set of properties
//             */
//            Set<Object> keStringSet =  observableComPortMapProps.keySet();
//            for (Object o : keStringSet) {
//                //logger.info((String)o);
//                SerialPortProperties spp = (SerialPortProperties)observableComPortMapProps.get(o);
//                logger.info("{} Baudrate: {}, Databits: {}, Stopbits: {}, Parity: {}", (String)o ,spp.getBaudRate(),spp.getDatabits(),spp.getStopbits(),spp.getParity());
//            }
//
//
//        }
//
//

         // todo Продумать всю логику обновления свойств ком портов
         // todo и сделать listener для combobox в конфигурации ком портов
        //

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
