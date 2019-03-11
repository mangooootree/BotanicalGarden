package com.lacit.botanicalgarden.controller;

import com.lacit.botanicalgarden.domain.Plant;
import com.lacit.botanicalgarden.domain.Task;
import com.lacit.botanicalgarden.domain.User;
import com.lacit.botanicalgarden.repos.PlantRepo;
import com.lacit.botanicalgarden.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class TaskController {

    private TaskRepo taskRepo;
    private PlantRepo plantRepo;

    @Autowired
    public TaskController(TaskRepo taskRepo, PlantRepo plantRepo) {
        this.taskRepo = taskRepo;
        this.plantRepo = plantRepo;
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        List<Task> tasks = new ArrayList<>(taskRepo.findAll());
        tasks.sort(Comparator.comparing(Task::getId));
        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @GetMapping("/curePlant")
    public String curePlant(@RequestParam String id) {
        Task task = new Task("Лечение растения",
                plantRepo.findOne(Long.parseLong(id)), false);

        taskRepo.save(task);

        return "redirect:/tasks";
    }

    @GetMapping("/removePlant")
    public String removePlant(@RequestParam String id) {
        Task task = new Task("Уничтожение растения",
                plantRepo.findOne(Long.parseLong(id)), false);

        taskRepo.save(task);

        return "redirect:/plants";
    }

    @GetMapping("/deleteTask")
    public String deletePlant(@RequestParam String id) {
        taskRepo.delete(Long.parseLong(id));

        return "redirect:/tasks";
    }

    @GetMapping("/setDone")
    public String setDone(@AuthenticationPrincipal User user,
                          @RequestParam String id) {
        Task task = taskRepo.findOne(Long.parseLong(id));

        if (user.isAdmin()) {
            if (task.getTitle().equals("Высадка растения")) {
                Plant plant = task.getPlant();
                plant.setPlanted(true);
                plantRepo.update(plant);
            }

            if (task.getTitle().equals("Уничтожение растения")) {
                Plant plant = task.getPlant();
                plant.setPlanted(false);
                plantRepo.update(plant);
            }
            task.setDone(true);
            task.setComment("complete");
        } else {
            task.setComment("Задание выполнено: "
                    + LocalDate.now());
        }
        taskRepo.update(task);

        return "redirect:/tasks";
    }

    @PostMapping("/newComment")
    public String addComment(@RequestParam String id,
                             @RequestParam String comment) {
        Task task = taskRepo.findOne(Long.parseLong(id));

        try {
            task.setComment(formatComment(comment));
            //           System.out.println(formatComment(comment));
        } catch (CommentFormatException e) {
            task.setComment("Внимание! Комментарий не удалось отформатировать:"
                    + "\n" + comment);

        }
        taskRepo.update(task);

        return "redirect:/tasks";
    }

    private String formatComment(String str) throws CommentFormatException {
        StringBuilder result = new StringBuilder();
        try {
            str = str.replace("\r\n", " \n ");
            StringBuilder sb = new StringBuilder(str);
            if (Character.isAlphabetic(str.codePointAt(0)))
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            for (int i = 1; i < sb.length(); i++) {
                if (Character.isAlphabetic(sb.charAt(i))
                        && Character.isSpaceChar(sb.charAt(i - 1))
                        && (sb.charAt(i - 2) == '.' || sb.charAt(i - 2) == '!'
                        || sb.charAt(i - 2) == '?'))
                    sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
            }

            String[] words = sb.toString().split(" ");
            StringBuilder builder = new StringBuilder("    ");
            boolean paragraphBegin = false;
            for (String word : words) {
                if (builder.length() + word.length() < 120
                        && (!word.equals("\n"))) {
                    if (paragraphBegin && word.length() > 0) {
                        word = word.replace(word.charAt(0),
                                Character.toUpperCase(word.charAt(0)));
                        paragraphBegin = false;
                    }
                    builder.append(word).append(" ");
                } else {
                    if (word.equals("\n")) {
                        result.append(builder).deleteCharAt(result.length() - 1)
                                .append("\n");
                        builder.delete(0, builder.length());
                        builder.append("    ");
                        paragraphBegin = true;
                    } else {
                        result.append(builder).deleteCharAt(result.length() - 1)
                                .append("\n");
                        builder.delete(0, builder.length());
                        builder.append(word).append(" ");
                    }
                }
            }
            result.append(builder).deleteCharAt(result.length() - 1);
        } catch (Exception e) {
            throw new CommentFormatException();
        }

        return result.toString();
    }
}
