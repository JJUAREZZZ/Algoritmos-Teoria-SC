package quicklibrary.structures.tree;

public class CustomBSTree<E extends Comparable<E>> {
    protected Node root;

    public class Node {
        public E data;
        public Node left;
        public Node right;

        public Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public CustomBSTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public Node getRaiz() {
        return this.root;
    }

    public int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
    
    public E search(E x) {
        return searchRec(this.root, x);
    }

    private E searchRec(Node node, E x) {
        if (node == null) return null;
        
        int cmp = x.compareTo(node.data);
        if (cmp < 0) {
            return searchRec(node.left, x);
        } else if (cmp > 0) {
            return searchRec(node.right, x);
        } else {
            return node.data; 
        }
    }
}
