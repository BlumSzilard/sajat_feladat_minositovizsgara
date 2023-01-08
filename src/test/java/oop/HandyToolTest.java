package oop;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HandyToolTest {

    @Test
    void testCreate() {
        HandyTool handyTool = new HandyTool("1", "cordless screwdriver");
        assertEquals("1", handyTool.getId());
        assertEquals("cordless screwdriver", handyTool.getName());
        assertEquals(1.0, handyTool.getPriceMultiplier());
        assertEquals(0, handyTool.getTotalIncome());
        assertEquals(null, handyTool.getRentFrom());
        assertEquals(null, handyTool.getRentTo());
    }

    @Test
    void testRent() {
        HandyTool handyTool = new HandyTool("1", "cordless screwdriver");
        handyTool.rent(LocalDateTime.now().plusDays(1));
        assertEquals(LocalDateTime.now().getMinute(), handyTool.getRentFrom().getMinute());
        assertEquals(LocalDateTime.now().plusDays(1).getMinute(), handyTool.getRentTo().getMinute());
    }

    @Test
    void testRentTwiceError() {
        HandyTool handyTool = new HandyTool("1", "cordless screwdriver");
        handyTool.rent(LocalDateTime.now().plusDays(1));
        assertThrows(AlreadyRentedException.class, () -> handyTool.rent(LocalDateTime.now().plusDays(2)));
    }

    @Test
    void testDatesAreNullWhenToolReturns() {
        HandyTool handyTool = new HandyTool("1", "cordless screwdriver");
        handyTool.rent(LocalDateTime.now().plusDays(1));
        handyTool.giveBack();

        assertEquals(null, handyTool.getRentFrom());
        assertEquals(null, handyTool.getRentTo());
    }

    @Test
    void testCalculateRentPriceOneSecond() {
        HandyTool handyTool = new HandyTool("1", "cordless screwdriver");
        handyTool.rent(LocalDateTime.now().plusHours(1));
        while (LocalDateTime.now().isBefore(handyTool.getRentFrom().plusSeconds(1))) {
            // wait
        }
        assertEquals(1000, handyTool.calculateRentalFee());
    }


    @Test
    void testCalculateRentPriceOneAndHalfHour() {
        HandyTool handyTool = new HandyTool("1", "cordless screwdriver");
        handyTool.rent(LocalDateTime.now().minusHours(1).minusMinutes(30).plusSeconds(1), LocalDateTime.now());

        assertEquals(2000, handyTool.calculateRentalFee());
    }

    @Test
    void testCalculateRentPriceOneDay() {
        HandyTool handyTool = new HandyTool("1", "cordless screwdriver");
        handyTool.rent(LocalDateTime.now().minusDays(1).plusSeconds(1), LocalDateTime.now());

        assertEquals(19800, handyTool.calculateRentalFee()); //3*1000 + 21*800
    }

    @Test
    void testCalculateRentPriceThreeDays() {
        HandyTool handyTool = new HandyTool("1", "cordless screwdriver");
        handyTool.rent(LocalDateTime.now().minusDays(3).plusSeconds(1), LocalDateTime.now());

        assertEquals(43800, handyTool.calculateRentalFee()); //3*1000 + 21*800 + 2*24*500
    }

    @Test
    void testingTotalIncome() {
        HandyTool handyTool = new HandyTool("1", "cordless screwdriver");
        handyTool.rent(LocalDateTime.now().minusDays(3).plusSeconds(1), LocalDateTime.now());
        handyTool.giveBack();

        assertEquals(43800, handyTool.getTotalIncome());

        handyTool.rent(LocalDateTime.now().plusHours(1));
        while (LocalDateTime.now().isBefore(handyTool.getRentFrom().plusSeconds(1))) {
            // wait
        }
        handyTool.giveBack();

        assertEquals(44800, handyTool.getTotalIncome());}
}