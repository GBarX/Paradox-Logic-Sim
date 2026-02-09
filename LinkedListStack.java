package MP1;

public class LinkedListStack<T> {

    private int N;
    private Node<T> first;

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void push(T t) {
        Node<T> oldfirst = first;
        first = new Node<T>(t);
        first.next = oldfirst;
        N++;
    }

    public void push2(T t) {
        Node<T> newNode = new Node<T>(t);
        newNode.next = first;
        first = newNode;
        N++;
    }

    public Node<T> top() {
        return first;
    }

    public T pop() {
        if (isEmpty()) {
            System.out.println("The stack is already empty");
            return null;
        }
        T return_value = first.data;
        first = first.next;
        N--;
        return return_value;
    }
}