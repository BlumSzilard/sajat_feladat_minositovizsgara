package datahandling;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RatingRepositoryTest {

    RatingRepository ratingRepository;
    @BeforeEach
    void init() {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/labs?useUnicode=true");
            dataSource.setUser("labs");
            dataSource.setPassword("OraEtLabora");
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot connect!", se);
        }

        Flyway flyway = Flyway.configure().cleanDisabled(false).dataSource(dataSource).load();

        flyway.clean();
        flyway.migrate();

        ratingRepository = new RatingRepository(dataSource);
    }

    @Test
    void testSave_IdNotNull() {
        Long id = ratingRepository.save(new Rating("Ede",1, 1, 5));
        assertTrue(id != null);
    }

    @Test
    void testSave_IdsAreNotSame() {
        Long id = ratingRepository.save(new Rating("Ede",1, 1, 5));
        Long id2 = ratingRepository.save(new Rating("Ede",1, 1, 5));
        assertTrue(id != null);
        assertTrue(id2 != null);
        assertFalse(id == id2);
    }

    @Test
    void testGetMonthFullScore(){
        ratingRepository.save(new Rating("Ede", 12, 7, 4));
        ratingRepository.save(new Rating("Ede", 1, 1, 6));
        ratingRepository.save(new Rating("Ede", 1, 2, 4));
        ratingRepository.save(new Rating("Lajos", 1, 3, 3));
        ratingRepository.save(new Rating("Lajos", 1, 1, 5));

        assertEquals(10, ratingRepository.getMonthFullScore("Ede",1));
        assertEquals(8, ratingRepository.getMonthFullScore("Lajos",1));
        assertNull(ratingRepository.getMonthFullScore("Ede",2));
    }

    @Test
    void testGetMonthFullScore_IfNoRating(){
        assertNull(ratingRepository.getMonthFullScore("John_Doe",1));
    }

    @Test
    void testGetRatingsByTask(){
        ratingRepository.save(new Rating("Ede", 1, 1, 6));
        ratingRepository.save(new Rating("Ede", 1, 2, 4));
        ratingRepository.save(new Rating("Lajos", 1, 3, 3));
        ratingRepository.save(new Rating("Lajos", 1, 1, 5));

        Map<String,Integer> result = ratingRepository.getRatingsByTask(1,1);
        assertEquals(2, result.size());
        assertEquals(6, result.get("Ede"));
        assertEquals(5, result.get("Lajos"));
    }

    @Test
    void testGetRatingsByTask_IfNoRating(){
        ratingRepository.save(new Rating("Ede", 1, 1, 6));
        ratingRepository.save(new Rating("Ede", 1, 2, 4));
        ratingRepository.save(new Rating("Lajos", 1, 3, 3));
        ratingRepository.save(new Rating("Lajos", 1, 1, 5));

        Map<String,Integer> result = ratingRepository.getRatingsByTask(12,1);
        assertEquals(0, result.size());
    }

}