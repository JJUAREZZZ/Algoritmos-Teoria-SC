package quicklibrary.structures.stack;

import quicklibrary.structures.list.CustomLinkedList;

public class CustomStack<T extends Comparable<T>> {
    private StackNode<T> cima;
    private int cantidad;

    public CustomStack() {
        this.cima = null;
        this.cantidad = 0;
    }

    public void push(T dato) {
        if (dato == null) {
            return;
        }
        StackNode<T> nuevoNodo = new StackNode<T>(dato);
        nuevoNodo.siguiente = cima;
        cima = nuevoNodo;
        cantidad++;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T datoRetirado = cima.dato;
        cima = cima.siguiente;
        cantidad--;
        return datoRetirado;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return cima.dato;
    }

    public boolean isEmpty() {
        return cima == null;
    }

    public int size() {
        return cantidad;
    }

    public CustomLinkedList<T> toList() {
        CustomLinkedList<T> lista = new CustomLinkedList<T>();
        StackNode<T> actual = cima;
        while (actual != null) {
            lista.addLast(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }

    public void clear() {
        cima = null;
        cantidad = 0;
    }

    public void mostrar() {
        if (isEmpty()) {
            System.out.println("La pila esta vacia.");
            return;
        }
        StackNode<T> actual = cima;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }
}
