package com.lacit.botanicalgarden.repos;

import com.lacit.botanicalgarden.domain.Role;
import com.lacit.botanicalgarden.domain.User;
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
public class JdbcUserRepo implements UserRepo {

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public JdbcUserRepo(JdbcTemplate jdbc, DataSource dataSource) {
        this.jdbc = jdbc;
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Usr").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("select id, username, password, role from Usr",
                this::mapRowToPlant);
    }

    @Override
    public User findOne(Long id) {
        return jdbc.queryForObject("select id, username, password, role from Usr where id=?",
                this::mapRowToPlant, id);
    }

    @Override
    public User findByUsername(String username) {
        return jdbc.queryForObject("select id, username, password, role from Usr where username=?",
                this::mapRowToPlant, username);
    }

    @Override
    public Long save(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());
        parameters.put("role", user.getRole().name());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return newId.longValue();
    }

    @Override
    public int update(User user) {
        return jdbc.update("update usr set username = ?, password = ?, role = ? where id = ?",
                user.getUsername(),
                user.getPassword(),
                user.getRole().name(),
                user.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbc.update("delete from usr where id=?", id);
    }

    private User mapRowToPlant(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                Long.parseLong(rs.getString("id")),
                rs.getString("username"),
                rs.getString("password"),
                Role.valueOf(rs.getString("role")));
    }
}
