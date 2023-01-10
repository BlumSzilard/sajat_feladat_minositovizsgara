package datahandling;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class RatingRepository {
    private JdbcTemplate jdbcTemplate;

    public RatingRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long save(Rating rating){
        String sqlInsert = """
                INSERT INTO ratings (nickname, month, task_nr, rating)
                VALUES (?,?,?,?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,rating.getNickname());
            ps.setInt(2, rating.getMonth());
            ps.setInt(3, rating.getTaskNr());
            ps.setInt(4, rating.getRating_value());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new IllegalStateException("Unable to retrieve the generated key for the insert");
        }
    }

    public Integer getMonthFullScore(String nickname, int month){
        String sqlSelect = """
                SELECT SUM(rating) AS sum_of_ratings
                FROM ratings
                WHERE nickname = ?
                AND month = ?
                """;
        return jdbcTemplate.query(sqlSelect,
                (rs,rn) -> rs.getInt("sum_of_ratings"),
                nickname, month).get(0);
    }

    public Map<String, Integer> getRatingsByTask(int month, int taskNr){
        String sqlSelect = """
                SELECT nickname, rating FROM ratings
                WHERE month = ?
                AND  task_nr = ?
                """;
        Map<String, Integer> result = new HashMap<>();
        jdbcTemplate.query(sqlSelect,
                (rs,rn) -> result.put(rs.getString("nickname"), rs.getInt("rating")),
                month, taskNr);
        return result;
    }


}
