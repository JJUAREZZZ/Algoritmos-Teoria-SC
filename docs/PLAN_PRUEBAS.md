# 🧪 Plan de Pruebas - QuickLibrary

## 10 Pruebas Mínimas Obligatorias

---

### PRUEBA 1️⃣: Registro Correcto de un Libro

**Objetivo:** Verificar que se puede registrar un nuevo libro correctamente

**Datos de Entrada:**
```
Código: 201
Título: Estructuras de Datos Avanzadas
Autor: Donald Knuth
Categoría: Algoritmos
Año: 2023
Estado: Disponible
```

**Pasos:**
1. Ejecutar el programa
2. Seleccionar opción "1. Registrar libro"
3. Ingresar los datos anteriores
4. Confirmar

**Resultado Esperado:**
```
✅ "Libro registrado exitosamente"
El libro 201 aparece en la lista de libros
```

**Estructura Utilizada:** Árbol Binario de Búsqueda

---

### PRUEBA 2️⃣: Intento de Registrar Código Duplicado

**Objetivo:** Validar que el sistema rechaza códigos repetidos

**Precondición:**
- Libro 101 ya existe en el catálogo

**Pasos:**
1. Opción "1. Registrar libro"
2. Ingresar código 101 (existente)
3. Ingresar otros datos

**Resultado Esperado:**
```
❌ "Error: El código 101 ya existe en la biblioteca"
El libro NO se registra
```

**Estructura Utilizada:** Árbol Binario de Búsqueda (búsqueda O(log n))

---

### PRUEBA 3️⃣: Búsqueda de Libro Existente

**Objetivo:** Buscar un libro existente por código

**Precondición:**
- Libro 103 existe en el catálogo

**Pasos:**
1. Opción "3. Buscar libro por código"
2. Ingresar código 103

**Resultado Esperado:**
```
✅ "Libro encontrado:"
[103] Introducción a los Algoritmos - Thomas Cormen (2022) - Prestado
```

**Estructura Utilizada:** Árbol Binario de Búsqueda (búsqueda O(log n))

---

### PRUEBA 4️⃣: Búsqueda de Libro Inexistente

**Objetivo:** Validar mensaje cuando libro no existe

**Pasos:**
1. Opción "3. Buscar libro por código"
2. Ingresar código 999 (no existe)

**Resultado Esperado:**
```
❌ "Libro con código 999 no encontrado"
Regresa al menú
```

**Estructura Utilizada:** Árbol Binario de Búsqueda (búsqueda O(log n))

---

### PRUEBA 5️⃣: Registro de Tres Solicitudes de Préstamo

**Objetivo:** Agregar 3 solicitudes a la cola en orden

**Datos de Entrada:**
```
Solicitud 1: Est. 1001, Carlos Pérez, Libro 101, 2026-06-23
Solicitud 2: Est. 1002, María López, Libro 102, 2026-06-23
Solicitud 3: Est. 1003, Juan García, Libro 105, 2026-06-23
```

**Pasos:**
1. Opción "7. Registrar solicitud de préstamo"
2. Ingresar datos de solicitud 1
3. Repetir para solicitudes 2 y 3
4. Opción "8. Mostrar cola de solicitudes"

**Resultado Esperado:**
```
✅ Cola de Solicitudes (3 pendientes):
   1. Estudiante 1001 - Carlos Pérez - Libro 101
   2. Estudiante 1002 - María López - Libro 102
   3. Estudiante 1003 - Juan García - Libro 105
```

**Estructura Utilizada:** Cola con enqueue() O(1)

---

### PRUEBA 6️⃣: Atención de Solicitudes en Orden (FIFO)

**Objetivo:** Verificar que se atienden en orden de llegada

**Precondición:**
- 3 solicitudes en la cola (de prueba anterior)

**Pasos:**
1. Opción "9. Atender siguiente solicitud"
2. Verificar que se atiende la 1ª (Estudiante 1001)
3. Repetir 2 veces más

**Resultado Esperado:**
```
✅ Atención 1: Estudiante 1001 - Carlos Pérez - Libro 101
✅ Atención 2: Estudiante 1002 - María López - Libro 102
✅ Atención 3: Estudiante 1003 - Juan García - Libro 105
Cola vacía
```

**Estructura Utilizada:** Cola con dequeue() O(1) - FIFO garantizado

---

### PRUEBA 7️⃣: Intento de Prestar Libro No Disponible

**Objetivo:** Validar que no se puede prestar un libro prestado

