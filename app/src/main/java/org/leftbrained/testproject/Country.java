package org.leftbrained.testproject;

public class Country {
    private String name;
    private String population;

    public Country(String name, String population){
        this.name=name;
        this.population=population;
    }

    public String getNameCountry() {
        return this.name;
    }

    public void setNameCountry(String name) {
        this.name = name;
    }

    public String getPopulationCountry() {
        return this.population;
    }

    public void setPopulationCountry(String population) {
        this.population = population;
    }
}