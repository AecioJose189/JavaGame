package user;

import java.util.ArrayList;

public class User {
    private String name;
    private int games;
    private int victories;
    private ArrayList<Match> history;

    public void addToHistory(Match match) {
        history.add(match);
    }

    public float getWinRate() {
        return (float) victories / games;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getGames() {
        return this.games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getVictories() {
        return this.victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public ArrayList<Match> getHistory() {
        return this.history;
    }

    public void setHistory(ArrayList<Match> history) {
        this.history = history;
    }

}
