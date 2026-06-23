package avltree;

import Exceptions.*;

public class AVLTree<E extends Comparable<E>> extends LinkedBST<E> {
	

	// creando clase NodeAVL
    protected class NodeAVL extends Node {
        protected int bf; 

        public NodeAVL(E data) {
            super(data); 
            this.bf = 0;
        }

        @Override
        public String toString() {
            return data + "(bf=" + bf + ")";
        }
    }

    private boolean height; 



    // redefinicion del metodo de insercion
    @Override
    public void insert(E x) throws ItemDuplicated { 
        this.height = false;
        this.root = insert(x, (NodeAVL) this.root); 
    }

    protected Node insert(E x, NodeAVL node) throws ItemDuplicated {
        NodeAVL fat = node;

        if (node == null) {
            this.height = true;
            fat = new NodeAVL(x);
        } else {
            int resC = node.data.compareTo(x);
            if (resC == 0) {
                // Lanzar la misma excepción del padre
                throw new ItemDuplicated(x + " ya se encuentra en el arbol..."); 
            }

            if (resC < 0) {
                // Insercion por la derecha
                // Como fat.right es de tipo Node del padre, lo casteamos a NodeAVL
                fat.right = insert(x, (NodeAVL) fat.right); 
                
                if (this.height) {
                    switch (fat.bf) {
                        case -1:
                            fat.bf = 0;
                            this.height = false;
                            break;
                        case 0:
                            fat.bf = 1; this.height = true; break;
                        case 1:
                            fat = balanceToLeft(fat); this.height = false; break;
                    }
                }
            } else {
                // Inserción por la IZQUIERDA
                fat.left = insert(x, (NodeAVL) fat.left);
                
                if (this.height) {
                    switch (fat.bf) {
                        case 1:
                            fat.bf = 0; this.height = false; break;
                        case 0:
                            fat.bf = -1; this.height = true; break;
                        case -1:
                            fat = balanceToRight(fat);this.height = false; break;
                    }
                }
            }
        }
        return fat;
    }
    
    
	
    // implementando el balance a la izquierda
    private NodeAVL balanceToLeft(NodeAVL node) {
        // obtencion del hijo derecho para analisis
        NodeAVL hijo = (NodeAVL) node.right;
        switch (hijo.bf) {
            case 1:
                // caso de rotacion simple a la izquierda
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSL(node);
                break;
            case 0: // Exclusivo de eliminación
                // caso especial de eliminacion con hijo equilibrado
                node.bf = 1;
                hijo.bf = -1;
                node = rotateSL(node);
                break;
            case -1:
                // caso de rotacion doble izquierda derecha
                NodeAVL nieto = (NodeAVL) hijo.left;
                switch (nieto.bf) {
                    case 1: node.bf = -1; hijo.bf = 0; break;
                    case 0: node.bf = 0; hijo.bf = 0; break;
                    case -1: node.bf = 0; hijo.bf = 1; break;
                }
                nieto.bf = 0;
                node.right = rotateSR(hijo);
                node = rotateSL(node);
                break;
        }
        return node;
    }

    // implementando el balance a la derecha
    private NodeAVL balanceToRight(NodeAVL node) {
        // obtencion del hijo izquierdo para analisis
        NodeAVL hijo = (NodeAVL) node.left;
        switch (hijo.bf) {
            case -1:
                // caso de rotacion simple a la derecha
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSR(node);
                break;
            case 0: // Exclusivo de eliminación
                // caso especial de eliminacion con hijo equilibrado
                node.bf = -1;
                hijo.bf = 1;
                node = rotateSR(node);
                break;
            case 1:
                // caso de rotacion doble derecha izquierda
                NodeAVL nieto = (NodeAVL) hijo.right;
                switch (nieto.bf) {
                    case -1: node.bf = 1; hijo.bf = 0; break;
                    case 0: node.bf = 0; hijo.bf = 0; break;
                    case 1: node.bf = 0; hijo.bf = -1; break;
                }
                nieto.bf = 0;
                node.left = rotateSL(hijo);
                node = rotateSR(node);
                break;
        }
        return node;
    }

    
    
    
    // Rotación Simple Izquierda RSL
    private NodeAVL rotateSL(NodeAVL node) {
    	NodeAVL p = (NodeAVL) node.right;
    	node.right = p.left;
    	p.left = node;
    	node = p;
    	return node;
    }

