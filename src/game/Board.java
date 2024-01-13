package game;

import game.pieces.Piece;

public class Board {
    private int rows;
    private int columns;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public Piece piece(int row, int column) {
        return null;
    }

    public Piece piece(Position position) {
        return null;
    }

    public void placePiece(Piece piece, Position position) {

    }

    public Piece removePiece(Position position) {
        return null;
    }

    public boolean positionExists(Position position) {
        return false;
    }

    public boolean thereIsAPiece(Position position) {
        return false;
    }
    //getters and setters

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

}