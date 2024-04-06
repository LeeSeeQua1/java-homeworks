package game;

import java.util.*;

public class Tournament {
    private final Player[] players;
    private final int[] results;
    private int remainingParticipants;
    private int roundCount;
    private final Board board;
    private final boolean log;

    public Tournament(Player[] playersArr, Board tournamentBoard, boolean log) {
        remainingParticipants = playersArr.length;
        players = playersArr;
        roundCount = 0;
        results = new int[remainingParticipants];
        board = tournamentBoard;
        this.log = log;
    }

    public void playTournament() {
        playFirstRound();
        while (remainingParticipants > 1) {
            playRound(remainingParticipants / 2);
        }
    }

    private int Max2Pow(int n) {
        int pow = 1;
        while (pow <= n) {
            pow *= 2;
        }
        return pow / 2;
    }

    private void playFirstRound() {
        int powOf2 = Max2Pow(remainingParticipants);
        if (remainingParticipants == powOf2) {
            playRound(remainingParticipants / 2);
        } else {
            playRound(remainingParticipants - powOf2);
        }
    }

    private void playRound(int pairs) {
        roundCount++;
        int oddPlayer = -1;
        int evenPlayer;
        for (int i = 0; i < results.length; i++) {
            if (results[i] == 0) {
                if (oddPlayer == -1) {
                    oddPlayer = i;
                } else {
                    evenPlayer = i;
                    int loser = playTillWin(oddPlayer, evenPlayer);
                    if (loser == 1) {
                        results[oddPlayer] = roundCount;
                    } else {
                        results[evenPlayer] = roundCount;
                    }
                    pairs--;
                    remainingParticipants--;
                    oddPlayer = -1;
                }
            }
            if (pairs == 0) {
                break;
            }
        }
    }

    private int playTillWin(int oddPlayer, int evenPlayer) {
        Random random = new Random();
        while (true) {
            int order = random.nextInt(2);
            int first = order == 0 ? oddPlayer : evenPlayer;
            int second = order == 1 ? oddPlayer : evenPlayer;
            log("Player " + (first + 1) + " is playing as Player 1");
            log("Player " + (second + 1) + " is playing as Player 2");
            Game game = match(order, players[oddPlayer], players[evenPlayer]);
            int result = game.play(board);
            if (result != 0) {
                if (order == 1) {
                    return result;
                } else {
                    return 3 - result;
                }
            }
        }
    }

    private Game match(int order, Player p1, Player p2) {
        final Game game;
        if (order == 0) {
            game = new Game(log, p1, p2);
        } else {
            game = new Game(log, p2, p1);
        }
        return game;
    }

    public void printResults() {
        Map<Integer, ArrayList<Integer>> map = new TreeMap<>();
        for (int i = 0 ; i < results.length; i++) {
            int key;
            if (results[i] == 0) {
                key = 1;
            } else {
                key = roundCount - results[i] + 2;
            }
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(i + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (int key : map.keySet()) {
            sb.append(key);
            sb.append(": ");
            ArrayList<Integer> value = map.get(key);
            for (int i = 0; i < value.size(); i++) {
                sb.append(value.get(i));
                if (i < value.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(System.lineSeparator());
        }
        System.out.println(sb);
    }
    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}