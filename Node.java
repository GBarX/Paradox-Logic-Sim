package MP1;

public class Node<T> {
    public T data;
    public Node next;
    public Node<T> prev;

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Node with data: " + data;
        return s;
    }
}
