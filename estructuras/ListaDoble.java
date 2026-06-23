package estructuras;

public class ListaDoble<T> {
    private static class NodeDoble<T> {
        T data;
        NodeDoble<T> next;
        NodeDoble<T> prev;

        NodeDoble(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private NodeDoble<T> head;
    private NodeDoble<T> tail;
    private int size;

    public ListaDoble() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public void insertFirst(T data) {
        NodeDoble<T> newNode = new NodeDoble<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void insertLast(T data) {
        NodeDoble<T> newNode = new NodeDoble<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) return null;
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        return data;
    }

    public T removeLast() {
        if (isEmpty()) return null;
        T data = tail.data;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;
        return data;
    }

    public void mostrarAdelante() {
        if (isEmpty()) {
            System.out.println("La lista está vacia.");
            return;
        }
        NodeDoble<T> aux = head;
        while (aux != null) {
            System.out.println(aux.data.toString());
            aux = aux.next;
        }
    }

    public void mostrarAtras() {
        if (isEmpty()) {
            System.out.println("La lista está vacia.");
            return;
        }
        NodeDoble<T> aux = tail;
        while (aux != null) {
            System.out.println(aux.data.toString());
            aux = aux.prev;
        }
    }
}