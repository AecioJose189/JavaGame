package game.ui;

import java.io.IOException;
import java.util.Scanner;
import user.AuthManager;

public class AuthScreen {
    private static final AuthManager auth = AuthManager.getInstance();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o número

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    try {
                        auth.saveUsers();
                        System.out.println("Saindo do sistema. Até mais!");
                        System.exit(0);
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar usuários, tente novamente.");
                    }

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("===== Sistema de Cadastro e Login =====");
        System.out.println("1. Registrar Usuário");
        System.out.println("2. Login");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Digite o nome de usuário: ");
        String username = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String password = scanner.nextLine();

        if (auth.register(username, password) == true) {
            System.out.println("Usuário registrado com sucesso!");
        } else {
            System.out.println("Erro ao registrar usuário. Tente novamente.");
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Digite o nome de usuário: ");
        String username = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String password = scanner.nextLine();

        if (auth.login(username, password) == true) {
            System.out.println("Login bem-sucedido!");
            MainMenu.showMenu();
            return;
        } else {
            System.out.println("Login falhou. Verifique seu usuário e senha.");
        }
    }
}
