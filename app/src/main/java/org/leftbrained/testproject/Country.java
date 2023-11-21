package org.leftbrained.testproject;

public class Country {
    private String name;
    private String population;
    private Flag flags;

    public Country(String name, String population){
        this.name=name;
        this.population=population;
        this.flags=flags;
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

    public String getFlag() { return this.flags.png; }

    public void setPopulationCountry(String population) {
        this.population = population;
    }
}