**Precondición:**
- Libro 103 está en estado "Prestado"

**Pasos:**
1. Registrar solicitud de préstamo para libro 103
2. Opción "9. Atender siguiente solicitud"

**Resultado Esperado:**
```
❌ "Error: Libro 103 no está disponible"
El estado del libro NO cambia
La solicitud NO se elimina de la cola
```

**Validación:** Verifica disponibilidad antes de cambiar estado

---

### PRUEBA 8️⃣: Devolución de un Libro

**Objetivo:** Cambiar estado de "Prestado" a "Disponible"

**Precondición:**
- Libro 103 está prestado

**Pasos:**
1. Opción "10. Registrar devolución"
2. Ingresar código 103

**Resultado Esperado:**
```
✅ "Devolución registrada exitosamente"
Estado anterior: Prestado
Estado actual:   Disponible
```

**Estructura Utilizada:** Árbol Binario para búsqueda rápida

---

### PRUEBA 9️⃣: Eliminación de un Libro

**Objetivo:** Remover un libro del catálogo

**Precondición:**
- Libro 201 existe

**Pasos:**
1. Opción "6. Eliminar libro"
2. Ingresar código 201
3. Opción "3. Buscar libro por código"
4. Buscar 201

**Resultado Esperado:**
```
✅ "Libro 201 eliminado exitosamente"
Posterior búsqueda:
❌ "Libro 201 no encontrado"
```

**Estructura Utilizada:** Árbol Binario - eliminación O(log n)

---

### PRUEBA 🔟: Visualización del Reporte General

**Objetivo:** Ver estadísticas completas del sistema

**Pasos:**
1. Opción "11. Mostrar reporte"

**Resultado Esperado:**
```
═══════════════════════════════════════════
           REPORTE GENERAL
═══════════════════════════════════════════
Total de libros:           29
Libros disponibles:        26
Libros prestados:           3
Solicitudes pendientes:     0
═══════════════════════════════════════════
```

**Notas:**
- Los números varían según pruebas anteriores
- Se obtienen mediante recorrido inorden (O(n))

---

## 📊 Tabla de Validación

| # | Prueba | Requisito | Estructura | Resultado | Evidencia |
|---|--------|-----------|-----------|-----------|-----------|
| 1 | Registrar libro | RF01 | Árbol | ✅ | Captura |
| 2 | Código duplicado | RF01 | Árbol | ✅ | Captura |
| 3 | Buscar existente | RF02 | Árbol | ✅ | Captura |
| 4 | Buscar inexistente | RF02 | Árbol | ✅ | Captura |
| 5 | 3 solicitudes | RF03 | Cola | ✅ | Captura |
| 6 | Atender FIFO | RF03 | Cola | ✅ | Captura |
| 7 | No disponible | RF04 | Árbol | ✅ | Captura |
| 8 | Devolución | RF05 | Árbol | ✅ | Captura |
| 9 | Eliminar libro | RF01 | Árbol | ✅ | Captura |
| 10 | Reporte | RF06 | Árbol/Cola | ✅ | Captura |

---

## 📸 Evidencia de Ejecución

**Documentar para cada prueba:**
1. Captura de pantalla mostrando el resultado
2. Entrada de datos utilizada
3. Resultado obtenido
4. Validación de requisito cumplido

**Guardar en:** `docs/EVIDENCIA_PRUEBAS/`

---

## ⚠️ Casos Adicionales de Validación

### Validación de Datos

```
Código vacío:          ❌ "El código no puede estar vacío"
Título vacío:          ❌ "El título no puede estar vacío"
Año inválido (ej: 3000): ❌ "Año no válido"
Código no numérico:    ❌ "El código debe ser numérico"
```

### Casos Límite

```
Cola vacía, atender:   ❌ "No hay solicitudes pendientes"
Libro no existe:       ❌ "Libro no encontrado"
Estudiante sin código: ❌ "Código de estudiante obligatorio"
```

---

## 🎯 Criterio de Aceptación

El proyecto se considera **COMPLETO** cuando:
- ✅ Las 10 pruebas mínimas pasan correctamente
- ✅ Cola<T> funciona con FIFO garantizado O(1)
- ✅ Árbol binario busca en O(log n) promedio
- ✅ No usa LinkedList, Queue o TreeMap
- ✅ Toda entrada se valida
- ✅ GitHub muestra contribuciones de todos

---

**Última actualización:** 2026-06-23
