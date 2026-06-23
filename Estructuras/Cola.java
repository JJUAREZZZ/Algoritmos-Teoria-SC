package estructuras;

public class Cola<T> {
    private Node<T> frente;
    private Node<T> fin;
    private int size;

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.size = 0;
    }

    public void enqueue(T dato) {
        Node<T> newNode = new Node<>(dato);
        if (isEmpty()) {
            frente = newNode;
        } else {
            fin.next = newNode;
        }
        fin = newNode;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null; // O lanzar una excepción personalizada
        }
        T valor = frente.value;
        frente = frente.next;
        size--;
        if (frente == null) {
            fin = null;
        }
        return valor;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return frente.value;
    }

    public boolean isEmpty() {
        return frente == null;
    }

    public int size() {
        return size;
    }

    public void mostrar() {
        if (isEmpty()) {
            System.out.println("La cola está vacía.");
            return;
        }
        Node<T> aux = frente;
        while (aux != null) {
            System.out.println(aux.value.toString());
            aux = aux.next;
        }
    }
}