package quicklibrary.structures.tree;

public class AVLNode<T> {
    public T dato;
    public AVLNode<T> izquierda;
    public AVLNode<T> derecha;
    public int altura;

    public AVLNode(T dato) {
        this.dato = dato;
        this.izquierda = null;
        this.derecha = null;
        this.altura = 1;
    }
}