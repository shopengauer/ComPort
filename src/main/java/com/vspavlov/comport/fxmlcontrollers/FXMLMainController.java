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
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

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

    @Autowired
    private FXMLTabController fxmlTabController;


    @PostConstruct
    private void initM(){

        /**
         * Initializing of Set for storing actual list of com ports
         */
        observableActualComPortSet = FXCollections.observableSet();
        logger.info("Initialization of object for storage set of com ports {}", observableActualComPortSet);

        /**
         * Initializing Map for storing properties of com ports
         */
        observableComPortMapProps = comPortNameCombo.getProperties();

        /**
         * Set listener for adding and removing com ports to combo box
          */
        observableActualComPortSet.addListener(new SetChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                if (change.wasAdded()) {
                    /**
                     *  Com port for add
                     */
                    String wasAddedComPort = change.getElementAdded();

                    /**
                     * Default initialization for added com port
                     */
                    SerialPortProperties serialPortProperties = new SerialPortProperties(new SerialPort(wasAddedComPort));

                    /**
                     * Save SerialPortProperties in comPortNameCombo properties map
                     */
                    observableComPortMapProps.put(wasAddedComPort, serialPortProperties);

                    /**
                     * Add founded com port to combo box list
                     */
                    comPortNameCombo.getItems().add(wasAddedComPort);


                    /**
                     * Set current comport value in combo box list
                     */
                    comPortNameCombo.setValue(comPortNameCombo.getItems().get(0));

                    logger.info("Element {} was added", change.getElementAdded());
                }
                if (change.wasRemoved()) {
                    /**
                     * Com port to remove
                     */
                    String wasRemovedComPort = change.getElementRemoved();


                    /**
                     * Remove com port from comPortComboBox Map properties
                     */
                    observableComPortMapProps.remove(wasRemovedComPort);


                    /**
                     * Remove com port item from comPortNameCombo list
                     */
                    comPortNameCombo.getItems().remove(wasRemovedComPort);


                    /**
                     * If comPortNameCombo is not empty set current com port value in combo box
                     */
                    if (!comPortNameCombo.getItems().isEmpty()) {
                        comPortNameCombo.setValue(comPortNameCombo.getItems().get(0));
                    }

                    logger.info("Element {} was removed", change.getElementRemoved());
                }

            }
        });

        /**
         * Init comPortComboBox
         */
        this.setComPortsToCombo();

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



        String comPortName = comPortNameCombo.getValue();



        if(comPortName == null) {

            Tab tab = new Tab(comPortNameCombo.getValue());
            ObservableMap<Object, Object> tabProperties = tab.getProperties();
            AnchorPane tabAnchor = new AnchorPane();

            Pane pane  = new Pane();
            Button closeBtn = new Button("Close port");
            closeBtn.setLayoutX(50);
            closeBtn.setLayoutY(50);
            // todo: продумать логику закрытия ком порта
            closeBtn.setOnAction(e -> beanTabPane.getTabs().removeAll(tab));
            pane.getChildren().add(closeBtn);
          //  pane.getChildren().addAll(new TextArea());

            tabAnchor.getChildren().add(pane);
            tab.setContent(tabAnchor);
            // todo: close com port when tab closing
            // tab.setOnClosed(event1 -> {});
            //mainTabPain.getTabs().add(tab);
            beanTabPane.getTabs().add(tab);

        } else {
            infoLabel.setText("Com port was not selected!");
        }


         //Tab tab = fxmlTabController.getTab();
        //  beanTabPane.getTabs().filtered(tab1 -> tab1.getText().equals())






    }

    @FXML
    void handleCloseItem(ActionEvent event)
    {
        Platform.exit();

    }

    @FXML
    void handleRefreshComPorts(ActionEvent event) {
        infoLabel.setText("");
        this.setComPortsToCombo();

    }

    public AnchorPane getView() {
        return mainView;
    }

    private void setComPortsToCombo(){

        /**
         * Get existing com ports
         */
        ObservableSet<String> comPortSet = FXCollections.observableSet(SerialPortList.getPortNames());

        /**
         *  If observableActualComPortSet not contain founded com ports in comPortSet,
         *  remove one from the observableActualComPortSet
         *  This is done for retain current configs of com ports. Com
         */
        for (String s : observableActualComPortSet) {
            if(!comPortSet.contains(s)){
                observableActualComPortSet.remove(s);
            }

        }
       // observableActualComPortSet.clear();
      //  observableActualComPortSet.

//        observableActualComPortSet.stream().noneMatch(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return comPortSet.contains(s);
//            }
//        });

      //  observableActualComPortSet.stream().filter(s -> comPortSet.contains(s));//.collect();

        /**
         * And then add comPortSet to actual Comport Set(add only if comport not already exist)
         */
         observableActualComPortSet.addAll(comPortSet);


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
