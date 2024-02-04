package user;

import java.util.List;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private List<Match> history;

    public User() {
        this.username = "";
        this.password = "";
        this.history = new ArrayList<Match>();
    }

    public User(String username, String password,  List<Match> history) {
        this.username = username;
        this.password = password;
        this.history = history;
    }

    public void addToHistory(Match match) {
        history.add(match);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Match> getHistory() {
        return this.history;
    }

    public void setHistory(List<Match> history) {
        this.history = history;
    }
}
