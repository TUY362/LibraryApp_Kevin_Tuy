# COMPONENTES.md

## Análisis de componentes - Semana 1

---

## 1. Autor.java

**Nombre de la clase:** Autor

**Paquete:** `org.kt.model`

**Capa arquitectónica:** Modelo (Model - MVC)

**Responsabilidad única:**  
Representar la información de un autor dentro de LibraryApp. Almacena su identificador, nombre, apellido, nacionalidad y biografía.

**Dependencias directas:**  
La clase no depende directamente de otras clases del modelo. Sus atributos utilizan tipos primitivos y objetos de tipo `String`. La relación entre un autor y un libro es manejada mediante `AutorLibro`.

**Datos principales:**

- `idAutor`: identificador del autor.
- `nombreAutor`: nombre del autor.
- `apellidoAutor`: apellido del autor.
- `nacionalidad`: nacionalidad del autor.
- `biografia`: información biográfica del autor.

**Flujo del dato:**

    Formulario / Base de datos
              ↓
           Autor
              ↓
    Controller / DAO
              ↓
            Vista

El objeto `Autor` recibe o almacena la información de un autor. Posteriormente estos datos pueden ser utilizados por otras capas de la aplicación para mostrarlos o almacenarlos.

---

## 2. Categoria.java

**Nombre de la clase:** Categoria

**Paquete:** `org.kt.model`

**Capa arquitectónica:** Modelo (Model - MVC)

**Responsabilidad única:**  
Representar una categoría utilizada para clasificar los libros registrados en LibraryApp.

**Dependencias directas:**  
No posee dependencias directas con otras clases. Utiliza un atributo de tipo `int` y otro de tipo `String`. La clase `Libro` utiliza `idCategoria` para identificar la categoría a la que pertenece.

**Datos principales:**

- `idCategoria`: identificador de la categoría.
- `nombreCategoria`: nombre de la categoría.

**Flujo del dato:**

    Formulario / Base de datos
              ↓
          Categoria
              ↓
    Controller / DAO
              ↓
            Vista
              ↓
            Libro

La categoría puede obtenerse desde la base de datos o desde información ingresada por el usuario y posteriormente utilizarse para clasificar un libro.

---

## 3. Libro.java

**Nombre de la clase:** Libro

**Paquete:** `org.kt.model`

**Capa arquitectónica:** Modelo (Model - MVC)

**Responsabilidad única:**  
Representar un libro dentro de LibraryApp y almacenar sus datos principales para que puedan ser utilizados por las demás capas de la aplicación.

**Dependencias directas:**  
La clase utiliza tipos como `String`, `double` e `int`. Además, mantiene referencias mediante identificadores hacia `Categoria` y `Editorial`.

- `idCategoria` relaciona el libro con una categoría.
- `nitEditorial` relaciona el libro con una editorial.

**Datos principales:**

- `isbn`: código ISBN que identifica al libro.
- `titulo`: título del libro.
- `fechaPublicacion`: fecha de publicación.
- `precio`: precio del libro.
- `idCategoria`: identificador de su categoría.
- `nitEditorial`: NIT de la editorial.
- `stock`: cantidad disponible.

**Flujo del dato:**

    Categoria ─────┐
                   │
    Editorial ─────┤
                   ↓
                 Libro
                   ↓
           Controller / DAO
                   ↓
           Base de datos / Vista

El objeto `Libro` concentra la información principal de un libro. La categoría y la editorial se relacionan mediante sus identificadores y los datos del libro pueden posteriormente ser utilizados para mostrarlos o almacenarlos.

---