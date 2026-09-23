GLOSARIO.md

Glosario técnico de LibraryApp

Este glosario reúne conceptos utilizados en el proyecto LibraryApp. Cada ficha incluye el término técnico, una definición formal, una explicación con mis palabras, su ubicación o relación con el proyecto y un ejemplo práctico.

1. Clase

Definición técnica: Estructura de Java que define atributos y métodos que compartirán sus objetos.
En mis palabras: Es una plantilla para crear objetos con características y comportamientos determinados.
Ubicación en el código: org.kt.model.Libro, Autor, Categoria, Cliente, entre otras.
Ejemplo / problema que resuelve: Libro.java permite representar todos los libros utilizando la misma estructura.

2. Objeto

Definición técnica: Instancia concreta de una clase creada durante la ejecución del programa.
En mis palabras: Es un elemento real creado a partir de una clase.
Ubicación en el código: Se crean objetos de modelos como Libro, Autor, Usuario o Venta.
Ejemplo / problema que resuelve: Un objeto Libro puede contener el ISBN, título, precio y stock de un libro específico.

3. Atributo

Definición técnica: Variable declarada dentro de una clase que representa parte del estado de sus objetos.
En mis palabras: Es un dato que describe al objeto.
Ubicación en el código: Libro.java: isbn, titulo, precio, stock.
Ejemplo / problema que resuelve: Permite guardar el precio de cada libro de forma individual.

4. Método

Definición técnica: Bloque de código perteneciente a una clase que realiza una operación y puede recibir parámetros o retornar un valor.
En mis palabras: Es una acción que la clase puede realizar.
Ubicación en el código: LineaVenta.getSubtotal() y SecurityUtil.hashSHA256().
Ejemplo / problema que resuelve: getSubtotal() calcula automáticamente el costo de una línea de venta.

5. Constructor

Definición técnica: Miembro especial de una clase utilizado para inicializar objetos.
En mis palabras: Sirve para crear un objeto y darle valores iniciales.
Ubicación en el código: Libro(), Libro(String isbn, String titulo, ...).
Ejemplo / problema que resuelve: Permite crear un libro vacío o crear uno con todos sus datos principales.

6. Encapsulamiento

Definición técnica: Principio de POO que restringe el acceso directo al estado interno de un objeto y expone operaciones controladas.
En mis palabras: Protege los datos y evita que otras clases los modifiquen directamente.
Ubicación en el código: Los atributos de las clases del paquete org.kt.model son private.
Ejemplo / problema que resuelve: Libro.precio se consulta con getPrecio() y se modifica con setPrecio().

7. Tipo primitivo

Definición técnica: Tipo de dato básico de Java que almacena directamente un valor, como int, long, double o boolean.
En mis palabras: Es un dato simple que no es un objeto.
Ubicación en el código: Libro.stock usa int, Libro.precio usa double y Usuario.activo usa boolean.
Ejemplo / problema que resuelve: Permite guardar de forma sencilla la cantidad disponible de un libro.

8. Clase Wrapper

Definición técnica: Clase que representa como objeto un tipo primitivo, por ejemplo Integer, Long, Double y Boolean.
En mis palabras: Es la versión objeto de un tipo primitivo y puede utilizar null.
Ubicación en el código: Puede utilizarse en modelos o DAO cuando un campo numérico de la base de datos puede ser nulo.
Ejemplo / problema que resuelve: Integer puede diferenciar entre el valor 0 y un valor todavía no asignado (null).

9. Getter

Definición técnica: Método de acceso que devuelve el valor de un atributo.
En mis palabras: Sirve para leer un dato privado.
Ubicación en el código: Libro.getTitulo(), Cliente.getCui().
Ejemplo / problema que resuelve: Una tabla JavaFX puede usar getTitulo() para mostrar el nombre de un libro.

10. Setter

Definición técnica: Método modificador que asigna un nuevo valor a un atributo.
En mis palabras: Sirve para cambiar un dato privado de forma controlada.
Ubicación en el código: Libro.setStock(int stock).
Ejemplo / problema que resuelve: Permite actualizar el stock de un libro sin acceder directamente al atributo.

