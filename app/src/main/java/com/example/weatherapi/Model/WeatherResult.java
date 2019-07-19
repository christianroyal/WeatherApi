package com.example.weatherapi.Model;

import java.util.List;

public class WeatherResult {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private int dt;
    private Sys sys;
    private int id;
    private String name;
    private int cod;

    public WeatherResult(){}

    public Coord getCoord() {
        return coord;
    }
    public void setCoord(Coord coord){
        this.coord= coord;
    }
    public String getBase(){
        return base;
    }
}
