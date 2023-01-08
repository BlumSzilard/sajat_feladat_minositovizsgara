/*
package datahandling;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RatingServiceTest {

    RatingRepository ratingRepository;
    RatingService ratingService;
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
        ratingService = new RatingService(ratingRepository);
    }

    @Test
    void testSaveValidData_returnsPositveId() {
        Long id = ratingService.save(new Rating("Ede",9, 1, 5));
        Long id2 = ratingService.save(new Rating("Ede",10, 1, 5));
        assertTrue(id > 0);
        assertTrue(id2 > 0);
    }

    @Test
    void testSaveInvalidDate_returnsNegativeId() {
        Long id = ratingService.save(new Rating("Ede",8, 1, 5));
        Long id2 = ratingService.save(new Rating("Ede",13, 1, 5));
        Long id3 = ratingService.save(new Rating("Ede",0, 2, 5));

        assertTrue(id < 0);
        assertTrue(id2 < 0);
        assertTrue(id3<0);
    }

    @Test
    void testSaveInvalidTaskNr_returnsNegativeId() {
        Long id = ratingService.save(new Rating("Ede",9, 0, 5));
        Long id2 = ratingService.save(new Rating("Ede",10, -1, 5));
        Long id3 = ratingService.save(new Rating("Ede",11, 11, 5));

        assertTrue(id < 0);
        assertTrue(id2 < 0);
        assertTrue(id3 < 0);
    }

    @Test
    void testSaveInvalidScore_returnsNegativeId() {
        Long id = ratingService.save(new Rating("Ede",9, 1, 11));
        Long id2 = ratingService.save(new Rating("Ede",10, 1, -1));
        Long id3 = ratingService.save(new Rating("Ede",11, 1,42));

        assertTrue(id < 0);
        assertTrue(id2 < 0);
        assertTrue(id3 < 0);
    }

    @Test
    void testGetMonthFullScore(){
        ratingService.save(new Rating("Ede", 10, 7, 4));
        ratingService.save(new Rating("Ede", 9, 1, 6));
        ratingService.save(new Rating("Ede", 9, 2, 4));
        ratingService.save(new Rating("Lajos", 9, 3, 3));
        ratingService.save(new Rating("Lajos", 9, 1, 5));

        assertEquals(10, ratingService.getMonthFullScore("Ede",9));
        assertEquals(8, ratingService.getMonthFullScore("Lajos",9));
    }

    @Test
    void testGetMonthFullScore_isZeroWhenNoDbRows(){
        ratingService.save(new Rating("Ede", 10, 7, 4));
        ratingService.save(new Rating("Ede", 9, 1, 6));
        ratingService.save(new Rating("Ede", 9, 2, 4));
        ratingService.save(new Rating("Lajos", 9, 3, 3));
        ratingService.save(new Rating("Lajos", 9, 1, 5));

        assertEquals(0, ratingService.getMonthFullScore("Lajos",10));
    }

    @Test
    void testGetRatingsByTask() {
        ratingService.save(new Rating("Ede", 9, 1, 6));
        ratingService.save(new Rating("Ede", 9, 2, 4));
        ratingService.save(new Rating("Lajos", 9, 3, 3));
        ratingService.save(new Rating("Lajos", 9, 1, 5));

        Map<String, Integer> result = ratingService.getRatingsByTask(9, 1);
        assertEquals(2, result.size());
        assertEquals(6, result.get("Ede"));
        assertEquals(5, result.get("Lajos"));
    }

    @Test
    void testGetRatingsByTask_IfNoRating(){
        ratingService.save(new Rating("Ede", 9, 1, 6));
        ratingService.save(new Rating("Ede", 9, 2, 4));
        ratingService.save(new Rating("Lajos", 9, 3, 3));
        ratingService.save(new Rating("Lajos", 9, 1, 5));

        Map<String,Integer> result = ratingService.getRatingsByTask(10,1);
        assertEquals(0, result.size());
    }

    @Test
    void testGetRatingsByTaskAsCsv(){
        ratingService.save(new Rating("Ede", 9, 1, 6));
        ratingService.save(new Rating("Ede", 9, 2, 4));
        ratingService.save(new Rating("Lajos", 9, 3, 3));
        ratingService.save(new Rating("Lajos", 10, 1, 5));

        String result = ratingService.getRatingsByTaskAsCsv(9,1);
        String expectedResult = "NAME;POINTS\nEde;6";
        assertEquals(expectedResult, result);
    }


}*/
