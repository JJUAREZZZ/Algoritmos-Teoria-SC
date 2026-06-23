# QuickLibrary - Sistema de Gestión de Préstamos de Libros

## 📋 Descripción del Proyecto
Aplicación Java para gestionar un catálogo de libros y una cola de solicitudes de préstamo en una biblioteca universitaria. El proyecto implementa estructuras de datos personalizadas: **Cola genérica** y **Árbol Binario de Búsqueda genérico**.

**Duración:** 1 semana  
**Lenguaje:** Java

---

## 📁 Estructura del Proyecto

```
PROYECTO_ADS/
├── src/
│   ├── main/java/
│   │   ├── estructuras/           # Cola y Árbol Binario de Búsqueda genéricos
│   │   ├── modelos/               # Clases Libro y SolicitudPréstamo
│   │   ├── logica/                # Gestor de la biblioteca
│   │   └── interfaz/              # Menú y entrada del programa
│   └── test/java/                 # Pruebas unitarias
├── docs/
│   ├── README.md                  # Este archivo
│   ├── APORTES.md                 # Registro de contribuciones
│   └── ANALISIS_COMPLEJIDAD.md    # Análisis de operaciones
├── datos/                         # Archivos CSV con libros iniciales
├── lib/                           # Librerías externas (si aplica)
├── bin/                           # Archivos compilados
└── recursos/                      # Recursos adicionales
```

---

## 🎯 Objetivos

### Generales
- Diseñar e implementar una aplicación para gestionar libros y solicitudes de préstamo
- Utilizar una estructura de datos lineal (Cola) y no lineal (Árbol Binario de Búsqueda)

### Específicos
- ✅ Implementar una Cola genérica con nodos enlazados
- ✅ Implementar un Árbol Binario de Búsqueda genérico
- ✅ Registrar y consultar libros
- ✅ Administrar solicitudes de préstamo respetando FIFO
- ✅ Analizar complejidad temporal de operaciones
- ✅ Usar Git/GitHub para control de versiones

---

## 🏗️ Componentes Principales

### Estructuras de Datos (`estructuras`)
- **Cola<T>**: Cola genérica con operaciones enqueue, dequeue, peek, isEmpty, size, mostrar
- **ArbolBinarioBúsqueda<T>**: Árbol genérico con inserción, búsqueda, eliminación, recorrido inorden

### Modelos (`modelos`)
- **Libro**: Código, Título, Autor, Categoría, Año, Estado
- **SolicitudPréstamo**: Código estudiante, Nombre, Código libro, Fecha solicitud

### Lógica (`logica`)
- **GestorBiblioteca**: Gestión de libros, búsquedas, préstamos y reportes

### Interfaz (`interfaz`)
- **Menu**: Menú de consola con 12 opciones principales
- **Main**: Punto de entrada de la aplicación

---

## 📋 Requisitos Funcionales

| ID | Descripción |
|-----|-------------|
| RF01 | Gestión de libros (registrar, mostrar, modificar, eliminar, disponibles, prestados) |
| RF02 | Búsqueda por código (árbol), título, autor, categoría |
| RF03 | Gestión de solicitudes de préstamo (registrar, mostrar, consultar, atender) |
| RF04 | Préstamo verificando disponibilidad |
| RF05 | Devolución de libros |
| RF06 | Reportes (cantidades, estado) |

---

## 🚀 Cómo Ejecutar

### Compilar
```bash
javac -d bin src/main/java/*/*.java
```

### Ejecutar
```bash
java -cp bin interfaz.Main
```

---

## 📊 Menú Principal

```
QUICKLIBRARY
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
```

---

## 📦 Datos Iniciales

El sistema trabaja con mínimo 30 libros. Pueden ingresarse:
- Directamente desde el programa
- Desde archivo CSV (`datos/libros.csv`)
- Manualmente mediante el menú

---

## 🧪 Pruebas Mínimas Requeridas

1. ✓ Registro correcto de libro
2. ✓ Rechazo de código duplicado
3. ✓ Búsqueda de libro existente
4. ✓ Búsqueda de libro inexistente
5. ✓ Registro de 3 solicitudes
6. ✓ Atención en orden FIFO
7. ✓ Rechazo de préstamo a libro no disponible
8. ✓ Devolución de libro
9. ✓ Eliminación de libro
10. ✓ Visualización de reporte

---

## ➕ Funcionalidades Opcionales

- Persistencia mediante archivos
- Interfaz gráfica (Swing/JavaFX)
- Historial de préstamos (Pila)
- Ordenamiento por título
- Exportación de reportes
- Árbol AVL balanceado
- Pruebas unitarias con JUnit

---

## 📚 Información del Proyecto

**Materia:** Algoritmos y Estructuras de Datos  
**Tipo:** Proyecto Corto  
**Plazo:** 1 semana

