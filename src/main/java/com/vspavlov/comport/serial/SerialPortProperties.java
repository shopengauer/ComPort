package com.vspavlov.comport.serial;

/**
 * Created by Vasiliy on 18.06.2015.
 */
import jssc.SerialPort;

import static jssc.SerialPort.*;
public class SerialPortProperties {

    private int baudRate;
    private int databits;
    private int stopbits;
    private int parity;
    private SerialPort serialPort;

    public SerialPortProperties(SerialPort serialPort) {
        this.serialPort = serialPort;
        baudRate = BAUDRATE_9600;
        databits = DATABITS_8;
        stopbits = STOPBITS_1;
        parity = PARITY_NONE;

    }

    public SerialPortProperties(int baudRate, int databits, int stopbits, int parity) {
        this.baudRate = baudRate;
        this.databits = databits;
        this.stopbits = stopbits;
        this.parity = parity;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDatabits() {
        return databits;
    }

    public void setDatabits(int databits) {
        this.databits = databits;
    }

    public int getStopbits() {
        return stopbits;
    }

    public void setStopbits(int stopbits) {
        this.stopbits = stopbits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }
}