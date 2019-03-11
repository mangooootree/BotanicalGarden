package com.lacit.botanicalgarden.domain;

import java.util.Objects;

public class Plant {

    private Long id;
    private String name;
    private PlantType plantType;
    private boolean planted;

    public Plant(Long id, String name, PlantType plantType, boolean planted) {
        this.id = id;
        this.name = name;
        this.plantType = plantType;
        this.planted = planted;
    }

    public Plant(String name, PlantType plantType, boolean planted) {
        this.name = name;
        this.plantType = plantType;
        this.planted = planted;
    }

    public Plant() {
    }

    public boolean isPlanted() {
        return planted;
    }

    public void setPlanted(boolean planted) {
        this.planted = planted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return Objects.equals(id, plant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
