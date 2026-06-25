## **Estructura de Directorios** 

src/ 

└── com/quicklibrary/ 

- `├` ── main/ 

- │   └── Main.java            // Punto de entrada: Menú y flujo del sistema 

- `├` ── models/ 

- │ `├` ── Libro.java           // Clase base con los atributos (código, título, etc.) 

- │   └── Solicitud.java       // Clase para los datos de la solicitud de préstamo 

- `├` ── structures/ 

- │ `├` ── Node.java            // Nodo genérico para las estructuras 

- │ `├` ── TreeNode.java        // Nodo para el árbol binario 

- │ `├` ── Queue.java           // Implementación de la cola genérica con nodos 

- │   └── BinarySearchTree.java// Implementación del árbol binario de búsqueda 

- `├` ── services/ 

- │ `├` ── LibraryManager.java  // Lógica de negocio (gestión de libros y préstamos) 

- │   └── DataHandler.java     // (Opcional) Lógica para cargar datos desde un CSV └── utils/ 

- └── Validator.java       // Clases para validar entradas de datos 

## **Descripción de los Módulos** 

## **1. models/ (Datos)** 

- Libro.java: Representa el objeto libro. Debe implementar la interfaz Comparable para que el árbol pueda ordenar y buscar los libros mediante su código. 

## **2. structures/ (El motor del proyecto)** 

- Queue.java: Estructura de datos lineal (cola) implementada con nodos enlazados. Debe usar tipos genéricos . 

- BinarySearchTree.java: Estructura no lineal (árbol) implementada para organizar los libros por su código. También debe usar tipos genéricos. 

## **3. services/ (Lógica de Negocio)** 

- LibraryManager.java: Es el centro del sistema. Aquí es donde se conectan las estructuras con las reglas del negocio, como verificar la disponibilidad de un libro antes de prestarlo, cambiar su estado o retirar una solicitud de la cola. 

## **4. main/ (Interfaz)** 

- Main.java: Es la capa de presentación. Se encarga de mostrar el menú al usuario y llamar a los métodos de LibraryManager. Si más adelante decides crear una interfaz gráfica, solo necesitarás modificar esta clase sin alterar la lógica interna. 

## **¿Por qué esta estructura es eficiente?** 

- Escalabilidad: Si necesitas añadir nuevas funciones, como un historial de préstamos, puedes crear una nueva clase en structures/ sin afectar el resto del sistema. 

- Reutilización: Las estructuras en structures/ son independientes; si el día de mañana necesitas usar tu cola en otro proyecto, solo tienes que copiar el archivo. 

- Mantenibilidad: La separación facilita corregir errores: si falla la lógica de préstamos, buscas en LibraryManager; si falla el ordenamiento, buscas en BinarySearchTree. 

