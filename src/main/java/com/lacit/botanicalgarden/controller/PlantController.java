package com.lacit.botanicalgarden.controller;

import com.lacit.botanicalgarden.domain.Plant;
import com.lacit.botanicalgarden.domain.PlantType;
import com.lacit.botanicalgarden.domain.Task;
import com.lacit.botanicalgarden.repos.PlantRepo;
import com.lacit.botanicalgarden.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
public class PlantController {

    private PlantRepo plantRepo;
    private TaskRepo taskRepo;
    private boolean showForm;

    @Autowired
    public PlantController(PlantRepo plantRepo, TaskRepo taskRepo) {
        this.plantRepo = plantRepo;
        this.taskRepo = taskRepo;
    }

    @GetMapping("/plants")
    public String plants(Model model) {
        List<Plant> plants = plantRepo.findAll();
        plants.sort(Comparator.comparing(Plant::getId));
        model.addAttribute("plants", plants);

        if (showForm) {
            model.addAttribute("showForm", true);
            model.addAttribute("types", PlantType.values());
        }

        showForm = false;

        return "plants";
    }

    @GetMapping("/deletePlant")
    public String deletePlant(@RequestParam String id) {
        Plant plant = plantRepo.findOne(Long.parseLong(id));
        List<Task> tasks = taskRepo.findAll();

        for (Task task: tasks){
            if (task.getPlant().equals(plant))
                return "redirect:/plants";
        }

        plantRepo.delete(plant.getId());

        return "redirect:/plants";
    }

    @GetMapping("/showForm")
    public String showForm() {
        showForm = true;

        return "redirect:/plants";
    }

    @PostMapping("/newPlant")
    public String showForm(@RequestParam String name,
                           @RequestParam String type) {
        Plant plant = new Plant(name, PlantType.valueOf(type), false);
        Long id = plantRepo.save(plant);
        Task task = new Task("Высадка растения", plantRepo.findOne(id),false);

        taskRepo.save(task);

        return "redirect:/plants";
    }
}
