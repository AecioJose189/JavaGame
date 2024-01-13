package game;

import java.util.ArrayList;
import game.pieces.ChessPiece;

public class ChessMatch {
    private int turn;
    private Player currentPlayer;
    private boolean isCheck;
    private boolean isCheckMate;
    private boolean isDraw;
    private ArrayList<ChessPiece> player1CapturedPieces;
    private ArrayList<ChessPiece> player2CapturedPieces;
    private ArrayList<Play> plays;
    private Board board;

    //getters and setters

    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isIsCheck() {
        return this.isCheck;
    }

    public boolean getIsCheck() {
        return this.isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public boolean isIsCheckMate() {
        return this.isCheckMate;
    }

    public boolean getIsCheckMate() {
        return this.isCheckMate;
    }

    public void setIsCheckMate(boolean isCheckMate) {
        this.isCheckMate = isCheckMate;
    }

    public boolean isIsDraw() {
        return this.isDraw;
    }

    public boolean getIsDraw() {
        return this.isDraw;
    }

    public void setIsDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }

    public ArrayList<ChessPiece> getPlayer1CapturedPieces() {
        return this.player1CapturedPieces;
    }

    public void setPlayer1CapturedPieces(ArrayList<ChessPiece> player1CapturedPieces) {
        this.player1CapturedPieces = player1CapturedPieces;
    }

    public ArrayList<ChessPiece> getPlayer2CapturedPieces() {
        return this.player2CapturedPieces;
    }

    public void setPlayer2CapturedPieces(ArrayList<ChessPiece> player2CapturedPieces) {
        this.player2CapturedPieces = player2CapturedPieces;
    }

    public ArrayList<Play> getPlays() {
        return this.plays;
    }

    public void setPlays(ArrayList<Play> plays) {
        this.plays = plays;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}