package datahandlingJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RatingRepository {

    private DataSource dataSource;

    public RatingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long save(Rating rating) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO ratings (nickname, month, task_nr, rating) VALUES (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, rating.getNickname());
            statement.setInt(2, rating.getMonth());
            statement.setInt(3, rating.getTaskNr());
            statement.setInt(4, rating.getRating_value());
            statement.executeUpdate();
            return getGeneratedId(statement);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot insert", e);
        }
    }

    private Long getGeneratedId(PreparedStatement statement) {
        try (ResultSet rs = statement.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new IllegalStateException("Cannot get id");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot get id", e);
        }
    }

    public Integer getMonthFullScore(String nickname, int month) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT SUM(rating) AS sum_of_ratings FROM ratings WHERE nickname = ? AND month = ?")) {
            statement.setString(1, nickname);
            statement.setInt(2, month);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot select", e);
        }

    }

    public Map<String, Integer> getRatingsByTask(int month, int taskNr) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT nickname, rating FROM ratings WHERE month = ? AND task_nr  = ?");){
            statement.setInt(1, month);
            statement.setInt(2, taskNr);
            ResultSet rs = statement.executeQuery();
            Map<String, Integer> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getString("nickname"), rs.getInt("rating"));
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot select from ratings table.", e);
        }
    }
}