11. Modificador private

Definición técnica: Modificador que limita el acceso a un miembro únicamente a su propia clase.
En mis palabras: Hace que un dato quede protegido dentro de la clase.
Ubicación en el código: Atributos de Autor, Categoria, Cliente, Libro, etc.
Ejemplo / problema que resuelve: Evita que cualquier parte del programa modifique directamente Libro.stock.

12. Modificador public

Definición técnica: Modificador que permite acceder a un miembro desde otras clases visibles en el proyecto.
En mis palabras: Hace que un método o clase pueda utilizarse desde otras partes del programa.
Ubicación en el código: Getters, setters, constructores y métodos como Conexion.conectar().
Ejemplo / problema que resuelve: Un DAO puede llamar a Conexion.getInstancia().conectar().

13. static

Definición técnica: Indica que un miembro pertenece a la clase y no a una instancia individual.
En mis palabras: Permite usar un método o dato sin crear un objeto de la clase.
Ubicación en el código: Principal.cambiarEscena() y SecurityUtil.hashSHA256().
Ejemplo / problema que resuelve: Un Controller puede cambiar de escena usando Principal.cambiarEscena(...).

14. final

Definición técnica: Palabra reservada que puede utilizarse para impedir que una variable cambie, que un método sea sobrescrito o que una clase sea heredada.
En mis palabras: Se usa para declarar algo que no debe modificarse.
Ubicación en el código: Conexion.CONFIG_FILE está declarado como static final.
Ejemplo / problema que resuelve: Evita modificar por accidente la ruta /db.properties.

15. POJO

Definición técnica: Plain Old Java Object; clase Java sencilla utilizada principalmente para representar datos sin depender obligatoriamente de frameworks.
En mis palabras: Es una clase simple con atributos, constructores y getters/setters.
Ubicación en el código: Usuario.java y la mayoría de clases de org.kt.model.
Ejemplo / problema que resuelve: Usuario representa los datos de una persona que usa el sistema.

16. Modelo

Definición técnica: Capa que representa las entidades y datos del dominio de una aplicación.
En mis palabras: Es la parte que representa la información con la que trabaja el sistema.
Ubicación en el código: Paquete org.kt.model.
Ejemplo / problema que resuelve: Libro, Venta y Cliente organizan los datos principales de LibraryApp.

17. MVC

Definición técnica: Patrón arquitectónico Model-View-Controller que separa los datos, la presentación y el control de interacción.
En mis palabras: Divide el proyecto en partes para que cada clase tenga una responsabilidad clara.
Ubicación en el código: model contiene entidades; las vistas estarán en view/fxml; los Controllers en controller.
Ejemplo / problema que resuelve: Evita colocar datos, SQL y eventos de botones dentro de una sola clase.

18. DAO

Definición técnica: Data Access Object; patrón que encapsula las operaciones de acceso a una fuente de datos.
En mis palabras: Es la parte que se encarga de guardar, consultar, modificar o eliminar datos de MySQL.
Ubicación en el código: Se trabajará en el paquete dao durante la Semana 2.
Ejemplo / problema que resuelve: LibroDAO puede separar las consultas de libros del Controller.

19. JDBC

Definición técnica: API estándar de Java para conectarse y ejecutar operaciones en bases de datos relacionales.
En mis palabras: Es el medio de comunicación entre Java y MySQL.
Ubicación en el código: Conexion.java usa java.sql.Connection y DriverManager.
Ejemplo / problema que resuelve: Permite abrir una conexión para que los DAO ejecuten consultas SQL.

20. Conexión a base de datos

Definición técnica: Sesión establecida entre una aplicación y un servidor de base de datos para ejecutar operaciones.
En mis palabras: Es el enlace que permite que LibraryApp pueda hablar con MySQL.
Ubicación en el código: org.kt.util.Conexion.
Ejemplo / problema que resuelve: conectar() devuelve una conexión nueva usando URL, usuario y contraseña.

21. Singleton

