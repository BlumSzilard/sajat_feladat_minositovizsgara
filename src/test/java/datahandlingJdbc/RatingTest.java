package datahandlingJdbc;

import datahandlingJdbc.Rating;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingTest {
    @Test
    void testContructorWithId(){
        datahandlingJdbc.Rating rating = new datahandlingJdbc.Rating(42L,"Ede",9,1,10);
        assertEquals(42, rating.getId());
    }

    @Test
    void testContructorWithoutId(){
        datahandlingJdbc.Rating rating = new datahandlingJdbc.Rating("Pallas_Athéné",9,1,10);
        assertNull(rating.getId());

    }

    @Test
    void testGetters(){
        datahandlingJdbc.Rating rating = new Rating(0L,"Ede",9,1,10);
        assertEquals(0L, rating.getId());
        assertEquals("Ede", rating.getNickname());
        assertEquals(9, rating.getMonth());
        assertEquals(1, rating.getTaskNr());
        assertEquals(10, rating.getRating_value());
    }


}