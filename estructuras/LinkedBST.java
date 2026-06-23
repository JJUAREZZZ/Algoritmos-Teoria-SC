package avltree;

import Exceptions.*;
import bstreeInterface.*;
import Queue.*;

public class LinkedBST<E extends Comparable<E>> implements BinarySearchTree<E> {

    protected class Node {
        protected E data; 
        protected Node left;
        protected Node right;

        public Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    protected Node root;

    public LinkedBST() {
        this.root = null; 
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void insert(E d) throws ItemDuplicated {
    	this.root = insertRec(this.root, d);
    }

    private Node insertRec(Node n, E d) throws ItemDuplicated {
        if (n == null) {
            return new Node(d);
        }
        int cmp = d.compareTo(n.data); 
        if (cmp < 0) {
            n.left = insertRec(n.left, d);
        } else if (cmp > 0) {
            n.right = insertRec(n.right, d);
        } else {
            throw new ItemDuplicated("El dato " + d + " ya existe");
        }
        return n;
    }

    @Override
    public E search(E d) throws ItemNotFound {
        return searchRec(root, d);
    }

    private E searchRec(Node n, E d) throws ItemNotFound {
        if (n == null) {
            throw new ItemNotFound("El dato " + d + " no se encuentra en el BST"); 
        }
        int cmp = d.compareTo(n.data); 
        if (cmp < 0) {
            return searchRec(n.left, d); 
        } else if (cmp > 0) {
            return searchRec(n.right, d); 
        } else {
            return n.data; 
        }
    }

    @Override
    public void delete(E d) throws ExceptionIsEmpty, ItemNotFound {
        if (isEmpty()) {
            throw new ExceptionIsEmpty("El árbol está vacío");
        }
        root = deleteRec(root, d);
    }

    private Node deleteRec(Node n, E d) throws ItemNotFound {
        if (n == null) throw new ItemNotFound("Elemento no encontrado");;
        int cmp = d.compareTo(n.data);
        if (cmp < 0) {
            n.left = deleteRec(n.left, d); 
        } else if (cmp > 0) {
            n.right = deleteRec(n.right, d); 
        } else {
            if (n.left == null) return n.right;
            if (n.right == null) return n.left;

            E d2 = findMin(n.right);
            n.data = d2; 
            n.right = deleteRec(n.right, d2);
        }
        return n;
    }

    private E findMin(Node n) {
        while (n.left != null) {
            n = n.left;
        }
        return n.data;
    }

    // ==========================================
    // RECORRIDOS
    // ==========================================
    public String getInOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(this.root, sb);
        return sb.toString().trim();
    }

    private void inOrder(Node node, StringBuilder sb) {
        if (node != null) {
            inOrder(node.left, sb);
            sb.append(node.data).append(" ");
            inOrder(node.right, sb);
        }
    }

    public String getPreOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder(this.root, sb);
        return sb.toString().trim();
    }

    private void preOrder(Node node, StringBuilder sb) {
        if (node != null) {
            sb.append(node.data).append(" ");
            preOrder(node.left, sb);
            preOrder(node.right, sb);
        }
    }

    public String getPostOrder() {
        StringBuilder sb = new StringBuilder();
        postOrder(this.root, sb);
        return sb.toString().trim();
    }

    private void postOrder(Node node, StringBuilder sb) {
        if (node != null) {
            postOrder(node.left, sb);
            postOrder(node.right, sb);
            sb.append(node.data).append(" ");
        }
    }

    // Método height(x) -> Iterativo
    public int height(E x) {
        Node current = root;

        while (current != null) {
            int cmp = x.compareTo(current.data);
            if (cmp == 0) {
                break;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (current == null) {
            return -1;
        }

        int h = -1;
        QueueLink<Node> queue = new QueueLink<>();
        queue.enqueue(current);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            h++;

            for (int i = 0; i < levelSize; i++) {
                Node tempNode = queue.dequeue();

                if (tempNode.left != null)
                    queue.enqueue(tempNode.left);

                if (tempNode.right != null)
                    queue.enqueue(tempNode.right);
            }
        }

        return h;
    }

    // Método amplitude()
    public int amplitude() {
        if (root == null)
            return 0;

        int maxAmplitude = 0;
        int treeHeight = height(root.data);

        for (int i = 0; i <= treeHeight; i++) {
            int currentAmplitude = getNodesAtLevel(root, i);
            if (currentAmplitude > maxAmplitude) {
                maxAmplitude = currentAmplitude;
            }
        }

        return maxAmplitude;
    }

    private int getNodesAtLevel(Node node, int level) {
        if (node == null)
            return 0;

        if (level == 0)
            return 1;

        return getNodesAtLevel(node.left, level - 1)
                + getNodesAtLevel(node.right, level - 1);
    }
    
    // areaBST() - Implementación iterativa
    public int areaBST() {
        if (root == null) {
            return 0;
        }

        int height = -1;
        int leafCount = 0;

        QueueLink<Node> queue = new QueueLink<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            height++;

            for (int i = 0; i < levelSize; i++) {
                Node current = queue.dequeue();

                if (current.left == null && current.right == null) {
                    leafCount++;
                }

                if (current.left != null) {
                    queue.enqueue(current.left);
                }

                if (current.right != null) {
                    queue.enqueue(current.right);
                }
            }
        }

        return leafCount * height;
    }

