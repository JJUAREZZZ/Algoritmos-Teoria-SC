package quicklibrary.structures.list;

public class CustomLinkedList<T extends Comparable<T>> {
    private ListNode<T> primero;
    private ListNode<T> ultimo;
    private int cantidad;

    public CustomLinkedList() {
        this.primero = null;
        this.ultimo = null;
        this.cantidad = 0;
    }

    public boolean isEmpty() {
        return cantidad == 0;
    }

    public int size() {
        return cantidad;
    }

    public void addFirst(T dato) {
        if (dato == null) {
            return;
        }
        ListNode<T> nuevoNodo = new ListNode<T>(dato);
        if (isEmpty()) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            nuevoNodo.siguiente = primero;
            primero = nuevoNodo;
        }
        cantidad++;
    }

    public void addLast(T dato) {
        if (dato == null) {
            return;
        }
        ListNode<T> nuevoNodo = new ListNode<T>(dato);
        if (isEmpty()) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.siguiente = nuevoNodo;
            ultimo = nuevoNodo;
        }
        cantidad++;
    }

    public T get(int index) {
        if (index < 0 || index >= cantidad) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + index);
        }
        ListNode<T> actual = primero;
        int contador = 0;
        while (contador < index) {
            actual = actual.siguiente;
            contador++;
        }
        return actual.dato;
    }
