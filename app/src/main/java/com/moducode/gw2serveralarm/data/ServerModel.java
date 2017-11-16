
package com.moducode.gw2serveralarm.data;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class ServerModel implements Comparable<ServerModel> {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("population")
    @Expose
    private String population;

    private int populationLevel;

    public ServerModel(int id, String name, String population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setPopulationLevel(int level){
        this.populationLevel = level;
    }

    public int getPopulationLevel(){
        switch (population){
            case "Low":
                return 0;
            case "Medium":
                return 1;
            case "High":
                return 2;
            case "VeryHigh":
                return 3;
            case "Full":
                return 4;
            default:
                return 0;
        }
    }

    public int getPopulationColor(){
        switch (population){
            case "Low":
                return Color.parseColor("#C6E0B4");
            case "Medium":
                return Color.parseColor("#A9D08E");
            case "High":
                return Color.parseColor("#FFC000");
            case "VeryHigh":
                return Color.parseColor("#ED7D31");
            case "Full":
                return Color.parseColor("#FF0000");
            default:
                return Color.parseColor("#C6E0B4");
        }
    }

    public String getIdString(){
        return String.valueOf(this.id);
    }

    @Override
    public int compareTo(@NonNull ServerModel serverModel) {
        return serverModel.getPopulationLevel() - this.getPopulationLevel();
    }
}
