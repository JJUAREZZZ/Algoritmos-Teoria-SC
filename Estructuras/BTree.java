package estructuras;

public class BTree<T extends Comparable<T>> {
    private NodeArbol<T> raiz;

    public BTree() {
        this.raiz = null;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public void insertar(T dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    private NodeArbol<T> insertarRecursivo(NodeArbol<T> actual, T dato) {
        if (actual == null) {
            return new NodeArbol<>(dato);
        }

        int comparacion = dato.compareTo(actual.value);
        if (comparacion < 0) {
            actual.left = insertarRecursivo(actual.left, dato);
        } else if (comparacion > 0) {
            actual.right = insertarRecursivo(actual.right, dato);
        }
        return actual;
    }

    public T buscar(T dato) {
        return buscarRecursivo(raiz, dato);
    }

    private T buscarRecursivo(NodeArbol<T> actual, T dato) {
        if (actual == null) {
            return null;
        }

        int comparacion = dato.compareTo(actual.value);
        if (comparacion == 0) {
            return actual.value;
        }
        return comparacion < 0
                ? buscarRecursivo(actual.left, dato)
                : buscarRecursivo(actual.right, dato);
    }

    public void eliminar(T dato) {
        raiz = eliminarRecursivo(raiz, dato);
    }

    private NodeArbol<T> eliminarRecursivo(NodeArbol<T> actual, T dato) {
        if (actual == null) {
            return null;
        }

        int comparacion = dato.compareTo(actual.value);

        if (comparacion < 0) {
            actual.left = eliminarRecursivo(actual.left, dato);
        } else if (comparacion > 0) {
            actual.right = eliminarRecursivo(actual.right, dato);
        } else {
            if (actual.left == null) {
                return actual.right;
            } else if (actual.right == null) {
                return actual.left;
            }

            actual.value = encontrarMenorValor(actual.right);
            actual.right = eliminarRecursivo(actual.right, actual.value);
        }
        return actual;
    }

    private T encontrarMenorValor(NodeArbol<T> raizSubarbol) {
        T menorValor = raizSubarbol.value;
        while (raizSubarbol.left != null) {
            menorValor = raizSubarbol.left.value;
            raizSubarbol = raizSubarbol.left;
        }
        return menorValor;
    }

    public void recorridoInorden() {
        recorridoInordenRecursivo(raiz);
    }

    private void recorridoInordenRecursivo(NodeArbol<T> actual) {
        if (actual != null) {
            recorridoInordenRecursivo(actual.left);
            System.out.println(actual.value.toString());
            recorridoInordenRecursivo(actual.right);
        }
    }

    public int contarElementos() {
        return contarElementosRecursivo(raiz);
    }

    private int contarElementosRecursivo(NodeArbol<T> actual) {
        if (actual == null) {
            return 0;
        }
        return 1 + contarElementosRecursivo(actual.left) + contarElementosRecursivo(actual.right);
    }
}