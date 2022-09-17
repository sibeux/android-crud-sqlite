package com.hacktiv8.crudsqlite;

public class Country {

    int id;
    String countryName;
    long population;

    public Country(String countryName, long population) {
        this.countryName = countryName;
        this.population = population;
    }

    public Country() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}
