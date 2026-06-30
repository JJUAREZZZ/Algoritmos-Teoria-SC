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
    
    public boolean remove(T dato) {
        if (dato == null || isEmpty()) {
            return false;
        }
        if (primero.dato.equals(dato)) {
            primero = primero.siguiente;
            cantidad--;
            if (primero == null) {
                ultimo = null;
            }
            return true;
        }
        ListNode<T> anterior = primero;
        ListNode<T> actual = primero.siguiente;
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                anterior.siguiente = actual.siguiente;
                if (actual == ultimo) {
                    ultimo = anterior;
                }
                cantidad--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    public void clear() {
        primero = null;
        ultimo = null;
        cantidad = 0;
    }

    public void insertOrdered(T dato) {
        if (dato == null) {
            return;
        }
        ListNode<T> nuevoNodo = new ListNode<T>(dato);
        if (isEmpty()) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
            cantidad++;
            return;
        }
        if (((Comparable<T>) dato).compareTo(primero.dato) <= 0) {
            nuevoNodo.siguiente = primero;
            primero = nuevoNodo;
            cantidad++;
            return;
        }
        ListNode<T> actual = primero;
        while (actual.siguiente != null && ((Comparable<T>) dato).compareTo(actual.siguiente.dato) > 0) {
            actual = actual.siguiente;
        }
        nuevoNodo.siguiente = actual.siguiente;
        actual.siguiente = nuevoNodo;
        if (nuevoNodo.siguiente == null) {
            ultimo = nuevoNodo;
        }
        cantidad++;
    }

    public String mostrarComoTexto() {
        StringBuilder sb = new StringBuilder();
        ListNode<T> actual = primero;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) {
                sb.append("\n");
            }
            actual = actual.siguiente;
        }
        return sb.toString();
    }
}
