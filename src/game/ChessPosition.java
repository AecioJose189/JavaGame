package game;

public class ChessPosition {
    private char column;
    private int row;

    public ChessPosition(char column, int row) {
        this.column = column;
        this.row = row;
    }

    public Position toPosition() {
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
