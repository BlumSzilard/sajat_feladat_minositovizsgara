package oop;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HardToolTest {

    @Test
    void testCreate() {
        HardTool hardTool = new HardTool("1", "pneumatic hammer", 2.0);

        assertEquals("1", hardTool.getId());
        assertEquals("pneumatic hammer", hardTool.getName());
        assertEquals(2.0, hardTool.getPriceMultiplier());
        assertEquals(0, hardTool.getTotalIncome());
        assertEquals(null, hardTool.getRentFrom());
        assertEquals(null, hardTool.getRentTo());
    }

    @Test
    void testRent() {
        HardTool hardTool = new HardTool("1", "pneumatic hammer", 2.0);
        hardTool.rent(LocalDateTime.now().plusDays(1));

        assertEquals(LocalDateTime.now().getMinute(), hardTool.getRentFrom().getMinute());
        assertEquals(LocalDateTime.now().plusDays(1), hardTool.getRentTo());
    }

    @Test
    void testRentTwiceError() {
        HardTool hardTool = new HardTool("1", "pneumatic hammer", 2.0);
        hardTool.rent(LocalDateTime.now().plusDays(1));

        assertThrows(AlreadyRentedException.class, () -> hardTool.rent(LocalDateTime.now().plusDays(2)));
    }

    @Test
    void testDatesAreNullWhenToolReturns() {
        HardTool hardTool = new HardTool("1", "pneumatic hammer", 2.0);
        hardTool.rent(LocalDateTime.now().plusDays(1));
        hardTool.giveBack();

        assertEquals(null, hardTool.getRentFrom());
        assertEquals(null, hardTool.getRentTo());
    }

    @Test
    void testCalculateRentPriceOneSecond() {
        HardTool hardTool = new HardTool("1", "pneumatic hammer", 2.0);
        hardTool.rent(LocalDateTime.now().plusHours(1));
        while (LocalDateTime.now().isBefore(hardTool.getRentFrom().plusSeconds(1))) {
            // wait
        }

        assertEquals(2000, hardTool.calculateRentalFee());
    }

    @Test
    void testCalculateRentPriceOneAndHalfHour() {
        HardTool hardTool = new HardTool("1", "pneumatic hammer", 2.0);
        hardTool.rent(LocalDateTime.now().minusHours(1).minusMinutes(29), LocalDateTime.now());

        assertEquals(4000, hardTool.calculateRentalFee());
    }

    @Test
    void testCalculateRentPriceOneDay() {
        HardTool hardTool = new HardTool("1", "pneumatic hammer", 2.0);
        hardTool.rent(LocalDateTime.now().minusDays(1).plusSeconds(1), LocalDateTime.now());

        assertEquals(24*2000, hardTool.calculateRentalFee());
    }

    @Test
    void testCalculateRentPriceThreeDays() {
        HardTool hardTool = new HardTool("1", "pneumatic hammer", 2.0);
        hardTool.rent(LocalDateTime.now().minusDays(3).plusSeconds(1), LocalDateTime.now());

        assertEquals(3*24*2000, hardTool.calculateRentalFee());
    }

    @Test
    void testTotalIncome() {
        HardTool hardTool = new HardTool("1", "pneumatic hammer", 2.0);
        hardTool.rent(LocalDateTime.now().minusDays(3).plusSeconds(1), LocalDateTime.now());
        hardTool.giveBack();

        assertEquals(3*24*2000, hardTool.getTotalIncome());

        hardTool.rent(LocalDateTime.now());
        while (LocalDateTime.now().isBefore(hardTool.getRentFrom().plusSeconds(1))) {
            // wait
        }
        hardTool.giveBack();

        assertEquals(3*24*2000 + 2000, hardTool.getTotalIncome());
    }

}