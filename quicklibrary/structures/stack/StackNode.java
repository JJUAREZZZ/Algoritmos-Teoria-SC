package quicklibrary.structures.stack;

class StackNode<T> {
    T dato;
    StackNode<T> siguiente;

    StackNode(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
