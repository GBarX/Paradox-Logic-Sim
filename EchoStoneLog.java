package MP1;

public class EchoStoneLog {
    private Node<Integer> first;
    private Node<Integer> last;
    private int size;

    public EchoStoneLog() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void addEchoStone(int stoneNumber) {
        Node<Integer> newNode = new Node<>(stoneNumber);

        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    public boolean checkWinCondition() {
        if (size == 0) {
            return false;
        }

        Node<Integer> current = first;
        int expectedNumber = 1;

        while (current != null) {
            if (current.data != expectedNumber) {
                return false;
            }
            expectedNumber++;
            current = current.next;
        }

        return true;
    }

    public void display() {
        System.out.print("Echo Stones Used: ");
        if (first == null) {
            System.out.println("None");
            return;
        }
        Node<Integer> current = first;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }

    public void restoreToCount(int targetCount) {
        if (targetCount <= 0) {
            first = null;
            last = null;
            size = 0;
            return;
        }

        if (targetCount >= size) {
            return;
        }

        Node<Integer> temp = null;
        Node<Integer> tempLast = null;

        Node<Integer> current = first;
        int count = 0;

        while (current != null && count < targetCount) {
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
            count++;
        }

        first = temp;
        last = tempLast;
        size = targetCount;

        System.out.println("EchoStoneLog restored to " + targetCount + " stones");
    }

    public int getCountAtTurn(ActionLog actionLog, int targetTurn) {
        int count = 0;
        Node<Action> current = actionLog.getFirst();

        while (current != null && current.data.getTurnNumber() <= targetTurn) {
            if (current.data.getActionType().equals("USE_ECHO_STONE")) {
                count++;
            }
            current = current.next;
        }

        return count;
    }

    public int getSize() {
        return size;
    }

    public Node<Integer> getFirst() {
        return first;
    }
}