package oop;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class RentableTool {
    private String id;
    private String name;
    private double priceMultiplier;
    private LocalDateTime rentFrom;
    private LocalDateTime rentTo;
    private int totalIncome;

    protected RentableTool(String id, String name, double priceMultiplier) {
        this.id = id;
        this.name = name;
        this.priceMultiplier = priceMultiplier;
        totalIncome = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    public LocalDateTime getRentFrom() {
        return rentFrom;
    }

    public LocalDateTime getRentTo() {
        return rentTo;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void rent(LocalDateTime from, LocalDateTime to){
        if (rentFrom != null){
           throw new AlreadyRentedException("Already rented!");
        }
        rentFrom = from;
        rentTo = to;
    }

    public void rent(LocalDateTime to){
        if (rentFrom != null){
            throw new AlreadyRentedException("Already rented!");
        }
        rentFrom = LocalDateTime.now();
        rentTo = to;
    }

    public int calculateRentalFee(){
        if (rentFrom == null || LocalDateTime.now().isBefore(rentFrom)){
            return 0;
        }

        Duration duration = Duration.between(rentFrom, LocalDateTime.now());
        int hours = (int) (duration.getSeconds() / 3600); //3600 = 60 sec * 60min
        if (duration.getSeconds() > hours*3600){
            hours++;
        }
        switch (hours){
            case 0,1,2,3: {
                return (int) (hours * Settings.BASIC_RENTAL_FEE * priceMultiplier);
            }
            case 4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24: {
                return (int) ((3 * Settings.BASIC_RENTAL_FEE * priceMultiplier) +
                        (hours - 3) * Settings.BASIC_RENTAL_FEE * priceMultiplier * 0.8);
            }
            default: {
                return (int) ((3 * Settings.BASIC_RENTAL_FEE * priceMultiplier) +
                        21 * Settings.BASIC_RENTAL_FEE * priceMultiplier * 0.8 +
                        (hours - 24) * Settings.BASIC_RENTAL_FEE * priceMultiplier * 0.5);
            }
        }
    }

    public void giveBack() {
        if (rentFrom != null){
            totalIncome += calculateRentalFee();
            rentFrom = null;
            rentTo = null;
        }
    }
}
