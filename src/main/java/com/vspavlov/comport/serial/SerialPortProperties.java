package com.vspavlov.comport.serial;

/**
 * Created by Vasiliy on 18.06.2015.
 */
import jssc.SerialPort;

import java.util.Enumeration;
import static jssc.SerialPort.*;

public class SerialPortProperties {

    private Baudrate baudRate;
    private Databits databits;
    private Stopbits stopbits;
    private Parity parity;
    private SerialPort serialPort;

    public SerialPortProperties(SerialPort serialPort) {
        this.serialPort = serialPort;
        baudRate = Baudrate.BAUDRATE_9600;
        databits = Databits.DATABITS_8;
        stopbits = Stopbits.STOPBITS_1;
        parity = Parity.PARITY_NONE;
    }

    public SerialPortProperties(SerialPort serialPort,Baudrate baudRate, Databits databits, Stopbits stopbits, Parity parity) {
        this.baudRate = baudRate;
        this.databits = databits;
        this.stopbits = stopbits;
        this.parity = parity;
    }

    public Baudrate getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(Baudrate baudRate) {
        this.baudRate = baudRate;
    }

    public Databits getDatabits() {
        return databits;
    }

    public void setDatabits(Databits databits) {
        this.databits = databits;
    }

    public Stopbits getStopbits() {
        return stopbits;
    }

    public void setStopbits(Stopbits stopbits) {
        this.stopbits = stopbits;
    }

    public Parity getParity() {
        return parity;
    }

    public void setParity(Parity parity) {
        this.parity = parity;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }
}
