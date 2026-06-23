# 📝 Documentación de Aportes - QuickLibrary

## 👥 Asignación de Roles

| Integrante | Rol | Responsabilidades |
|-----------|-----|-------------------|
| Integrante 1 | **Estructuras de Datos** | Implementar Cola<T>, ArbolBinarioBúsqueda<T>, Nodos, Nodos del árbol |
| Integrante 2 | **Lógica del Sistema** | GestorBiblioteca, validaciones, préstamos, devoluciones, reportes |
| Integrante 3 | **Integración y Pruebas** | Menú (Main), interfaz, pruebas funcionales, documentación |

---

## 📅 Plan de Trabajo (7 días)

| Día | Actividad | Entregables |
|-----|-----------|-------------|
| **Día 1** | Análisis del problema, diseño de clases, crear repositorio | Diagrama de clases, README |
| **Día 2** | Implementación de Cola<T> genérica | Cola funcional con nodos enlazados |
| **Día 3** | Implementación de ArbolBinarioBúsqueda<T> | Árbol con inserción, búsqueda, eliminación |
| **Día 4** | Gestión de libros, búsquedas, eliminación | Modelos y lógica de búsqueda |
| **Día 5** | Solicitudes, préstamos y devoluciones | RF03, RF04, RF05 completos |
| **Día 6** | Integración, validaciones, pruebas | Menú funcional, 10 pruebas mínimas |
| **Día 7** | Documentación, análisis de complejidad, presentación | Informe técnico, slides |

---
![alt text](12.png)

### Prueba 2: Búsqueda
[Ver imagnes](imagnes/12.png)


## 📊 Registro de Aportes

### Integrante 1: Estructuras de Datos

| Fecha | Componente | Descripción | Estado |
|-------|-----------|-------------|--------|
| Día 2 | `estructuras/Nodo.java` | Nodo genérico para Cola | ⏳ Pendiente |
| Día 2 | `estructuras/Cola.java` | Implementación Cola genérica (enqueue, dequeue, peek) | ⏳ Pendiente |
| Día 3 | `estructuras/NodoArbol.java` | Nodo para Árbol Binario | ⏳ Pendiente |
| Día 3 | `estructuras/ArbolBinarioBúsqueda.java` | Árbol genérico con operaciones completas | ⏳ Pendiente |

### Integrante 2: Lógica del Sistema

| Fecha | Componente | Descripción | Estado |
|-------|-----------|-------------|--------|
| Día 1 | `modelos/Libro.java` | Clase Libro con atributos | ⏳ Pendiente |
| Día 1 | `modelos/SolicitudPréstamo.java` | Clase SolicitudPréstamo | ⏳ Pendiente |
| Día 4 | `logica/GestorBiblioteca.java` | Gestión de libros y búsquedas | ⏳ Pendiente |
| Día 5 | `logica/GestorBiblioteca.java` | Préstamos y devoluciones | ⏳ Pendiente |
| Día 6 | `logica/GestorBiblioteca.java` | Reportes y validaciones | ⏳ Pendiente |

### Integrante 3: Integración y Pruebas

| Fecha | Componente | Descripción | Estado |
|-------|-----------|-------------|--------|
| Día 1 | Repositorio GitHub | Crear y configurar repo | ⏳ Pendiente |
| Día 6 | `interfaz/Menu.java` | Menú de consola (12 opciones) | ⏳ Pendiente |
| Día 6 | `interfaz/Main.java` | Punto de entrada principal | ⏳ Pendiente |
| Día 6 | Pruebas Unitarias | Ejecutar 10 pruebas mínimas | ⏳ Pendiente |
| Día 7 | Documentación | Informe técnico 4-6 páginas | ⏳ Pendiente |

---

## 🎯 Requisitos Funcionales a Implementar

### RF01: Gestión de Libros
- [ ] Registrar un libro
- [ ] Mostrar todos los libros
- [ ] Modificar datos de un libro
- [ ] Eliminar un libro
- [ ] Mostrar libros disponibles
- [ ] Mostrar libros prestados

### RF02: Búsqueda de Libros
- [ ] Buscar por código (usando Árbol)
- [ ] Buscar por título
- [ ] Buscar por autor
- [ ] Buscar por categoría

### RF03: Solicitudes de Préstamo
- [ ] Registrar solicitud
- [ ] Mostrar cola de solicitudes
- [ ] Consultar siguiente solicitud
- [ ] Atender siguiente solicitud (FIFO)
- [ ] Eliminar solicitud atendida

### RF04: Préstamo de Libros
- [ ] Verificar que libro exista
- [ ] Comprobar disponibilidad
- [ ] Cambiar estado a "prestado"
- [ ] Retirar solicitud de cola
- [ ] Mostrar confirmación

### RF05: Devolución de Libros
- [ ] Registrar devolución
- [ ] Cambiar estado a "disponible"
- [ ] Mostrar confirmación

### RF06: Reportes
- [ ] Cantidad total de libros
- [ ] Cantidad de libros disponibles
- [ ] Cantidad de libros prestados
- [ ] Cantidad de solicitudes pendientes

---

## 📈 Análisis de Complejidad (Por completar)

| Operación | Estructura | Complejidad Esperada | Complejidad Peor Caso |
|-----------|-----------|-------------------|----------------------|
| Registrar solicitud | Cola | O(1) | O(1) |
| Atender solicitud | Cola | O(1) | O(1) |
| Buscar libro | Árbol | O(log n) | O(n) |
| Insertar libro | Árbol | O(log n) | O(n) |
| Recorrer libros | Árbol | O(n) | O(n) |

---

## 📌 Notas Importantes

- ✅ No se permite usar LinkedList, Queue, PriorityQueue, TreeMap
- ✅ Las estructuras DEBEN ser genéricas <T>
- ✅ Cada estudiante debe tener contribuciones visibles en GitHub
- ✅ Validar datos incorrectos y campos vacíos
- ✅ Organizar código en paquetes
- ✅ Mínimo 30 libros en el sistema

---

## 📚 Entregables Finales

1. **Código fuente en GitHub**
   - Código Java organizado
   - Comentarios relevantes
   - README completo
   - Historial de contribuciones

2. **Informe Técnico (4-6 páginas PDF)**
   - Descripción del problema
   - Diseño de clases
   - Estructuras implementadas
   - Capturas de funcionamiento
   - Análisis de complejidad
   - Resultados de pruebas

3. **Manual de Usuario**
   - Requisitos de ejecución
   - Pasos de instalación
   - Opciones del menú
   - Ejemplos de uso

4. **Presentación (5-7 minutos)**
   - Exposición del equipo
   - Demostración en vivo

---

**Estado General:** 🔄 En Planificación

Última actualización: 2026-06-23

