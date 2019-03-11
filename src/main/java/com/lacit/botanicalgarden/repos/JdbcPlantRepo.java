package com.lacit.botanicalgarden.repos;

import com.lacit.botanicalgarden.domain.Plant;
import com.lacit.botanicalgarden.domain.PlantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcPlantRepo implements PlantRepo {

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public JdbcPlantRepo(JdbcTemplate jdbc, DataSource dataSource) {
        this.jdbc = jdbc;
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Plant").usingGeneratedKeyColumns("id");
    }


    @Override
    public List<Plant> findAll() {
        return jdbc.query("select id, name, type, planted from Plant",
                this::mapRowToPlant);
    }

    @Override
    public Plant findOne(Long id) {
        return jdbc.queryForObject("select id, name, type, planted from Plant where id=?",
                this::mapRowToPlant, id);
    }

    @Override
    public Long save(Plant plant) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", plant.getName());
        parameters.put("type", plant.getPlantType().toString());
        parameters.put("planted", false);
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return newId.longValue();
    }

    @Override
    public int delete(Long id) {
        return jdbc.update("delete from Plant where id=?", id);
    }

    @Override
    public int update(Plant plant) {
        return jdbc.update("update plant set name = ?, type = ?, planted = ? where id = ?",
                plant.getName(),
                plant.getPlantType().name(),
                plant.isPlanted(),
                plant.getId());
    }

    private Plant mapRowToPlant(ResultSet rs, int rowNum) throws SQLException {
        return new Plant(
                Long.parseLong(rs.getString("id")),
                rs.getString("name"),
                PlantType.valueOf(rs.getString("type")),
                rs.getBoolean("planted"));
    }


}
