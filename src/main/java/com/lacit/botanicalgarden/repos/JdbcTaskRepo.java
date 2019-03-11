package com.lacit.botanicalgarden.repos;

import com.lacit.botanicalgarden.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTaskRepo implements TaskRepo {

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert simpleJdbcInsert;
    private PlantRepo plantRepo;

    @Autowired
    public JdbcTaskRepo(JdbcTemplate jdbc, DataSource dataSource, PlantRepo plantRepo) {
        this.jdbc = jdbc;
        this.plantRepo = plantRepo;
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Task").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Task> findAll() {

        return jdbc.query("select id, title, date, plant_id, done, comment from Task",
                this::mapRowToPlant);
    }

    @Override
    public Task findOne(Long id) {

        return jdbc.queryForObject("select id, title, date, plant_id, done, comment from Task where id=?",
                this::mapRowToPlant, id);
    }

    @Override
    public Long save(Task task) {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("title", task.getTitle());
        parameters.put("date", new Date());
        parameters.put("plant_id", task.getPlant().getId());
        parameters.put("done", task.isDone());
        parameters.put("comment", task.getComment());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);

        return newId.longValue();
    }

    @Override
    public int update(Task task) {

        return jdbc.update("update task set title = ?, date = ?, plant_id = ?, done = ?, comment = ? where id = ?",
                task.getTitle(),
                task.getDate(),
                task.getPlant().getId(),
                task.isDone(),
                task.getComment(),
                task.getId());
    }

    @Override
    public int delete(Long id) {

        return jdbc.update("delete from Task where id=?", id);
    }

    private Task mapRowToPlant(ResultSet rs, int rowNum) throws SQLException {

        return new Task(
                Long.parseLong(rs.getString("id")),
                rs.getString("title"),
                rs.getDate("date"),
                plantRepo.findOne(rs.getLong("plant_id")),
                rs.getBoolean("done"),
                rs.getString("comment"));

    }
}
