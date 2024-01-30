package user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import game.Movement;
import game.Player;

public class Match {
    private User player1;
    private String player2;

    private ArrayList<Movement> movements;

    private Player winner;
    private LocalDateTime date;

    public String getWinnerName() {
        return winner == Player.WHITE ? player1.getName() : player2;
    }

//getters and setters

    public User getPlayer1() {
        return this.player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return this.player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public ArrayList<Movement> getMovements() {
        return this.movements;
    }

    public void setMovements(ArrayList<Movement> movements) {
        this.movements = movements;
    }

    public Player getWinner() {
        return this.winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}