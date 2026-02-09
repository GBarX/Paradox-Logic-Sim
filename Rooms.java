package MP1;

public class Rooms {
    public Node<Integer> first;
    public Node<Integer> last;
    private int currentRoomNumber;
    private int nextEchoStoneNumber;

    public Rooms() {
        this.first = null;
        this.last = null;
        this.currentRoomNumber = 1;
        this.nextEchoStoneNumber = 1;
        addRoom();
    }

    public void addRoom() {
        Node<Integer> newNode = new Node<>(currentRoomNumber);

        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        currentRoomNumber++;
    }

    public EventResult triggerEvent(Explorer explorer) {
        int randomNo = (int) (Math.random() * 11) + 1;
        String eventDescription = "";
        int hpChange = 0;
        int treasureChange = 0;
        String stoneFound = null;
        boolean turnOrderReversed = false;

        if (randomNo == 1) {
            Stone echoStone = new Stone("ECHO", nextEchoStoneNumber);
            explorer.addStone(echoStone);
            eventDescription = explorer.getName() + " found Echo Stone " + nextEchoStoneNumber;
            stoneFound = "ECHO:" + nextEchoStoneNumber;
            nextEchoStoneNumber++;

        } else if (randomNo == 2) {
            Stone paradoxStone = new Stone("PARADOX");
            explorer.addStone(paradoxStone);
            eventDescription = explorer.getName() + " found a Paradox Stone";
            stoneFound = "PARADOX";

        } else if (randomNo == 3) {
            explorer.takeDamage(1);
            eventDescription = explorer.getName() + " triggered a trap and lost 1 HP";
            hpChange = -1;

        } else if (randomNo == 4) {
            explorer.takeDamage(1);
            eventDescription = explorer.getName() + " was attacked by a shadow creature and lost 1 HP";
            hpChange = -1;

        } else if (randomNo == 5) {
            explorer.takeDamage(1);
            eventDescription = explorer.getName() + " fell into a pit and lost 1 HP";
            hpChange = -1;

        } else if (randomNo == 6) {
            explorer.takeDamage(1);
            eventDescription = explorer.getName() + " was hit by falling debris and lost 1 HP";
            hpChange = -1;

        } else if (randomNo == 7) {
            explorer.heal(1);
            eventDescription = explorer.getName() + " found a healing herb and gained 1 HP";
            hpChange = 1;

        } else if (randomNo == 8) {
            explorer.addTreasure(10);
            eventDescription = explorer.getName() + " discovered ancient treasure worth 10 points";
            treasureChange = 10;

        } else if (randomNo == 9) {
            explorer.heal(1);
            eventDescription = explorer.getName() + " drank from a magical fountain and gained 1 HP";
            hpChange = 1;

        } else if (randomNo == 10) {
            explorer.addTreasure(5);
            eventDescription = explorer.getName() + " found gold coins worth 5 points";
            treasureChange = 5;

        } else if (randomNo == 11) {
            eventDescription = "The magical clock reversed! Turn order is now reversed";
            turnOrderReversed = true;
        }

        return new EventResult(eventDescription, hpChange, treasureChange, stoneFound, turnOrderReversed);
    }

    public void restoreToRoom(int targetRoomNumber) {
        if (targetRoomNumber <= 0) {
            return;
        }

        Node<Integer> temp = null;
        Node<Integer> tempLast = null;

        Node<Integer> current = first;

        while (current != null && current.data <= targetRoomNumber) {
            Node<Integer> newNode = new Node<>(current.data);

            if (temp == null) {
                temp = newNode;
                tempLast = newNode;
            } else {
                tempLast.next = newNode;
                newNode.prev = tempLast;
                tempLast = newNode;
            }

            current = current.next;
        }

        first = temp;
        last = tempLast;
        currentRoomNumber = targetRoomNumber + 1;

        System.out.println("Rooms restored to Room " + targetRoomNumber);
    }

    public int getRoomAtTurn(ActionLog actionLog, int targetTurn) {
        Node<Action> current = actionLog.getFirst();
        int lastRoom = 1;

        while (current != null && current.data.getTurnNumber() <= targetTurn) {
            if (current.data.getActionType().equals("MOVE")) {
                lastRoom = current.data.getRoomNumber();
            }
            current = current.next;
        }

        return lastRoom;
    }

    public int getCurrentRoomNumber() {
        return currentRoomNumber - 1;
    }

    public Node<Integer> search(int roomNumber) {
        Node<Integer> tmp = first;
        while (tmp != null) {
            if (tmp.data == roomNumber) {
                return tmp;
            }
            tmp = tmp.next;
        }
        return null;
    }

    public static class EventResult {
        public String description;
        public int hpChange;
        public int treasureChange;
        public String stoneFound;
        public boolean turnOrderReversed;

        public EventResult(String desc, int hp, int treasure, String stone, boolean reversed) {
            this.description = desc;
            this.hpChange = hp;
            this.treasureChange = treasure;
            this.stoneFound = stone;
            this.turnOrderReversed = reversed;
        }
    }
}