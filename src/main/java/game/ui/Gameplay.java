package game.ui;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import game.Player;
import game.chess.ChessException;
import game.chess.ChessMatch;
import game.chess.ChessPiece;
import game.chess.ChessPosition;
import user.AuthManager;
import user.Match;

public class Gameplay{
    public static void play(ChessMatch chessMatch, Scanner sc, String adversary, List<ChessPiece> captured){

        while (!chessMatch.getIsCheckMate()) {
			try {
				Utils.clearScreen();
                System.out.println("Digite 0 para sair da partida");

                GameUtils.printMatch(chessMatch, captured, adversary);

                System.out.println();
                System.out.print("Fonte: ");
                String sourceString = sc.nextLine();

                if (sourceString.equals("0")) {
                    break;
                }

                ChessPosition source = GameUtils.readChessPosition(sourceString);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                Utils.clearScreen();
                GameUtils.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.println();
                System.out.print("Alvo: ");
                String targetString = sc.nextLine();
                ChessPosition target = GameUtils.readChessPosition(targetString);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

				if (chessMatch.getPromoted() != null) {
					System.out.print("Selecione uma peça para promover (B/H/T/Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}				

			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

		String scoreboard = chessMatch.getCurrentPlayer() == Player.WHITE ? "1-0" : "0-1";
		AuthManager.getInstance().addMatchToCurrentUser(new Match(adversary, scoreboard, LocalDateTime.now()));

		Utils.clearScreen();
		GameUtils.printMatch(chessMatch, captured, adversary);
    }
}