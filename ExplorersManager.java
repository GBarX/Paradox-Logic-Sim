package MP1;

import java.util.Scanner;

public class ExplorersManager {
    private Node<Explorer> current;
    private int size;
    private boolean clockwise;
    private int turnCounter;

    public ExplorersManager() {
        this.current = null;
        this.size = 0;
        this.clockwise = true;
        this.turnCounter = 0;
    }

    public void addExplorer(Explorer explorer) {
        Node<Explorer> newNode = new Node<>(explorer);

        if (size == 0) {
            newNode.next = newNode;
            newNode.prev = newNode;
            current = newNode;
        } else {
            Node<Explorer> last = current.prev;

            newNode.next = current;
            newNode.prev = last;
            last.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    public int performAction(Scanner sc, Rooms rooms, ActionLog actionLog, EchoStoneLog echoLog) {
        Explorer currentExplorer = current.data;

        System.out.println("\n--- " + currentExplorer.getName() + "'s Turn ---");
        System.out.println(
                "HP: " + currentExplorer.getHitPoints() + " | Treasure: " + currentExplorer.getTreasurePoints());
        currentExplorer.displayInventory();

        System.out.println("\nChoose action:");
        System.out.println("1. Move to next room");
        System.out.println("2. Use Echo Stone");
        System.out.println("3. Use Paradox Stone");
        System.out.print("Your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();
        turnCounter++;

        if (choice == 1) {
            rooms.addRoom();
            int roomNumber = rooms.getCurrentRoomNumber();

            Action moveAction = new Action(currentExplorer.getName(), "MOVE", roomNumber, turnCounter);

            Rooms.EventResult eventResult = rooms.triggerEvent(currentExplorer);
            System.out.println("\nEVENT: " + eventResult.description);

            moveAction.setEventInfo(eventResult.description, eventResult.hpChange,
                    eventResult.treasureChange, eventResult.stoneFound);

            actionLog.addAction(moveAction);

            if (eventResult.turnOrderReversed) {
                reverseTurnOrder();
            }

            if (!currentExplorer.isAlive()) {
                System.out.println("WARNING: " + currentExplorer.getName() + " has died!");
            }

            return 0;

        } else if (choice == 2) {
            if (!currentExplorer.hasEchoStone()) {
                System.out.println("You don't have any Echo Stones!");
                turnCounter--;
                return performAction(sc, rooms, actionLog, echoLog);
            }

            Stone usedStone = findAndRemoveEchoStone(currentExplorer);
            if (usedStone == null) {
                System.out.println("Error: Could not use Echo Stone");
                turnCounter--;
                return performAction(sc, rooms, actionLog, echoLog);
            }

            int roomNumber = rooms.getCurrentRoomNumber();
            int stoneNumber = usedStone.getEchoNumber();

            Action useStoneAction = new Action(currentExplorer.getName(), "USE_ECHO_STONE", roomNumber, stoneNumber,
                    turnCounter);
            actionLog.addAction(useStoneAction);
            echoLog.addEchoStone(stoneNumber);

            System.out.println("\n" + currentExplorer.getName() + " used Echo Stone " + stoneNumber);
            echoLog.display();

            if (echoLog.checkWinCondition()) {
                return 1;
            }

            return 0;

        } else if (choice == 3) {
            if (!currentExplorer.hasParadoxStone()) {
                System.out.println("You don't have any Paradox Stones!");
                turnCounter--;
                return performAction(sc, rooms, actionLog, echoLog);
            }

            System.out.println("\n=== PARADOX STONE MENU ===");
            System.out.println("1. View Timeline");
            System.out.println("2. Forge New Path (Time Travel)");
            System.out.print("Your choice: ");

            int paradoxChoice = sc.nextInt();
            sc.nextLine();

            if (paradoxChoice == 1) {
                actionLog.displayAllActions();
                turnCounter--;
                return performAction(sc, rooms, actionLog, echoLog);

            } else if (paradoxChoice == 2) {
                Stone paradoxStone = findAndRemoveParadoxStone(currentExplorer);
                if (paradoxStone == null) {
                    System.out.println("Error: Could not use Paradox Stone");
                    turnCounter--;
                    return performAction(sc, rooms, actionLog, echoLog);
                }

                int roomNumber = rooms.getCurrentRoomNumber();
                Action paradoxAction = new Action(currentExplorer.getName(), "USE_PARADOX_STONE", roomNumber,
                        turnCounter);
                actionLog.addAction(paradoxAction);

                System.out.println("\n" + currentExplorer.getName() + " used Paradox Stone!");

                return 2;
            }
        }

        System.out.println("Invalid choice!");
        turnCounter--;
        return performAction(sc, rooms, actionLog, echoLog);
    }

    private Stone findAndRemoveEchoStone(Explorer explorer) {
        LinkedListStack<Stone> inventory = explorer.getInventory();
        LinkedListStack<Stone> tempStack = new LinkedListStack<>();
        Stone foundStone = null;

        while (!inventory.isEmpty()) {
            Stone stone = inventory.pop();
            if (stone.getType().equals("ECHO") && foundStone == null) {
                foundStone = stone;
            } else {
                tempStack.push(stone);
            }
        }

        while (!tempStack.isEmpty()) {
            inventory.push(tempStack.pop());
        }

        return foundStone;
    }

    private Stone findAndRemoveParadoxStone(Explorer explorer) {
        LinkedListStack<Stone> inventory = explorer.getInventory();
        LinkedListStack<Stone> tempStack = new LinkedListStack<>();
        Stone foundStone = null;

        while (!inventory.isEmpty()) {
            Stone stone = inventory.pop();
            if (stone.getType().equals("PARADOX") && foundStone == null) {
                foundStone = stone;
            } else {
                tempStack.push(stone);
            }
        }

        while (!tempStack.isEmpty()) {
            inventory.push(tempStack.pop());
        }

        return foundStone;
    }

    public void nextTurn() {
        if (current == null) {
            return;
        }

        int checked = 0;
        do {
            if (clockwise) {
                current = current.next;
            } else {
                current = current.prev;
            }
            checked++;
        } while (!current.data.isAlive() && checked < size);
    }

    public Explorer getCurrentExplorer() {
        if (current != null) {
            return current.data;
        }
        return null;
    }

    public void reverseTurnOrder() {
        clockwise = !clockwise;
        if (clockwise) {
            System.out.println("Turn order is now: CLOCKWISE");
        } else {
            System.out.println("Turn order is now: COUNTER-CLOCKWISE");
        }
    }

    public boolean allExplorersDead() {
        if (size == 0) {
            return true;
        }

        Node<Explorer> temp = current;
        for (int i = 0; i < size; i++) {
            if (temp.data.isAlive()) {
                return false;
            }
            temp = temp.next;
        }
        return true;
    }

    public int getLivingCount() {
        if (size == 0) {
            return 0;
        }

        int count = 0;
        Node<Explorer> temp = current;
        for (int i = 0; i < size; i++) {
            if (temp.data.isAlive()) {
                count++;
            }
            temp = temp.next;
        }
        return count;
    }

    public void restoreExplorersToTurn(ActionLog actionLog, int targetTurn) {
        Node<Explorer> temp = current;
        for (int i = 0; i < size; i++) {
            temp.data.setHitPoints(3);
            temp.data.setTreasurePoints(0);
            temp.data.clearInventory();
            temp = temp.next;
        }

        Node<Action> action = actionLog.getFirst();

        while (action != null && action.data.getTurnNumber() <= targetTurn) {
            Explorer explorer = findExplorerByName(action.data.getExplorerName());

            if (explorer != null) {
                if (action.data.getHpChange() != 0) {
                    if (action.data.getHpChange() > 0) {
                        explorer.heal(action.data.getHpChange());
                    } else {
                        explorer.takeDamage(-action.data.getHpChange());
                    }
                }

                if (action.data.getTreasureChange() != 0) {
                    explorer.addTreasure(action.data.getTreasureChange());
                }
            }

            action = action.next;
        }

        System.out.println("Explorers restored to Turn " + targetTurn);
    }

    private Explorer findExplorerByName(String name) {
        Node<Explorer> temp = current;
        for (int i = 0; i < size; i++) {
            if (temp.data.getName().equals(name)) {
                return temp.data;
            }
            temp = temp.next;
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter(int turn) {
        this.turnCounter = turn;
    }
}