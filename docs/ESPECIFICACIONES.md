# 🔧 Especificaciones Técnicas - QuickLibrary

## Atributos de Clases

### Clase: Libro

```
ATRIBUTO              TIPO        DESCRIPCIÓN
─────────────────────────────────────────────────────
codigo               String      Identificador único (ej: "101")
titulo               String      Nombre del libro
autor                String      Autor principal
categoria            String      Área o especialidad
anio                 int         Año de publicación
estado               String      "Disponible" o "Prestado"
```

**Métodos mínimos:**
- `Libro(codigo, titulo, autor, categoria, anio, estado)`
- `getCodigo()`, `getTitulo()`, `getAutor()`, `getCategoria()`, `getAnio()`, `getEstado()`
- `setEstado(String nuevoEstado)`
- `equals(Object)` - Comparar por código
- `compareTo(Libro)` - Implementar Comparable para árbol
- `toString()` - Mostrar información

---

### Clase: SolicitudPréstamo

```
ATRIBUTO              TIPO        DESCRIPCIÓN
─────────────────────────────────────────────────────
codigoEstudiante     String      Identificador del estudiante
nombreEstudiante     String      Nombre completo
codigoLibro          String      Código del libro solicitado
fechaSolicitud       String      Fecha del registro (ej: "2026-06-23")
```

**Métodos mínimos:**
- `SolicitudPréstamo(codigoEstudiante, nombreEstudiante, codigoLibro, fechaSolicitud)`
- Getters para todos los atributos
- `toString()` - Mostrar información completa

---

## Estructura de Datos: Cola<T>

### Clase: Nodo<T>

```java
// Nodo genérico para la cola
public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;
    
    // Constructor, getters y setters
}
```

### Clase: Cola<T>

```java
public class Cola<T> {
    private Nodo<T> inicio;
    private Nodo<T> fin;
    private int tamaño;
    
    // Métodos OBLIGATORIOS
    public void enqueue(T dato)           // O(1) - Agregar
    public T dequeue()                    // O(1) - Quitar
    public T peek()                       // O(1) - Ver primero
    public boolean isEmpty()              // O(1) - ¿Vacía?
    public int size()                     // O(1) - Tamaño
    public void mostrar()                 // O(n) - Listar
}
```

**Invariante FIFO:** Primero en entrar = Primero en salir

---

## Estructura de Datos: ArbolBinarioBúsqueda<T>

### Clase: NodoArbol<T>

```java
// Nodo para el árbol binario
public class NodoArbol<T> {
    private T dato;
    private NodoArbol<T> izquierda;
    private NodoArbol<T> derecha;
    
    // Constructor, getters y setters
}
```

### Clase: ArbolBinarioBúsqueda<T>

```java
public class ArbolBinarioBúsqueda<T extends Comparable<T>> {
    private NodoArbol<T> raiz;
    
    // Métodos OBLIGATORIOS
    public void insertar(T dato)          // O(log n) promedio
    public T buscar(T dato)               // O(log n) promedio
    public boolean eliminar(T dato)       // O(log n) promedio
    public void recorridoInorden()        // O(n)
    public int contar()                   // O(n)
    public boolean estaVacio()            // O(1)
}
```

**Invariante BST:** Izquierda < Nodo < Derecha

---

## Clase: GestorBiblioteca

