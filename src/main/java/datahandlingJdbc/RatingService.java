package datahandlingJdbc;

import java.util.Map;

public class RatingService {

    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public int getMonthFullScore(String nickname, int month) {
        if (ratingRepository.getMonthFullScore(nickname, month) == null) {
            return 0;
        }
        return ratingRepository.getMonthFullScore(nickname, month);
    }

    public Long save(Rating rating) {
        if (!rating.isValid()) {
            return -1L;
        }
        boolean wasEliminatedFromCompetition = false;
        int month = rating.getMonth();
        if (month > 9 && month != 1) {
            wasEliminatedFromCompetition = getMonthFullScore(rating.getNickname(), month - 1) == 0;
        }
        if (month == 1) {
            wasEliminatedFromCompetition = getMonthFullScore(rating.getNickname(), 12) == 0;
        }
        if (wasEliminatedFromCompetition) {
            return -1L;
        }
        return ratingRepository.save(rating);
    }

    public Map<String, Integer> getRatingsByTask(int month, int taskNr) {
        return ratingRepository.getRatingsByTask(month, taskNr);
    }

    public String getRatingsByTaskAsCsv(int month, int taskNr) {
        StringBuilder sb = new StringBuilder();
        sb.append("NAME;POINTS");
        for (Map.Entry<String, Integer> entry : getRatingsByTask(month, taskNr).entrySet()) {
            sb.append("\n").append(entry.getKey()).append(";").append(entry.getValue());
        }
        return sb.toString();
    }


}
