package datahandling;

import java.util.Map;

public class RatingService {
    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Long save(Rating rating) {
        if (!rating.isValid()) {
            return -1L;
        }
        int month = rating.getMonth();
        boolean reachedAchieveInPreviousMonth = true;
        if (month != 9 && month != 1){
            reachedAchieveInPreviousMonth = ratingRepository.getMonthFullScore(rating.getNickname(), month-1) > 0;
        }
        if (month == 1){
            reachedAchieveInPreviousMonth = ratingRepository.getMonthFullScore(rating.getNickname(), 12) > 0;
        }

        if (!reachedAchieveInPreviousMonth) {
            return -1L;
        }

        return ratingRepository.save(rating);
    }

    public Map<String, Integer> getRatingsByTask(int month, int taskNr) {
        return ratingRepository.getRatingsByTask(month, taskNr);
    }

    public String getRatingsByTaskAsCsv(int month, int taskNr) {
        Map<String, Integer> resultMap = ratingRepository.getRatingsByTask(month, taskNr);
        StringBuilder result = new StringBuilder();
        result.append( "NAME;POINTS");
        for (Map.Entry<String, Integer> actual: resultMap.entrySet()){
            result.append("\n"+actual.getKey()+";"+actual.getValue());
        }
        return result.toString();
    }

    public int getMonthFullScore(String nickname, int month) {
        Integer result = ratingRepository.getMonthFullScore(nickname, month);
        if (result == null) {
            return 0;
        }
        return result;
    }
}
