package MP1;

import java.util.Scanner;

public class GameManager {
    private ExplorersManager explorersManager;
    private Rooms rooms;
    private ActionLog actionLog;
    private EchoStoneLog echoStoneLog;
    private Scanner scanner;
    private boolean gameRunning;

    public GameManager() {
        this.explorersManager = new ExplorersManager();
        this.rooms = new Rooms();
        this.actionLog = new ActionLog();
        this.echoStoneLog = new EchoStoneLog();
        this.scanner = new Scanner(System.in);
        this.gameRunning = true;
    }

    public void initializeGame() {
        System.out.println("  TIME LOOP EXPLORERS");
        System.out.println(" ");

        System.out.print("Enter number of explorers (3-5): ");
        int numExplorers = scanner.nextInt();
        scanner.nextLine();

        if (numExplorers < 3 || numExplorers > 5) {
            System.out.println("Invalid number! Setting to 3 explorers.");
            numExplorers = 3;
        }

        for (int i = 1; i <= numExplorers; i++) {
            System.out.print("Enter name for Explorer " + i + ": ");
            String name = scanner.nextLine();
            Explorer explorer = new Explorer(name);
            explorersManager.addExplorer(explorer);
        }

        System.out.println("\nGame initialized with " + numExplorers + " explorers!");
        System.out.println("Objective: Use Echo Stones in sequence (1, 2, 3...) to break the time loop!");
        System.out.println("   ");
    }

    public void startGame() {
        initializeGame();

        while (gameRunning) {
            if (explorersManager.allExplorersDead()) {
                System.out.println("  GAME OVER - ALL EXPLORERS DIED");
                System.out.println(" ");
                actionLog.displayAllActions();
                gameRunning = false;
                break;
            }

            int actionResult = explorersManager.performAction(scanner, rooms, actionLog, echoStoneLog);

            if (actionResult == 1) {
                System.out.println("  VICTORY!");
                System.out.println("  The time loop has been broken!");
                System.out.println("Final Statistics:");
                actionLog.displayAllActions();
                gameRunning = false;
                break;

            } else if (actionResult == 2) {
                actionLog.displayAllActions();
                System.out.print(" Enter turn number to return to: ");
                int targetTurn = scanner.nextInt();
                scanner.nextLine();

                if (targetTurn < 1 || targetTurn >= explorersManager.getTurnCounter()) {
                    System.out.println("Invalid turn number!");
                    continue;
                }

                actionLog.restoreToTurn(targetTurn);

                int echoCountAtTurn = echoStoneLog.getCountAtTurn(actionLog, targetTurn);
                echoStoneLog.restoreToCount(echoCountAtTurn);

                int roomAtTurn = rooms.getRoomAtTurn(actionLog, targetTurn);
                rooms.restoreToRoom(roomAtTurn);

                explorersManager.restoreExplorersToTurn(actionLog, targetTurn);

                explorersManager.setTurnCounter(targetTurn);

                System.out.println(" TIME REWIND COMPLETE ");
                System.out.println("Game restored to Turn " + targetTurn);
            }
            explorersManager.nextTurn();
        }
    }
}