    @Override
    public String toString() {
        if (root == null)
            return "El árbol está vacío.";

        StringBuilder sb = new StringBuilder();
        buildTreeString(root, "", true, sb);
        return sb.toString();
    }

    // Construcción gráfica del árbol
    private void buildTreeString(Node node, String prefix, boolean isTail, StringBuilder sb) {
        if (node.right != null) {
            buildTreeString(node.right, prefix + (isTail ? "│   " : "    "), false, sb);
        }

        sb.append(prefix)
          .append(isTail ? "└── " : "┌── ")
          .append(node.data.toString())
          .append("\n");

        if (node.left != null) {
            buildTreeString(node.left, prefix + (isTail ? "    " : "│   "), true, sb);
        }
    }

    public void drawBST() {
        System.out.println("Estructura del árbol (Nodos y aristas):");
        System.out.println(this.toString());
    }

    // Hojas: Nodos sin hijos
    public int countLeaves() {
        return countLeavesRec(this.root);
    }

    private int countLeavesRec(Node node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return countLeavesRec(node.left) + countLeavesRec(node.right);
    }

    public E findMin() throws ItemNotFound {
        if (isEmpty()) throw new ItemNotFound("Árbol vacío");
        Node current = this.root;
        while (current.left != null) current = current.left; 
        return current.data;
    }

    public E findMax() throws ItemNotFound {
        if (isEmpty()) throw new ItemNotFound("Árbol vacío");
        Node current = this.root;
        while (current.right != null) current = current.right; 
        return current.data;
    }
    
    // EJERCICIO 4----------------------	
    public void parenthesize() {
        parenthesizeRec(this.root, 0);
    }

    private void parenthesizeRec(Node n, int level) {
        if (n == null) return;

        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        
        System.out.print(n.data);
        if (n.left != null || n.right != null) {
            System.out.println(" ("); 

            parenthesizeRec(n.left, level + 1);
            parenthesizeRec(n.right, level + 1);

            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            System.out.println(")");
        } else {
            System.out.println();
        }
    }

    // ==========================================
    // VERIFICACIÓN DE PROPIEDADES 
    // ==========================================
    public boolean isValidBST() {
        return isValidBSTRec(this.root, null, null);
    }

    private boolean isValidBSTRec(Node node, E min, E max) {
        if (node == null) {
            return true; 
        }
        if (min != null && node.data.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && node.data.compareTo(max) >= 0) {
            return false;
        }

        return isValidBSTRec(node.left, min, node.data) && 
               isValidBSTRec(node.right, node.data, max);
    }        

    // EJERCICIO 5--------------------------
    public String searchRange(E min, E max) {
        return searchRangeRec(this.root, min, max);
    }

    private String searchRangeRec(Node n, E min, E max) {
        if (n == null) {
            return "";
        }
        String resultado = "";
        if (min.compareTo(n.data) < 0) {
            resultado += searchRangeRec(n.left, min, max);
        }
        if (min.compareTo(n.data) <= 0 && max.compareTo(n.data) >= 0) {
            resultado += "\n   " + n.data.toString();
        }
        if (max.compareTo(n.data) > 0) {
            resultado += searchRangeRec(n.right, min, max);
        }

        return resultado;
    }

    public void printDescending() {
        System.out.print("Productos en descendente: ");
        printDescendingRec(this.root);
        System.out.println();
    }

    private void printDescendingRec(Node n) {
        if (n == null) return;
        printDescendingRec(n.right);
        System.out.print(n.data + " ");
        printDescendingRec(n.left);
    }
}





