package game.pieces;

import game.Player;

public class ChessPiece extends Piece{
    private Player owner;

    //getters and setters

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

}