package oop;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BrandedHandyToolTest {

        @Test
        void testCreate() {
            BrandedHandyTool brandedHandyTool = new BrandedHandyTool("1", "Makita screwdriver");
            assertEquals("1", brandedHandyTool.getId());
            assertEquals("Makita screwdriver", brandedHandyTool.getName());
            assertEquals(1.5, brandedHandyTool.getPriceMultiplier());
            assertEquals(0, brandedHandyTool.getTotalIncome());
            assertEquals(null, brandedHandyTool.getRentFrom());
            assertEquals(null, brandedHandyTool.getRentTo());
        }

        @Test
        void testRent() {
            BrandedHandyTool brandedHandyTool = new BrandedHandyTool("1", "Makita screwdriver");
            brandedHandyTool.rent(LocalDateTime.now().plusDays(1));
            assertEquals(LocalDateTime.now().getMinute(), brandedHandyTool.getRentFrom().getMinute());
            assertEquals(LocalDateTime.now().plusDays(1).getMinute(), brandedHandyTool.getRentTo().getMinute());
        }

        @Test
        void testRentTwiceError() {
            BrandedHandyTool brandedHandyTool = new BrandedHandyTool("1", "Makita screwdriver");
            brandedHandyTool.rent(LocalDateTime.now().plusDays(1));
            assertThrows(AlreadyRentedException.class, () -> brandedHandyTool.rent(LocalDateTime.now().plusDays(2)));
        }

        @Test
        void testDatesAreNullWhenToolReturns() {
            BrandedHandyTool brandedHandyTool = new BrandedHandyTool("1", "Makita screwdriver");
            brandedHandyTool.rent(LocalDateTime.now().plusDays(1));
            brandedHandyTool.giveBack();

            assertEquals(null, brandedHandyTool.getRentFrom());
            assertEquals(null, brandedHandyTool.getRentTo());
        }

        @Test
        void testCalculateRentPriceOneSecond() {
            BrandedHandyTool brandedHandyTool = new BrandedHandyTool("1", "Makita screwdriver");
            brandedHandyTool.rent(LocalDateTime.now().plusHours(1));
            while (LocalDateTime.now().isBefore(brandedHandyTool.getRentFrom().plusSeconds(1))) {
                // wait
            }
            assertEquals(1500, brandedHandyTool.calculateRentalFee());
        }

        @Test
        void testCalculateRentPriceOneAndHalfHour() {
            BrandedHandyTool brandedHandyTool = new BrandedHandyTool("1", "Makita screwdriver");
            brandedHandyTool.rent(LocalDateTime.now().minusHours(1).minusMinutes(29), LocalDateTime.now());

            assertEquals(3000, brandedHandyTool.calculateRentalFee());
        }

        @Test
        void testCalculateRentPriceOneDay() {
            BrandedHandyTool brandedHandyTool = new BrandedHandyTool("1", "Makita screwdriver");
            brandedHandyTool.rent(LocalDateTime.now().minusDays(1), LocalDateTime.now());

            assertEquals(3*1500 + 21*1500*0.8, brandedHandyTool.calculateRentalFee());
        }

        @Test
        void testCalculateRentPriceThreeDays() {
            BrandedHandyTool brandedHandyTool = new BrandedHandyTool("1", "Makita screwdriver");
            brandedHandyTool.rent(LocalDateTime.now().minusDays(3), LocalDateTime.now());

            assertEquals(3*1500 + 21*1500*0.8 + 2*24*1500*0.5, brandedHandyTool.calculateRentalFee());
        }

        @Test
        void testingTotalIncome() {
            BrandedHandyTool brandedHandyTool = new BrandedHandyTool("1", "Makita screwdriver");
            brandedHandyTool.rent(LocalDateTime.now().minusDays(3), LocalDateTime.now());
            brandedHandyTool.giveBack();
            assertEquals(65700, brandedHandyTool.getTotalIncome());

            brandedHandyTool.rent(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
            while (LocalDateTime.now().isBefore(brandedHandyTool.getRentFrom().plusSeconds(1))) {
                // wait
            }
            brandedHandyTool.giveBack();
            assertEquals(65700 + 1500, brandedHandyTool.getTotalIncome());
        }



}