```java
public class GestorBiblioteca {
    private ArbolBinarioBúsqueda<Libro> libros;  // Búsqueda rápida
    private Cola<SolicitudPréstamo> solicitudes;  // FIFO
    
    // GESTIÓN DE LIBROS (RF01)
    public void registrarLibro(Libro libro)
    public void mostrarLibros()
    public void modificarLibro(String codigo, Libro nuevoLibro)
    public void eliminarLibro(String codigo)
    public void mostrarLibrosDisponibles()
    public void mostrarLibrosPrestados()
    
    // BÚSQUEDA DE LIBROS (RF02)
    public Libro buscarPorCodigo(String codigo)      // Usa árbol
    public void buscarPorTitulo(String titulo)       // Recorrido + filtro
    public void buscarPorAutor(String autor)         // Recorrido + filtro
    public void buscarPorCategoria(String categoria) // Recorrido + filtro
    
    // SOLICITUDES (RF03)
    public void registrarSolicitud(SolicitudPréstamo solicitud)
    public void mostrarSolicitudes()
    public SolicitudPréstamo consultarSiguiente()
    public SolicitudPréstamo atenderSiguiente()
    public void eliminarSolicitud()
    
    // PRÉSTAMO (RF04)
    public boolean realizarPrestamo(String codigoLibro)
    
    // DEVOLUCIÓN (RF05)
    public boolean registrarDevolucion(String codigoLibro)
    
    // REPORTES (RF06)
    public void mostrarReporte()
}
```

---

## Clase: Menu

```java
public class Menu {
    private GestorBiblioteca gestor;
    
    // Métodos
    public void mostrarMenuPrincipal()
    public void procesarOpcion(int opcion)
    public void ejecutar()
}
```

**Opciones del menú:**
1. Registrar libro
2. Mostrar libros
3. Buscar libro por código
4. Buscar libros por categoría
5. Modificar libro
6. Eliminar libro
7. Registrar solicitud de préstamo
8. Mostrar cola de solicitudes
9. Atender siguiente solicitud
10. Registrar devolución
11. Mostrar reporte
12. Salir

---

## Clase: Main

```java
public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.ejecutar();
    }
}
```

---

## Validaciones Requeridas

- ✅ Código duplicado en libros
- ✅ Campos vacíos o nulos
- ✅ Libro inexistente en búsquedas
- ✅ Préstamo de libro no disponible
- ✅ Devolución de libro no prestado
- ✅ Cola vacía al intentar atender
- ✅ Código inválido (formato numérico)
- ✅ Año de publicación válido (razonable)

---

## Ejemplo de Flujo: Atender Solicitud

```
1. Usuario selecciona opción 9
2. Sistema llama atenderSiguiente()
3. Obtiene siguiente solicitud con peek()
4. Busca libro por código en árbol
5. Verifica estado = "Disponible"
6. Cambia estado a "Prestado"
7. Elimina solicitud con dequeue()
8. Muestra confirmación
9. Vuelve al menú
```

---

## Datos Iniciales (Archivo CSV)

```csv
codigo,titulo,autor,categoria,anio,estado
101,Programación en Java,Herbert Schildt,Programación,2022,Disponible
102,Estructuras de Datos,Mark Allen Weiss,Computación,2021,Disponible
103,Introducción a los Algoritmos,Thomas Cormen,Algoritmos,2022,Prestado
104,Java Concurrency,Doug Lea,Programación,2023,Disponible
105,Design Patterns,Gang of Four,Diseño,2020,Disponible
...
```

**Ubicación:** `datos/libros.csv`  
**Cantidad mínima:** 30 libros

---

## Pruebas Mínimas (10 casos)

| # | Caso | Entrada | Resultado Esperado | Estructura |
|---|------|---------|-------------------|-----------|
| 1 | Registrar libro | Código: 101 | ✅ Libro registrado | Árbol |
| 2 | Código duplicado | Código: 101 | ❌ Error: código existe | Árbol |
| 3 | Buscar existente | Buscar 101 | ✅ Libro encontrado | Árbol |
| 4 | Buscar inexistente | Buscar 999 | ❌ Libro no existe | Árbol |
| 5 | 3 solicitudes | Registrar 3 | ✅ 3 en cola | Cola |
| 6 | Atender FIFO | Atender x3 | ✅ Orden correcto | Cola |
| 7 | Préstamo no disponible | Libro prestado | ❌ No disponible | Árbol |
| 8 | Devolución | Devolver 101 | ✅ Estado disponible | Árbol |
| 9 | Eliminar libro | Eliminar 101 | ✅ Libro removido | Árbol |
| 10 | Reporte | Mostrar reporte | ✅ Cantidades correctas | Árbol/Cola |

---

**Última actualización:** 2026-06-23
