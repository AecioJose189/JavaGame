package game.chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import game.Player;
import game.board.Board;
import game.board.Piece;
import game.board.Position;

import game.chess.pieces.Pawn;
import game.chess.pieces.King;
import game.chess.pieces.Bioshop;
import game.chess.pieces.Horse;
import game.chess.pieces.Queen;
import game.chess.pieces.Tower;

public class ChessMatch {
	private int turn;
	private Player currentPlayer;
	private Board board;
	private boolean isCheck;
	private boolean isCheckMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		this.board = new Board(8, 8);
		this.turn = 1;
		this.currentPlayer = Player.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getIsCheck() {
		return isCheck;
	}

	public boolean getIsCheckMate() {
		return isCheckMate;
	}

	public ChessPiece getPromoted() {
		return promoted;
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);

			}
		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You cab't put yourself in isCheck");
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target);

		// Movimento especial
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getPlayer() == Player.WHITE && target.getRow() == 0)
					|| (movedPiece.getPlayer() == Player.RED && target.getRow() == 7)) {
				promoted = (ChessPiece) board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		isCheck = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer))) {
			isCheckMate = true;
		} else {
			nextTurn();
		}

		if (movedPiece instanceof Pawn
				&& (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}

		return (ChessPiece) capturedPiece;
	}

	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("C") && !type.equals("T") & !type.equals("Q")) {
			throw new InvalidParameterException("Inavalid type for promotion");
		}
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);

		ChessPiece newPiece = newPiece(type, promoted.getPlayer());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);

		return newPiece;
	}

	private ChessPiece newPiece(String type, Player color) {
		if (type.equals("B"))
			return new Bioshop(board, color);
		if (type.equals("C"))
			return new Horse(board, color);
		if (type.equals("Q"))
			return new Queen(board, color);
		return new Tower(board, color);

	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		// movimento especial
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece torre = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(torre, targetT);
			torre.increaseMoveCount();

		}

		// movimento especial
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece torre = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(torre, targetT);
			torre.increaseMoveCount();

		}

		// Movimento especial
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position peaoPosition;
				if (p.getPlayer() == Player.WHITE) {
					peaoPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					peaoPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(peaoPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		// movimento especial rei
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece torre = (ChessPiece) board.removePiece(targetT);
			board.placePiece(torre, sourceT);
			torre.decreaseMoveCount();

		}

		// movimento especial torre
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece torre = (ChessPiece) board.removePiece(targetT);
			board.placePiece(torre, sourceT);
			torre.decreaseMoveCount();

		}
		// Movimento especial
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece peao = (ChessPiece) board.removePiece(target);
				Position peaoPosition;
				if (p.getPlayer() == Player.WHITE) {
					peaoPosition = new Position(3, target.getColumn());
				} else {
					peaoPosition = new Position(4, target.getColumn());
				}
				board.placePiece(peao, peaoPosition);
				capturedPiece = board.removePiece(peaoPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is not piece on sours position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getPlayer()) {
			throw new ChessException("The chose piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");

		}
	}

	public void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMoves(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Player.WHITE) ? Player.RED : Player.WHITE;
	}

	private Player opponent(Player color) {
		return (color == Player.WHITE) ? Player.RED : Player.WHITE;
	}

	private ChessPiece King(Player color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getPlayer() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no" + color + "King on the board");
	}

	private boolean testCheck(Player color) {
		Position KingPosition = King(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getPlayer() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[KingPosition.getRow()][KingPosition.getColumn()]) {
				return true;
			}
		}
		return false;

	}

	private boolean testCheckMate(Player color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getPlayer() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}

		}
		return true;
	}

	private void placeNewPiece(char coluna, int linha, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(coluna, linha).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Tower(board, Player.WHITE));
		placeNewPiece('b', 1, new Horse(board, Player.WHITE));
		placeNewPiece('c', 1, new Bioshop(board, Player.WHITE));
		placeNewPiece('d', 1, new Queen(board, Player.WHITE));
		placeNewPiece('e', 1, new King(board, Player.WHITE, this));
		placeNewPiece('f', 1, new Bioshop(board, Player.WHITE));
		placeNewPiece('g', 1, new Horse(board, Player.WHITE));
		placeNewPiece('h', 1, new Tower(board, Player.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Player.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Player.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Player.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Player.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Player.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Player.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Player.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Player.WHITE, this));

		placeNewPiece('a', 8, new Tower(board, Player.RED));
		placeNewPiece('b', 8, new Horse(board, Player.RED));
		placeNewPiece('c', 8, new Bioshop(board, Player.RED));
		placeNewPiece('d', 8, new Queen(board, Player.RED));
		placeNewPiece('e', 8, new King(board, Player.RED, this));
		placeNewPiece('f', 8, new Bioshop(board, Player.RED));
		placeNewPiece('g', 8, new Horse(board, Player.RED));
		placeNewPiece('h', 8, new Tower(board, Player.RED));
		placeNewPiece('a', 7, new Pawn(board, Player.RED, this));
		placeNewPiece('b', 7, new Pawn(board, Player.RED, this));
		placeNewPiece('c', 7, new Pawn(board, Player.RED, this));
		placeNewPiece('d', 7, new Pawn(board, Player.RED, this));
		placeNewPiece('e', 7, new Pawn(board, Player.RED, this));
		placeNewPiece('f', 7, new Pawn(board, Player.RED, this));
		placeNewPiece('g', 7, new Pawn(board, Player.RED, this));
		placeNewPiece('h', 7, new Pawn(board, Player.RED, this));
	}

}
