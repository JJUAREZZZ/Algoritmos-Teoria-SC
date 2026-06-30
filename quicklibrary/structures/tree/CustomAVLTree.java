package quicklibrary.structures.tree;

import quicklibrary.exception.BookNotFoundException;
import quicklibrary.exception.DuplicateKeyException;
import quicklibrary.structures.list.CustomLinkedList;

public class CustomAVLTree<T extends Comparable<T>> {
    private AVLNode<T> raiz;
    private int cantidad;

    public CustomAVLTree() {
        this.raiz = null;
        this.cantidad = 0;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public int size() {
        return cantidad;
    }

    public void insert(T dato) throws DuplicateKeyException {
        if (dato == null) {
            return;
        }
        raiz = insertarRecursivo(raiz, dato);
    }

    private AVLNode<T> insertarRecursivo(AVLNode<T> nodo, T dato) throws DuplicateKeyException {
        if (nodo == null) {
            cantidad++;
            return new AVLNode<T>(dato);
        }

        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion < 0) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, dato);
        } else if (comparacion > 0) {
            nodo.derecha = insertarRecursivo(nodo.derecha, dato);
        } else {
            throw new DuplicateKeyException("Ya existe un registro con la misma clave.");
        }

        actualizarAltura(nodo);
        return balancear(nodo);
    }

    public T search(T dato) throws BookNotFoundException {
        AVLNode<T> encontrado = buscarNodo(raiz, dato);
        if (encontrado == null) {
            throw new BookNotFoundException("No se encontro el elemento solicitado.");
        }
        return encontrado.dato;
    }

    public T searchOrNull(T dato) {
        AVLNode<T> encontrado = buscarNodo(raiz, dato);
        if (encontrado == null) {
            return null;
        }
        return encontrado.dato;
    }

    private AVLNode<T> buscarNodo(AVLNode<T> nodo, T dato) {
        if (nodo == null || dato == null) {
            return null;
        }
        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion == 0) {
            return nodo;
        }
        if (comparacion < 0) {
            return buscarNodo(nodo.izquierda, dato);
        }
        return buscarNodo(nodo.derecha, dato);
    }

    public void delete(T dato) throws BookNotFoundException {
        if (dato == null) {
            return;
        }
        raiz = eliminarRecursivo(raiz, dato);
    }

    private AVLNode<T> eliminarRecursivo(AVLNode<T> nodo, T dato) throws BookNotFoundException {
        if (nodo == null) {
            throw new BookNotFoundException("No se encontro el elemento para eliminar.");
        }

        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion < 0) {
            nodo.izquierda = eliminarRecursivo(nodo.izquierda, dato);
        } else if (comparacion > 0) {
            nodo.derecha = eliminarRecursivo(nodo.derecha, dato);
        } else {
            cantidad--;
            if (nodo.izquierda == null || nodo.derecha == null) {
                AVLNode<T> hijo = null;
                if (nodo.izquierda != null) {
                    hijo = nodo.izquierda;
                } else if (nodo.derecha != null) {
                    hijo = nodo.derecha;
                }
                return hijo;
            } else {
                AVLNode<T> sucesor = encontrarMenor(nodo.derecha);
                nodo.dato = sucesor.dato;
                nodo.derecha = eliminarSucesor(nodo.derecha, sucesor.dato);
            }
        }
        actualizarAltura(nodo);
        return balancear(nodo);
    }

    private AVLNode<T> eliminarSucesor(AVLNode<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }
        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion < 0) {
            nodo.izquierda = eliminarSucesor(nodo.izquierda, dato);
        } else if (comparacion > 0) {
            nodo.derecha = eliminarSucesor(nodo.derecha, dato);
        } else {
            if (nodo.izquierda == null || nodo.derecha == null) {
                if (nodo.izquierda != null) {
                    return nodo.izquierda;
                }
                return nodo.derecha;
            } else {
                AVLNode<T> sucesor = encontrarMenor(nodo.derecha);
                nodo.dato = sucesor.dato;
                nodo.derecha = eliminarSucesor(nodo.derecha, sucesor.dato);
            }
        }
        actualizarAltura(nodo);
        return balancear(nodo);
    }

    private AVLNode<T> encontrarMenor(AVLNode<T> nodo) {
        AVLNode<T> actual = nodo;
        while (actual.izquierda != null) {
            actual = actual.izquierda;
        }
        return actual;
    }

    private int altura(AVLNode<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    private int maximo(int primero, int segundo) {
        if (primero > segundo) {
            return primero;
        }
        return segundo;
    }

    private void actualizarAltura(AVLNode<T> nodo) {
        if (nodo != null) {
            nodo.altura = maximo(altura(nodo.izquierda), altura(nodo.derecha)) + 1;
        }
    }

    private int obtenerBalance(AVLNode<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierda) - altura(nodo.derecha);
    }

    private AVLNode<T> balancear(AVLNode<T> nodo) {
        int balance = obtenerBalance(nodo);

        if (balance > 1) {
            if (obtenerBalance(nodo.izquierda) < 0) {
                nodo.izquierda = rotarIzquierda(nodo.izquierda);
            }
            return rotarDerecha(nodo);
        }

        if (balance < -1) {
            if (obtenerBalance(nodo.derecha) > 0) {
                nodo.derecha = rotarDerecha(nodo.derecha);
            }
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private AVLNode<T> rotarDerecha(AVLNode<T> y) {
        AVLNode<T> x = y.izquierda;
        AVLNode<T> subArbol = x.derecha;

        x.derecha = y;
        y.izquierda = subArbol;

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    private AVLNode<T> rotarIzquierda(AVLNode<T> x) {
        AVLNode<T> y = x.derecha;
        AVLNode<T> subArbol = y.izquierda;

        y.izquierda = x;
        x.derecha = subArbol;

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    public CustomLinkedList<T> inOrder() {
        CustomLinkedList<T> lista = new CustomLinkedList<T>();
        inOrderRecursivo(raiz, lista);
        return lista;
    }

    private void inOrderRecursivo(AVLNode<T> nodo, CustomLinkedList<T> lista) {
        if (nodo == null) {
            return;
        }
        inOrderRecursivo(nodo.izquierda, lista);
        lista.addLast(nodo.dato);
        inOrderRecursivo(nodo.derecha, lista);
    }

    public String drawTree() {
        if (raiz == null) {
            return "El arbol esta vacio.";
        }
        StringBuilder sb = new StringBuilder();
        construirTexto(raiz, "", true, sb);
        return sb.toString();
    }

    private void construirTexto(AVLNode<T> nodo, String prefijo, boolean esUltimo, StringBuilder sb) {
        if (nodo.derecha != null) {
            construirTexto(nodo.derecha, prefijo + (esUltimo ? "│   " : "    "), false, sb);
        }
        sb.append(prefijo).append(esUltimo ? "└── " : "┌── ").append(nodo.dato).append("\n");
        if (nodo.izquierda != null) {
            construirTexto(nodo.izquierda, prefijo + (esUltimo ? "    " : "│   "), true, sb);
        }
    }

    public AVLNode<T> getRaiz() {
        return this.raiz;
    }
}