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
    
    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return front.value;
    }

    public int size() {
        return size;
    }

    public void mostrar() {
        if (isEmpty()) {
            System.out.println("La cola está vacía.");
            return;
        }
        QueueNode<T> current = front;
        while (current != null) {
            System.out.println(current.value.toString());
            current = current.next;
        }
    }
}
