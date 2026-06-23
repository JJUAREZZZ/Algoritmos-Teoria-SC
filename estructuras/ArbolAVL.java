package estructuras;

public class ArbolAVL<T extends Comparable<T>> {
    private NodeAVL<T> raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    private int height(NodeAVL<T> node) {
        return (node == null) ? 0 : node.height;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private int getBalance(NodeAVL<T> node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private NodeAVL<T> rotarDerecha(NodeAVL<T> y) {
        NodeAVL<T> x = y.left;
        NodeAVL<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private NodeAVL<T> rotarIzquierda(NodeAVL<T> x) {
        NodeAVL<T> y = x.right;
        NodeAVL<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insertar(T dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    private NodeAVL<T> insertarRecursivo(NodeAVL<T> node, T dato) {
        if (node == null) {
            return new NodeAVL<>(dato);
        }

        int comparacion = dato.compareTo(node.value);

        if (comparacion < 0) {
            node.left = insertarRecursivo(node.left, dato);
        } else if (comparacion > 0) {
            node.right = insertarRecursivo(node.right, dato);
        } else {
            return node;
        }

        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && dato.compareTo(node.left.value) < 0) {
            return rotarDerecha(node);
        }

        if (balance < -1 && dato.compareTo(node.right.value) > 0) {
            return rotarIzquierda(node);
        }

        if (balance > 1 && dato.compareTo(node.left.value) > 0) {
            node.left = rotarIzquierda(node.left);
            return rotarDerecha(node);
        }

        if (balance < -1 && dato.compareTo(node.right.value) < 0) {
            node.right = rotarDerecha(node.right);
            return rotarIzquierda(node);
        }

        return node;
    }

    public T buscar(T dato) {
        return buscarRecursivo(raiz, dato);
    }

    private T buscarRecursivo(NodeAVL<T> actual, T dato) {
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

    private NodeAVL<T> eliminarRecursivo(NodeAVL<T> root, T dato) {
        if (root == null) {
            return root;
        }

        int comparacion = dato.compareTo(root.value);

        if (comparacion < 0) {
            root.left = eliminarRecursivo(root.left, dato);
        } else if (comparacion > 0) {
            root.right = eliminarRecursivo(root.right, dato);
        } else {
            if ((root.left == null) || (root.right == null)) {
                NodeAVL<T> temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                NodeAVL<T> temp = encontrarMenorNodo(root.right);
                root.value = temp.value;
                root.right = eliminarRecursivo(root.right, temp.value);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rotarDerecha(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotarIzquierda(root.left);
            return rotarDerecha(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return rotarIzquierda(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotarDerecha(root.right);
            return rotarIzquierda(root);
        }

        return root;
    }

    private NodeAVL<T> encontrarMenorNodo(NodeAVL<T> node) {
        NodeAVL<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void recorridoInorden() {
        recorridoInordenRecursivo(raiz);
    }

    private void recorridoInordenRecursivo(NodeAVL<T> actual) {
        if (actual != null) {
            recorridoInordenRecursivo(actual.left);
            System.out.println(actual.value.toString());
            recorridoInordenRecursivo(actual.right);
        }
    }

    public int contarElementos() {
        return contarElementosRecursivo(raiz);
    }

    private int contarElementosRecursivo(NodeAVL<T> actual) {
        if (actual == null) {
            return 0;
        }
        return 1 + contarElementosRecursivo(actual.left) + contarElementosRecursivo(actual.right);
    }
}