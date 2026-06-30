package quicklibrary.structures.queue;

public class QueueNode<T> {
    public T value;
    public QueueNode<T> next;

    public QueueNode(T value) {
        this.value = value;
        this.next = null;
    }
}
