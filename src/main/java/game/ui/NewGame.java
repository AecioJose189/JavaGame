package game.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.chess.ChessMatch;
import game.chess.ChessPiece;

public class NewGame {
	public static void start() {
		Scanner sc = new Scanner(System.in);

		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();

		System.out.print("Digite o nome do adversário: ");
		String adversary = sc.nextLine();

		Gameplay.play(chessMatch, sc, adversary, captured);
	}
}
