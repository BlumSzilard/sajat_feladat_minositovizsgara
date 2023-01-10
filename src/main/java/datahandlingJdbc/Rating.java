package datahandlingJdbc;



public class Rating {

    private Long id;
    private String nickname;
    private int month;
    private int taskNr;
    private int rating_value;

    public Rating(Long id, String nickname, int month, int taskNr, int rating_value) {
        this.id = id;
        this.nickname = nickname;
        this.month = month;
        this.taskNr = taskNr;
        this.rating_value = rating_value;
    }

    public Rating(String nickname, int month, int taskNr, int rating_value) {
        this.nickname = nickname;
        this.month = month;
        this.taskNr = taskNr;
        this.rating_value = rating_value;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public int getMonth() {
        return month;
    }

    public int getTaskNr() {
        return taskNr;
    }

    public int getRating_value() {
        return rating_value;
    }

    public boolean isValid() {
        if (nickname == null) {
            return false;
        }
        if (month < 1 || month > 12 || month == 7 || month == 8) {
            return false;
        }
        if (taskNr < 1 || taskNr > 10) {
            return false;
        }
        if (rating_value < 0 || rating_value > 10) {
            return false;
        }
        return true;
    }

}
