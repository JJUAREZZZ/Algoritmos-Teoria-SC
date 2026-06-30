package quicklibrary.structures.list;

class ListNode<T> {
    T dato;
    ListNode<T> siguiente;

    ListNode(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
