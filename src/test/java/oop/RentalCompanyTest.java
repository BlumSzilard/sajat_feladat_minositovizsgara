/*
package oop;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentalCompanyTest {

    @Test
    void testFindToolById() {
        RentalCompany rentalCompany = new RentalCompany();
        rentalCompany.addTool(new HandyTool("a1", "torque wrench"));
        rentalCompany.addTool(new HandyTool("a2", "eccentric sander"));
        rentalCompany.addTool(new HandyTool("a3", "planer"));

        RentableTool tool = rentalCompany.findToolById("a2");

        assertEquals("eccentric sander", tool.getName());

        tool = rentalCompany.findToolById("cc");

        assertNull(tool);
    }

    @Test
    void testListFreeTools() {
        RentalCompany rentalCompany = new RentalCompany();

        assertEquals(0, rentalCompany.listFreeTools().size());

        rentalCompany.addTool(new HardTool("A1", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A2", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A3", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A4", "foldable stand, 4m", 2));
        rentalCompany.addTool(new HardTool("A5", "foldable stand, 4m", 2));
        rentalCompany.addTool(new HardTool("A6", "foldable stand, 6m", 2.5));


        assertEquals(6, rentalCompany.listFreeTools().size());

        rentalCompany.listFreeTools().get(0).rent(LocalDateTime.of(2025, 1, 1, 8, 0));

        assertEquals(5, rentalCompany.listFreeTools().size());

        rentalCompany.findToolById("A1").giveBack();

        assertEquals(6, rentalCompany.listFreeTools().size());
    }

    @Test
    void testListExpiredToolsToFileAsCsv() {
        RentalCompany rentalCompany = new RentalCompany();

        assertEquals(0, rentalCompany.listFreeTools().size());

        rentalCompany.addTool(new HardTool("A1", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A2", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A3", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A4", "foldable stand, 4m", 2));
        rentalCompany.addTool(new HardTool("A5", "foldable stand, 4m", 2));
        rentalCompany.addTool(new HardTool("A6", "foldable stand, 6m", 2.5));

        rentalCompany.findToolById("A1").rent(LocalDateTime.of(2020, 1, 1, 8, 0),
                LocalDateTime.of(2020, 1, 1, 8, 0).plusDays(2));
        rentalCompany.findToolById("A2").rent(LocalDateTime.of(2020, 1, 1, 8, 0),
                LocalDateTime.of(2020, 1, 1, 8, 0).plusDays(2));

        try {
            rentalCompany.listExpiredToolsToFileAsCsv(Path.of("src","test","resources","expired.csv"));
        } catch (Exception e) {
            fail("Exception thrown", e);
        }

        try {
            List<String> output = Files.readAllLines(Path.of("src","test","resources","expired.csv"));

            assertEquals(3, output.size());
            assertEquals("id;name;rentTo", output.get(0));
            assertEquals("A1;concrete mixer;2020-01-03T08:00", output.get(1));
            assertEquals("A2;concrete mixer;2020-01-03T08:00", output.get(2));

        } catch (Exception e) {
            fail("Exception thrown", e);
        }
    }

    @Test
    void testAgain_ListExpiredToolsToFileAsCsv() { //testing if existing file is overwritten
        RentalCompany rentalCompany = new RentalCompany();

        assertEquals(0, rentalCompany.listFreeTools().size());

        rentalCompany.addTool(new HardTool("A1", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A2", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A3", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A4", "foldable stand, 4m", 2));
        rentalCompany.addTool(new HardTool("A5", "foldable stand, 4m", 2));
        rentalCompany.addTool(new HardTool("A6", "foldable stand, 6m", 2.5));

        rentalCompany.findToolById("A1").rent(LocalDateTime.of(2020, 1, 1, 8, 0),
                LocalDateTime.of(2020, 1, 1, 8, 0).plusDays(2));
        rentalCompany.findToolById("A2").rent(LocalDateTime.of(2020, 1, 1, 8, 0),
                LocalDateTime.of(2020, 1, 1, 8, 0).plusDays(2));

        try {
            rentalCompany.listExpiredToolsToFileAsCsv(Path.of("src","test","resources","expired.csv"));
        } catch (Exception e) {
            fail("Exception thrown", e);
        }

        try {
            List<String> output = Files.readAllLines(Path.of("src","test","resources","expired.csv"));

            assertEquals(3, output.size());


        } catch (Exception e) {
            fail("Exception thrown", e);
        }
    }



    @Test
    void testCalculateTotalIncome() {
        RentalCompany rentalCompany = new RentalCompany();
        rentalCompany.addTool(new HardTool("A1", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A2", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A3", "concrete mixer", 2));
        rentalCompany.addTool(new HardTool("A4", "foldable stand, 4m", 2));
        rentalCompany.addTool(new HardTool("A5", "foldable stand, 4m", 2));
        rentalCompany.addTool(new HardTool("A6", "foldable stand, 6m", 2.5));

        assertEquals(0, rentalCompany.calculateTotalIncome());

        rentalCompany.listFreeTools().get(3).rent(LocalDateTime.now().minusSeconds(1),LocalDateTime.now().plusSeconds(1));
        rentalCompany.findToolById("A4").giveBack();

        assertEquals(2000, rentalCompany.calculateTotalIncome());

        rentalCompany.listFreeTools().get(5).rent(LocalDateTime.now().minusSeconds(1),LocalDateTime.now().plusSeconds(1));
        rentalCompany.findToolById("A6").giveBack();

        assertEquals(4500, rentalCompany.calculateTotalIncome());
    }

}*/
