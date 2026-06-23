# 📊 Análisis de Complejidad Temporal

## Estructura: Cola<T> (Solicitudes de Préstamo)

### Implementación
- **Tipo:** Cola genérica con nodos enlazados
- **Nodos:** Referencias a inicio y fin
- **Datos:** Almacenan SolicitudPréstamo

### Operaciones

| Operación | Descripción | Complejidad | Justificación |
|-----------|-------------|------------|----------------|
| `enqueue(T)` | Agregar solicitud | **O(1)** | Inserción al final con referencia directa |
| `dequeue()` | Eliminar solicitud | **O(1)** | Eliminación del inicio con referencia directa |
| `peek()` | Consultar siguiente | **O(1)** | Acceso directo al primer nodo |
| `isEmpty()` | Verificar vacío | **O(1)** | Comparación de referencias |
| `size()` | Obtener tamaño | **O(1)** o **O(n)** | Depende si se mantiene contador |
| `mostrar()` | Listar solicitudes | **O(n)** | Debe recorrer todos los nodos |

### Ejemplo de Complejidad
```
Registrar 100 solicitudes: 100 × O(1) = O(100) = O(n)
Atender 50 solicitudes: 50 × O(1) = O(50) = O(n)
Consultar todas: O(n)
```

---

## Estructura: ArbolBinarioBúsqueda<T> (Libros)

### Implementación
- **Tipo:** Árbol genérico balanceado
- **Nodos:** Referencia a raíz, izquierda y derecha
- **Datos:** Almacenan Libro (comparable por código)
- **Invariante:** Izquierda < nodo < derecha

### Operaciones

| Operación | Descripción | Mejor Caso | Caso Promedio | Peor Caso |
|-----------|-------------|----------|-------------|-----------|
| `insertar(T)` | Agregar libro | O(log n) | **O(log n)** | O(n) |
| `buscar(T)` | Encontrar libro | O(log n) | **O(log n)** | O(n) |
| `eliminar(T)` | Quitar libro | O(log n) | **O(log n)** | O(n) |
| `recorridoInorden()` | Listar ordenado | O(n) | **O(n)** | O(n) |
| `contar()` | Número de libros | O(n) | **O(n)** | O(n) |
| `estaVacio()` | Verificar vacío | O(1) | **O(1)** | O(1) |

### Análisis Detallado

#### Búsqueda por Código (Árbol Balanceado)
```
Árbol con 30 libros (balanceado):
Altura = log₂(30) ≈ 5 niveles
Búsqueda = O(5) = O(log n)

Árbol degenerado (peor caso - lista enlazada):
Altura = 30 niveles
Búsqueda = O(30) = O(n)
```

#### Inserción
```
Mejor caso: Árbol balanceado, insertar en hoja
  - Búsqueda: O(log n)
  - Inserción: O(1)
  - Total: O(log n)

Peor caso: Árbol degenerado (lista enlazada)
  - Búsqueda: O(n)
  - Inserción: O(1)
  - Total: O(n)
```

#### Recorrido Inorden (Listar todos)
```
Visitar cada nodo exactamente una vez
Total de nodos = n
Complejidad = O(n) siempre
```

---

## Comparación: Otras Estructuras vs. Nuestras Implementaciones

### Búsqueda de Libro por Código

| Estructura | Complejidad | Ventajas | Desventajas |
|-----------|-----------|----------|------------|
| **Árbol B.B.** ✓ | O(log n) | Búsqueda rápida, orden natural | Peor caso O(n) sin balanceo |
| Arreglo | O(n) | Fácil acceso índice | Búsqueda lenta |
| Lista enlazada | O(n) | Inserción/eliminación flexible | Búsqueda secuencial |
| Tabla Hash | O(1) promedio | Búsqueda muy rápida | Sin orden, colisiones |

**Conclusión:** Árbol B.B. es óptimo para búsqueda + mantener orden

### Gestión de Solicitudes de Préstamo

| Estructura | Complejidad | Ventajas | Desventajas |
|-----------|-----------|----------|------------|
| **Cola (FIFO)** ✓ | O(1) | Respeta orden, rápida | No permite búsqueda |
| Pila (LIFO) | O(1) | Rápida | Orden incorrecto |
| Arreglo | O(1) | Flexible | Compleja de manejar |
| Lista con búsqueda | O(n) | Flexible | Lenta al atender |

**Conclusión:** Cola es la estructura correcta (FIFO = orden de llegada)

---

## Peor Caso: Árbol Degenerado

### ¿Qué ocurre?

Si insertamos libros **en orden** (1, 2, 3, 4...), el árbol se convierte en una lista:

```
       101
        |
       102
        |
       103
        |
       104
```

En este caso:
- Búsqueda del 104: recorrer 4 nodos = **O(n)**
- Inserción: recorrer 4 + 1 = **O(n)**
- Recorrido: siempre **O(n)**

### Soluciones (Opcionales)
- **Árbol AVL:** Balanceo automático, garantiza O(log n)
- **Árbol Rojo-Negro:** Balanceo más relajado
- **Árbol 2-3:** Balanceo automático

---

## Análisis de Operaciones Completas (Ejemplo)

### Atender una Solicitud de Préstamo

```
1. Consultar siguiente solicitud (Cola):        O(1)
2. Buscar libro por código (Árbol):             O(log n) promedio
3. Verificar disponibilidad (Comparación):      O(1)
4. Cambiar estado (Modificación):               O(1)
5. Eliminar de cola (Cola):                     O(1)

Total: O(1) + O(log n) + O(1) + O(1) + O(1) = O(log n)
```

### Buscar Libro por Categoría (Búsqueda Lineal)

```
1. Recorrer todo el árbol (inorden):            O(n)
2. Comparar categoría de cada libro:            O(n) iteraciones × O(1) = O(n)

Total: O(n)
```

---

## Recomendaciones

| Tarea | Estructura | Complejidad | Recomendación |
|-------|-----------|-----------|----------------|
| Buscar por código | Árbol | O(log n) | ✅ Usar árbol |
| Gestionar solicitudes | Cola | O(1) | ✅ Usar cola FIFO |
| Buscar por atributos | Recorrido + filtro | O(n) | ✅ Aceptable para 30 libros |
| Eliminar libro | Árbol | O(log n) | ✅ Eficiente |

---

**Nota:** Para conjuntos pequeños (30 libros), O(n) es aceptable. Para millones, sería necesario índices adicionales.

Última actualización: 2026-06-23
