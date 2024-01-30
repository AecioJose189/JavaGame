package game.chess;

import game.Player;
import game.board.Board;
import game.board.Piece;
import game.board.Position;

public abstract class ChessPiece extends Piece {
    private Player player;
    private int moveCount;

    public ChessPiece(Board board, Player player) {
        super(board);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece != null && piece.getPlayer() != player;
    }

}