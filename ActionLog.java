package MP1;

public class ActionLog {
    private Node<Action> first;
    private Node<Action> last;
    private int size;

    public ActionLog() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void addAction(Action action) {
        Node<Action> newNode = new Node<>(action);

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

    public void displayAllActions() {
        System.out.println("\n ACTION HISTORY ");
        Node<Action> current = first;
        while (current != null) {
            System.out.println(current.data.toString());
            current = current.next;
        }
        System.out.println(" ");
    }

    public Node<Action> getActionByTurn(int turnNumber) {
        Node<Action> current = first;
        while (current != null) {
            if (current.data.getTurnNumber() == turnNumber) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void restoreToTurn(int targetTurn) {
        if (first == null) {
            return;
        }

        Node<Action> temp = null;
        Node<Action> tempLast = null;

        Node<Action> current = first;

        while (current != null && current.data.getTurnNumber() <= targetTurn) {
            Node<Action> newNode = new Node<>(current.data);

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

        size = 0;
        Node<Action> counter = first;
        while (counter != null) {
            size++;
            counter = counter.next;
        }

        System.out.println("ActionLog restored to Turn " + targetTurn);
    }

    public Node<Action> getFirst() {
        return first;
    }

    public Node<Action> getLast() {
        return last;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return first == null;
    }
}