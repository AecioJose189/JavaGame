package game.ui;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

import game.chess.ChessMatch;
import game.chess.ChessPiece;
import game.chess.ChessPosition;
import user.AuthManager;
import game.Player;

public class GameUtils {
    public static ChessPosition readChessPosition(String string) {
        try {
            char coluna = string.charAt(0);
            int linha = Integer.parseInt(string.substring(1));
            return new ChessPosition(coluna, linha);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro lendo a posição");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured, String adversary) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPiece(captured, adversary);
        System.out.println();
        System.out.println("Turno: " + chessMatch.getTurn());
        String player = chessMatch.getCurrentPlayer() == Player.WHITE
                ? AuthManager.getInstance().getCurrentUser().getUsername()
                : adversary;
        if (!chessMatch.getIsCheck()) {
            System.out.println("Aguardando jogador " + player);
            if (chessMatch.getIsCheck()) {
                System.out.println("Check!");
            }
        } else {
            System.out.println("CHECKMATE!");
            System.out.println("Vencedor: " + player);
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");

    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMovies) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMovies[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");

    }

    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(Utils.ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + Utils.ANSI_RESET);
        } else {
            if (piece.getPlayer() == Player.WHITE) {
                System.out.print(Utils.ANSI_WHITE + piece + Utils.ANSI_RESET);
            } else {
                System.out.print(Utils.ANSI_RED + piece + Utils.ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void printCapturedPiece(List<ChessPiece> captured, String adversary) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getPlayer() == Player.WHITE)
                .collect(Collectors.toList());
        List<ChessPiece> red = captured.stream().filter(x -> x.getPlayer() == Player.RED).collect(Collectors.toList());
        String player = AuthManager.getInstance().getCurrentUser().getUsername();

        System.out.println("Peças capturadas:");
        System.out.print(player + ": ");
        System.out.print(Utils.ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(Utils.ANSI_RESET);
        System.out.print(adversary + ": ");
        System.out.print(Utils.ANSI_RED);
        System.out.println(Arrays.toString(red.toArray()));
        System.out.print(Utils.ANSI_RESET);
    }
}