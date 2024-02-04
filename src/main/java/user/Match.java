package user;

import java.time.LocalDateTime;

public class Match {
    private String adversary;
    private String scoreboard;
    private LocalDateTime date;

    public Match() {
    }

    public Match(String adversary, String scoreboard, LocalDateTime date) {
        this.adversary = adversary;
        this.scoreboard = scoreboard;
        this.date = date;
    }   

    //getters and setters
    public String getAdversary() {
        return this.adversary;
    }

    public void setAdversary(String player2) {
        this.adversary = player2;
    }

    public String getScoreboard() {
        return this.scoreboard;
    }

    public void setScoreboard(String scoreboard) {
        this.scoreboard = scoreboard;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
