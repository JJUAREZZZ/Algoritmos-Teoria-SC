package estructuras;

public class NodeArbol<T> {
    public T value;
    public NodeArbol<T> left;
    public NodeArbol<T> right;

    public NodeArbol(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}