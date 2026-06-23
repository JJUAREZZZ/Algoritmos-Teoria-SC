# 🚀 Guía Rápida - QuickLibrary

## ⚡ Inicio Rápido

### 1. Clonar/Abrir el Repositorio
```bash
cd PROYECTO_ADS
```

### 2. Estructura de Carpetas
```
src/main/java/
  ├── estructuras/    → Cola.java, Nodo.java, ArbolBinarioBúsqueda.java, NodoArbol.java
  ├── modelos/        → Libro.java, SolicitudPréstamo.java
  ├── logica/         → GestorBiblioteca.java
  └── interfaz/       → Menu.java, Main.java
datos/
  └── libros.csv      → 30 libros iniciales
```

### 3. Compilar el Proyecto
```bash
javac -d bin src/main/java/*/*.java
```

### 4. Ejecutar
```bash
java -cp bin interfaz.Main
```

---

## 📋 Checklist de Desarrollo

### Integrante 1: Estructuras de Datos

**Día 2 - Cola<T>**
- [ ] Crear `estructuras/Nodo.java`
- [ ] Crear `estructuras/Cola.java`
- [ ] Implementar: enqueue, dequeue, peek, isEmpty, size, mostrar
- [ ] Probar métodos de forma aislada

**Día 3 - Árbol Binario**
- [ ] Crear `estructuras/NodoArbol.java`
- [ ] Crear `estructuras/ArbolBinarioBúsqueda.java`
- [ ] Implementar: insertar, buscar, eliminar, recorridoInorden, contar, estaVacio
- [ ] Probar métodos de forma aislada

---

### Integrante 2: Lógica del Sistema

**Día 1 - Modelos**
- [ ] Crear `modelos/Libro.java` (6 atributos)
- [ ] Crear `modelos/SolicitudPréstamo.java` (4 atributos)
- [ ] Implementar toString() y getters

**Día 4 - Gestión de Libros**
- [ ] Crear `logica/GestorBiblioteca.java`
- [ ] Implementar RF01: registrar, mostrar, modificar, eliminar, disponibles, prestados
- [ ] Implementar parte de RF02: búsquedas

**Día 5 - Préstamos y Devoluciones**
- [ ] Implementar RF03: solicitudes en cola
- [ ] Implementar RF04: lógica de préstamo
- [ ] Implementar RF05: devolución
- [ ] Implementar RF06: reportes

---

### Integrante 3: Integración y Pruebas

**Día 1 - Repositorio**
- [ ] Crear repositorio GitHub
- [ ] Configurar .gitignore
- [ ] Hacer primer commit

**Día 6 - Interfaz**
- [ ] Crear `interfaz/Menu.java` (12 opciones)
- [ ] Crear `interfaz/Main.java`
- [ ] Integrar todas las funciones
- [ ] Validar datos de entrada

**Día 6-7 - Pruebas**
- [ ] Ejecutar 10 pruebas mínimas
- [ ] Crear evidencia (capturas)
- [ ] Documentar resultados

**Día 7 - Documentación**
- [ ] Actualizar README.md
- [ ] Crear informe técnico PDF
- [ ] Preparar presentación

---

## 🧪 Plantilla de Prueba

```
PRUEBA 1: Registrar libro
├─ Entrada: 101, "Programación en Java", "Herbert Schildt", "Programación", 2022
├─ Acción: Menu → Opción 1
└─ Resultado esperado: ✅ "Libro registrado exitosamente"

PRUEBA 2: Código duplicado
├─ Entrada: Intentar registrar 101 nuevamente
├─ Acción: Menu → Opción 1
└─ Resultado esperado: ❌ "Error: código ya existe"

[...]
```

---



## 📝 Ejemplo de Implementación: Clase Libro

```java
package modelos;

public class Libro implements Comparable<Libro> {
    private String codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private int anio;
    private String estado; // "Disponible" o "Prestado"
    
    public Libro(String codigo, String titulo, String autor, 
                 String categoria, int anio, String estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anio = anio;
        this.estado = estado;
    }
    
    @Override
    public int compareTo(Libro otro) {
        // Compara por código (numérico)
        return Integer.compare(Integer.parseInt(this.codigo), 
                              Integer.parseInt(otro.codigo));
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%d) - %s", 
            codigo, titulo, autor, anio, estado);
    }
    
    // Getters y setters...
}
```

---

## 📤 Commits Sugeridos en GitHub

```
Día 1: "feat: Análisis inicial y diseño de clases"
Día 2: "feat: Implementación de Cola<T>"
Día 3: "feat: Implementación de ArbolBinarioBúsqueda<T>"
Día 4: "feat: Gestión de libros y búsquedas"
Día 5: "feat: Sistema de préstamos y devoluciones"
Día 6: "feat: Interfaz de menú y pruebas"
Día 7: "docs: Documentación final y análisis de complejidad"
```

---

## 🔍 Validación: ¿Está todo listo?

Antes de entregar:

- [ ] ✅ Cola<T> implementada y funcionando
- [ ] ✅ ArbolBinarioBúsqueda<T> implementado y funcionando
- [ ] ✅ Todas las clases con generics <T>
- [ ] ✅ Sin uso de LinkedList, Queue, TreeMap
- [ ] ✅ 30 libros cargados
- [ ] ✅ Menú con 12 opciones
- [ ] ✅ 10 pruebas mínimas pasadas
- [ ] ✅ Contribuciones visibles en GitHub
- [ ] ✅ README.md completo
- [ ] ✅ Informe técnico 4-6 páginas
- [ ] ✅ Análisis de complejidad documentado
- [ ] ✅ Código comentado y organizado en paquetes

---

## 📞 Contacto y Soporte

En caso de dudas sobre:
- **Estructuras:** Ver `docs/ESPECIFICACIONES.md`
- **Complejidad:** Ver `docs/ANALISIS_COMPLEJIDAD.md`
- **Aportes:** Ver `docs/APORTES.md`
- **Requisitos:** Ver `docs/README.md`

---

**Última actualización:** 2026-06-23