Definición técnica: Patrón de diseño que controla la creación de una clase para mantener una única instancia accesible globalmente.
En mis palabras: Hace que el programa reutilice un solo objeto administrador en lugar de crear muchos.
Ubicación en el código: Conexion posee constructor privado, atributo instancia y método getInstancia().
Ejemplo / problema que resuelve: Centraliza la configuración de conexión a la base de datos.

22. Hash SHA-256

Definición técnica: Función criptográfica que transforma una entrada en un resumen de 256 bits de longitud fija.
En mis palabras: Convierte una contraseña en una cadena difícil de revertir directamente.
Ubicación en el código: org.kt.util.SecurityUtil.hashSHA256(String password).
Ejemplo / problema que resuelve: Evita guardar una contraseña directamente en texto plano.

23. FXML

Definición técnica: Lenguaje XML utilizado por JavaFX para declarar la estructura de una interfaz gráfica.
En mis palabras: Es el archivo donde se puede definir visualmente una pantalla de JavaFX.
Ubicación en el código: Principal.java intenta cargar rutas dentro de /org/kt/view/fxml/.
Ejemplo / problema que resuelve: Permite separar el diseño del formulario de libros del código Java del Controller.

24. JavaFX

Definición técnica: Plataforma de Java para construir aplicaciones gráficas utilizando escenas, controles, FXML, CSS y otros componentes.
En mis palabras: Es la tecnología con la que se construyen las ventanas de LibraryApp.
Ubicación en el código: Principal.java importa Application, FXMLLoader, Parent, Scene y Stage.
Ejemplo / problema que resuelve: Permite mostrar el login y los dashboards del sistema.

25. Scene

Definición técnica: Contenedor de JavaFX que aloja el árbol de nodos que se muestra dentro de un Stage.
En mis palabras: Es el contenido visual que se coloca dentro de una ventana.
Ubicación en el código: Principal.cambiarEscena() crea new Scene(raiz).
Ejemplo / problema que resuelve: Permite reemplazar una pantalla por otra al navegar en la aplicación.

26. Stage

Definición técnica: Ventana principal o secundaria de una aplicación JavaFX.
En mis palabras: Es la ventana física que aparece en pantalla.
Ubicación en el código: Principal guarda private static Stage escenarioPrincipal.
Ejemplo / problema que resuelve: Mantiene la ventana principal mientras se cambian sus escenas.

27. FXMLLoader

Definición técnica: Clase de JavaFX encargada de cargar y construir una interfaz declarada en un archivo FXML.
En mis palabras: Lee el archivo FXML y lo convierte en controles que JavaFX puede mostrar.
Ubicación en el código: Principal.cambiarEscena(String rutaFXML).
Ejemplo / problema que resuelve: Permite abrir la pantalla de inicio de sesión o un dashboard.

28. Relación muchos a muchos

Definición técnica: Relación de base de datos donde varios registros de una tabla pueden relacionarse con varios registros de otra, normalmente mediante una tabla intermedia.
En mis palabras: Sirve cuando un elemento puede tener varios relacionados y esos relacionados también pueden pertenecer a varios elementos.
Ubicación en el código: AutorLibro.java relaciona idAutor con isbn.
Ejemplo / problema que resuelve: Permite que un libro tenga varios autores y que un autor escriba varios libros.

29. Proyección de datos

Definición técnica: Objeto usado para representar un conjunto específico de datos obtenido de una consulta, aunque no corresponda directamente a una única tabla.
En mis palabras: Es una clase creada para mostrar el resultado combinado de varias tablas.
Ubicación en el código: LineaFactura.java.
Ejemplo / problema que resuelve: Puede reunir datos de venta, cliente, libro y usuario para imprimir una factura.

30. Dependencia

Definición técnica: Relación en la que una clase utiliza otra clase, biblioteca o servicio para cumplir su responsabilidad.
En mis palabras: Es algo externo que una clase necesita para funcionar.
Ubicación en el código: LineaVenta depende de Libro; Conexion depende de JDBC; Principal depende de JavaFX.
Ejemplo / problema que resuelve: LineaVenta reutiliza los datos del libro en vez de repetir ISBN, título y precio.