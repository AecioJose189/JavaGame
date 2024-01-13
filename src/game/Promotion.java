package game;

import game.pieces.ChessPiece;

public class Promotion implements Play {
    private ChessPiece initialPieace;
    private ChessPiece finalPiece;

    //getters and setters

    public ChessPiece getInitialPieace() {
        return this.initialPieace;
    }

    public void setInitialPieace(ChessPiece initialPieace) {
        this.initialPieace = initialPieace;
    }

    public ChessPiece getFinalPiece() {
        return this.finalPiece;
    }

    public void setFinalPiece(ChessPiece finalPiece) {
        this.finalPiece = finalPiece;
    }

}