package game;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int boardType;
        while (true) {
            System.out.println("Enter the type of board (0 for Mnk, 1 for Round)");
            try {
                String type = sc.next();
                boardType = Integer.parseInt(type);
                if (boardType > 1 || boardType < 0) {
                    System.out.println("Wrong board type");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong board type");
            } catch (NoSuchElementException e) {
                System.out.println("Error: expected more parameters");
                return;
            }
        }
        Board board;
        if (boardType == 0) {
            int m, n, k;
            System.out.println("Enter board parameters and win streak");
            while (true) {
                try {
                    String mStr = sc.next();
                    m = Integer.parseInt(mStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Board height is not an integer");
                } catch (NoSuchElementException e) {
                    System.out.println("Error: expected more parameters");
                    return;
                }
            }
            while (true) {
                try {
                    String nStr = sc.next();
                    n = Integer.parseInt(nStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Board width is not an integer");
                } catch (NoSuchElementException e) {
                    System.out.println("Error: expected more parameters");
                    return;
                }
            }
            while (true) {
                try {
                    String kStr = sc.next();
                    k = Integer.parseInt(kStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Win streak is not an integer");
                } catch (NoSuchElementException e) {
                    System.out.println("Error: expected more parameters");
                    return;
                }
            }
            board = new MnkBoard(n, m, k);
        } else {
            int n, k;
            System.out.println("Enter board radius and win streak");
            while (true) {
                try {
                    String nStr = sc.next();
                    n = Integer.parseInt(nStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Board radius is not an integer");
                } catch (NoSuchElementException e) {
                    System.out.println("Error: expected more parameters");
                    return;
                }
            }
            while (true) {
                try {
                    String kStr = sc.next();
                    k = Integer.parseInt(kStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Win streak is not an integer");
                } catch (NoSuchElementException e) {
                    System.out.println("Error: expected more parameters");
                    return;
                }
            }
            board = new RoundBoard(n, k);
        }
        int m;
        System.out.println("Enter number of participants");
        while (true) {
            try {
                String mStr = sc.next();
                m = Integer.parseInt(mStr);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Number of participants is not an integer");
            } catch (NoSuchElementException e) {
                System.out.println("Error: expected more parameters");
                return;
            }
        }
        Player[] players = new Player[m];
        for (int i = 0; i < m; i++) {
            players[i] = new RandomPlayer();
        }
        Tournament tournament = new Tournament(players, board, true);
        tournament.playTournament();
        tournament.printResults();
    }
}
