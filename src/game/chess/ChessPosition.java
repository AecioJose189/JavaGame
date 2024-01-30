package game.chess;

import game.board.Position;

public class ChessPosition {
    private char column;
    private int row;

    public ChessPosition(char column, int row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException("Error instantiating ChessPosition");
        }
        this.column = column;
        this.row = row;
    }

    protected Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    public static ChessPosition fromPosition(Position position) {
        char column = (char) ('a' + position.getColumn());
        int row = 8 - position.getRow();
        return new ChessPosition(column, row);
    }

    // getters and setters
    public char getColumn() {
        return column;
    }

    public void setColumn(char column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

}
