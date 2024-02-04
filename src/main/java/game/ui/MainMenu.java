package game.ui;

import java.util.Scanner;

public class MainMenu {
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            printMainMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Opção 1 selecionada - Nova partida");
                    Utils.clearScreen();
                    NewGame.start();
                    break;
                case 2:
                    System.out.println("Opção 2 selecionada - Histórico de partidas");
                    Utils.clearScreen();
                    UserHistory.showHistory();
                    break;
                case 0:
                    System.out.println("Opção 0 selecionada - Sair");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (choice != 0);
    }

    private static void printMainMenu() {
        Utils.clearScreen();

        System.out.println("===== Menu Principal =====");

        System.out.println("1. Nova Partida");
        System.out.println("2. Histórico de partidas");
        System.out.println("0. Sair");

        System.out.print("Escolha uma opção: ");
    }
}