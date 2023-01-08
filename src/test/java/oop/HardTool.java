package oop;

import java.time.Duration;
import java.time.LocalDateTime;

public class HardTool extends RentableTool {

    public HardTool(String id, String name, double priceMultiplier) {
        super(id, name, priceMultiplier);
    }

    @Override
    public int calculateRentalFee(){
        if (getRentFrom() == null || LocalDateTime.now().isBefore(getRentFrom())){
            return 0;
        }
        Duration duration = Duration.between(getRentFrom(), LocalDateTime.now());
        int hours = (int) (duration.getSeconds() / 3600); //3600 = 60 sec * 60min
        if (duration.getSeconds() > hours*3600){
            hours++;
        }
        return (int) (hours * Settings.BASIC_RENTAL_FEE * getPriceMultiplier());
    }
}