    // Rotación Simple Derecha RSR
    private NodeAVL rotateSR(NodeAVL node) {
    	NodeAVL p = (NodeAVL) node.left;
    	node.left = p.right;
    	p.right = node;
    	node = p;
    	return node;
    }	
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void delete(E x) throws ExceptionIsEmpty, ItemNotFound {
        if (isEmpty()) {
            throw new ExceptionIsEmpty("El árbol está vacío");
        }
        this.height = false; // Usamos height para rastrear si el subárbol redujo su altura
        this.root = deleteRec((NodeAVL) this.root, x);
    }

    private NodeAVL deleteRec(NodeAVL node, E d) throws ItemNotFound {
        if (node == null) 
        	throw new ItemNotFound("Elemento no encontrado");

        int cmp = d.compareTo(node.data);
        if (cmp < 0) {
            // Eliminar por la izquierda
            node.left = deleteRec((NodeAVL) node.left, d);
            if (this.height) {
                node = balanceAfterDeleteLeft(node);
            }
        } else if (cmp > 0) {
            // Eliminar por la derecha
            node.right = deleteRec((NodeAVL) node.right, d);
            if (this.height) {
                node = balanceAfterDeleteRight(node);
            }
        } else {
            // Nodo encontrado
            if (node.left == null) {
                this.height = true; // El subárbol reduce su altura
                return (NodeAVL) node.right;
            } else if (node.right == null) {
                this.height = true; // El subárbol reduce su altura
                return (NodeAVL) node.left;
            } else {
                // Caso 3 con dos hijos. Buscamos el sucesor inorden 
                E minData = findMinAVL((NodeAVL) node.right);
                node.data = minData;
                node.right = deleteRec((NodeAVL) node.right, minData);
                if (this.height) {
                    node = balanceAfterDeleteRight(node);
                }
            }
        }
        return node;
    }

    // Auxiliar para encontrar el mínimo 
    private E findMinAVL(NodeAVL node) {
        while (node.left != null) {
            node = (NodeAVL) node.left;
        }
        return node.data;
    }
    
    
    // Actualiza factores si se eliminó un nodo a la izquierda
    private NodeAVL balanceAfterDeleteLeft(NodeAVL node) {
        switch (node.bf) {
            case -1: 
                node.bf = 0; 
                break; // Se balanceó, sigue disminuyendo la altura 
            case 0: 
                node.bf = 1; 
                this.height = false; 
                break; // Se cargó a la derecha, pero la altura total no cambia
            case 1: 
                NodeAVL rightChild = (NodeAVL) node.right;
                int b = rightChild != null ? rightChild.bf : 0;
                node = balanceToLeft(node);
                if (b == 0) this.height = false; // Caso especial de rotación donde la altura no se reduce
                break;
        }
        return node;
    }

    // Actualiza factores si se eliminó un nodo a la derecha
    private NodeAVL balanceAfterDeleteRight(NodeAVL node) {
        switch (node.bf) {
            case 1: 
                node.bf = 0; 
                break; // Se balanceó, sigue disminuyendo la altura 
            case 0: 
                node.bf = -1; 
                this.height = false; 
                break; // Se cargó a la izquierda, pero la altura total no cambia
            case -1: 
                NodeAVL leftChild = (NodeAVL) node.left;
                int b = leftChild != null ? leftChild.bf : 0;
                node = balanceToRight(node);
                if (b == 0) this.height = false;
                break;
        }
        return node;
    }
    
    
    
   
    
    public void recorridoAmplitudRecursivo() {
        if (isEmpty()) {
            System.out.println("El árbol está vacío.");
            return;
        }
        
        // Obtenemos la altura del árbol rutilizamos el metodo heigth del padre
        int h = height(this.root.data); 
        
        for (int i = 0; i <= h; i++) {
            imprimirNivel((NodeAVL) this.root, i);
        }
        System.out.println();
    }

    private void imprimirNivel(NodeAVL nodo, int nivel) {
        if (nodo == null) {
            return;
        }
        if (nivel == 0) {
            System.out.print(nodo.data + " ");
        } else if (nivel > 0) {
            imprimirNivel((NodeAVL) nodo.left, nivel - 1);
            imprimirNivel((NodeAVL) nodo.right, nivel - 1);
        }
    }
}