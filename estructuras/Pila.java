package estructuras;

public class Pila<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> top;
    private int size;

    public Pila() {
        this.top = null;
        this.size = 0;
    }

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) return null;
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        return (top != null) ? top.data : null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void mostrar() {
        if (isEmpty()) {
            System.out.println("La pila está vacía.");
            return;
        }
        Node<T> aux = top;
        while (aux != null) {
            System.out.println(aux.data.toString());
            aux = aux.next;
        }
    }
}