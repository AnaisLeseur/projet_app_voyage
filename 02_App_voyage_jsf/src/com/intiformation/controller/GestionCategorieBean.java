package com.intiformation.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.SlideEndEvent;
import org.primefaces.event.UnselectEvent;

@ManagedBean(name="GestionCategorieBean")
@SessionScoped
public class GestionCategorieBean implements Serializable {
	
	private String[] selectedConsoles;
    private String[] selectedConsoles2;
    private String[] selectedCities;
    private String[] selectedCities2;
    private List<String> cities;
    
    
    private int number3;   
    private int number8 = 30;
    private int number9 = 500;
 
    @PostConstruct
    public void init() {
        cities = new ArrayList<String>();
        cities.add("Miami");
        cities.add("London");
        cities.add("Paris");
        cities.add("Istanbul");
        cities.add("Berlin");
        cities.add("Barcelona");
        cities.add("Rome");
        cities.add("Brasilia");
        cities.add("Amsterdam");

    }

    
    public int getNumber8() {
        return number8;
    }
 
    public void setNumber8(int number8) {
        this.number8 = number8;
    }
 
    public int getNumber9() {
        return number9;
    }
 
    public void setNumber9(int number9) {
        this.number9 = number9;
    }
 
    public void onInputChanged(ValueChangeEvent event) {
        FacesMessage message = new FacesMessage("Input Changed", "Value: " + event.getNewValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    } 
     
    public void onSlideEnd(SlideEndEvent event) {
        FacesMessage message = new FacesMessage("Slide Ended", "Value: " + event.getValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    } 
    
    
    
    public int getNumber3() {
        return number3;
    }
 
    public void setNumber3(int number3) {
        this.number3 = number3;
    }
 
    public String[] getSelectedConsoles() {
        return selectedConsoles;
    }
 
    public void setSelectedConsoles(String[] selectedConsoles) {
        this.selectedConsoles = selectedConsoles;
    }
 
    public String[] getSelectedCities() {
        return selectedCities;
    }
 
    public void setSelectedCities(String[] selectedCities) {
        this.selectedCities = selectedCities;
    }
 
    public String[] getSelectedCities2() {
        return selectedCities2;
    }
 
    public void setSelectedCities2(String[] selectedCities2) {
        this.selectedCities2 = selectedCities2;
    }
 
    public String[] getSelectedConsoles2() {
        return selectedConsoles2;
    }
 
    public void setSelectedConsoles2(String[] selectedConsoles2) {
        this.selectedConsoles2 = selectedConsoles2;
    }
 
    public List<String> getCities() {
        return cities;
    }
 
 
    public void onItemUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
         
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
         
        context.addMessage(null, msg);
    }

}// end GestionCategorieBean
