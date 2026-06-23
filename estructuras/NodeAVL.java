package estructuras;

public class NodeAVL<T> {
    public T value;
    public NodeAVL<T> left;
    public NodeAVL<T> right;
    public int height;

    public NodeAVL(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}