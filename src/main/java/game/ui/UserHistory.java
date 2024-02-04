package game.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import user.User;
import user.Match;
import user.AuthManager;

public class UserHistory {
    public static void showHistory() {
        User currentUser = AuthManager.getInstance().getCurrentUser();
        Scanner scanner = new Scanner(System.in);

        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        String RESET = "\u001B[0m";

        System.out.println("======== SEU HISTÓRICO DE PARTIDAS =========");
        System.out.println("Data e Hora         Usuário       Adversário");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        for (int i = 0; i < currentUser.getHistory().size(); i++) {
            Match match = currentUser.getHistory().get(i);
            LocalDateTime data = match.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String formattedDate = data.format(formatter);
            String user = currentUser.getUsername();
            String opponent = match.getAdversary();

            if (match.getScoreboard().equals("1-0")) {
                System.out.println(formattedDate + " " + GREEN + Utils.abbreviate(user, 10, true) + " 1" + RESET
                        + " x " + "0 " + Utils.abbreviate(opponent, 10, false)); 
            } else {
                System.out.println(formattedDate + " " + Utils.abbreviate(user, 10, true) + " 0" + " x " + RED + "1 "
                        + Utils.abbreviate(opponent, 10, false) + RESET);
            }
            System.out.println("--------------------------------------------");
        }

        System.out.println("Aperte ENTER para voltar ao menu principal");
        scanner.nextLine();
    }
}