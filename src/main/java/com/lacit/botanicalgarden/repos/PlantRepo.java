package com.lacit.botanicalgarden.repos;

import com.lacit.botanicalgarden.domain.Plant;

import java.util.List;

public interface PlantRepo {

    List<Plant> findAll();
    Plant findOne(Long id);
    Long save(Plant plant);
    int update(Plant plant);
    int delete(Long id);
}
