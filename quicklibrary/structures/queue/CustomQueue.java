package quicklibrary.structures.queue;

public class CustomQueue<T> {
    private QueueNode<T> front;
    private QueueNode<T> rear;
    private int size;

    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(T value) {
        QueueNode<T> newNode = new QueueNode<T>(value);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T value = front.value;
        front = front.next;
        size--;
        if (front == null) {
            rear = null;
        }
        return value;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
