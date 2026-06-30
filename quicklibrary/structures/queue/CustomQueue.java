package quicklibrary.structures.queue;

import quicklibrary.structures.list.CustomLinkedList;
//definimos una cola custom con atributos front, rear y size
public class CustomQueue<T extends Comparable<T>>  {
    private QueueNode<T> front;
    private QueueNode<T> rear;
    private int size;
//se inician los atributos front< rear como nulos y el tamaño en 0
    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }
// se define el metodo para poder encolar (agregar elemento al final de la cola)
    public void enqueue(T value) {
       if (value == null)  return;
        QueueNode<T> newNode = new QueueNode<T>(value);
        if (isEmpty()) { 
            front = newNode;// se establece el primer nodo de la cola
        } else {
            rear.next = newNode; //vuelve a enlazar el final del ultimo nodo
        }
        rear = newNode; // actualiza el puntero
        size++;
    }
    // retira y retorna el elemento 
    public T dequeue() {
        if (isEmpty())  return null;
        T value = front.value;
        front = front.next; // mueve el elemento al siguiente
        size--;
        if (front == null) {
            rear = null; // si se logro vaciar el rear tmb es nulo
        }
        return value;
    }
     // verifica si la cola no tiene elementos
    public boolean isEmpty() {
        return front == null;
    }
        // solo es para devolver un elemento al frente sin quitarlo 
    public T peek() {
        if (isEmpty())  return null;
        
        return front.value;
    }
    // cantidad actual de elementos
    public int size() {
        return size;
    }
    public CustomLinkedList<T> toList() {
        CustomLinkedList<T> lista = new CustomLinkedList<T>();
        QueueNode<T> current = front;
        while (current != null) {
            lista.addLast(current.value);
            current = current.next;
        }
        return lista;
    }

    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
    // imprime en consola todo 
    public void mostrar() {
        if (isEmpty()) {
            System.out.println("La cola está vacía.");
            return;
        }
        QueueNode<T> current = front;
        while (current != null) {
            System.out.println(current.value.toString());
            current = current.next; // avanza al siguiente
        }
    }
}
