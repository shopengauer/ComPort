package com.vspavlov.comport.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyChangeSupport;

/**
 * Created by vasiliy on 16.06.15.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/",method= RequestMethod.GET)
    public String home(){
        return "home";
    }
    @RequestMapping(value = "/rest",method= RequestMethod.GET)
    @ResponseBody
    public Person rest(){
        return new Person("COM","1");
    }



}
class Person{

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Person(String name, String cod) {
        this.name = name;
        this.cod = cod;
    }


    private String name;
    private String cod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //pcs.
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}