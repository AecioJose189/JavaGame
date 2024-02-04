package game;

import game.chess.ChessPosition;
import game.chess.ChessPiece;

public class Movement implements Play {
    private ChessPiece piece;
    private ChessPosition from;
    private ChessPosition to;

    public ChessPiece getPiece() {
        return this.piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public ChessPosition getFrom() {
        return this.from;
    }

    public void setFrom(ChessPosition from) {
        this.from = from;
    }

    public ChessPosition getTo() {
        return this.to;
    }

    public void setTo(ChessPosition to) {
        this.to = to;
    }

}