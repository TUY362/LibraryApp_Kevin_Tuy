Java y Programación Orientada a Objetos

Pregunta 1. ¿Cuál es la diferencia entre una clase y un objeto?

Respuesta investigada:
Una clase es una plantilla que define los atributos y comportamientos que tendrán sus objetos. Un objeto es una instancia concreta creada a partir de una clase y posee valores propios en sus atributos.

Explicación con mis palabras:
La clase indica cómo debe ser algo y el objeto es un ejemplar real de esa clase. Por ejemplo, Libro define qué información tendrá un libro, mientras que un libro específico con ISBN, título y precio es un objeto.

Ejemplo en LibraryApp:
Libro.java es una clase. Al ejecutar new Libro("9780001", "Java Básico", "2026", 150.00, 1, "1234-5", 10) se crea un objeto de tipo Libro.

Pregunta 2. ¿Qué es un atributo?

Respuesta investigada:
Un atributo es una variable declarada dentro de una clase que representa una característica o estado de los objetos de esa clase.

Explicación con mis palabras:
Los atributos guardan los datos que describen un objeto.

Ejemplo en LibraryApp:
En Libro.java, isbn, titulo, precio, idCategoria, nitEditorial y stock son atributos.

Pregunta 3. ¿Qué es un método?

Respuesta investigada:
Un método es un bloque de código definido dentro de una clase que realiza una acción o devuelve un resultado.

Explicación con mis palabras:
Un método representa algo que un objeto o una clase puede hacer.

Ejemplo en LibraryApp:
En LineaVenta.java, el método getSubtotal() calcula el subtotal multiplicando el precio del libro por la cantidad.

Pregunta 4. ¿Para qué sirve un constructor?

Respuesta investigada:
Un constructor sirve para inicializar un objeto cuando se crea. Tiene el mismo nombre de la clase y no declara un tipo de retorno.

Explicación con mis palabras:
El constructor permite crear un objeto con sus datos iniciales listos desde el principio.

Ejemplo en LibraryApp:
Categoria posee un constructor vacío y otro constructor que recibe idCategoria y nombreCategoria.

Pregunta 5. ¿Cuál es la diferencia entre private, public y protected?

Respuesta investigada:
Son modificadores de acceso. private permite acceder solamente desde la misma clase; public permite acceder desde cualquier clase que tenga acceso al objeto; protected permite el acceso desde el mismo paquete y también desde clases hijas.

Explicación con mis palabras:
Sirven para controlar qué partes del programa pueden usar directamente un atributo, método o clase.

Ejemplo en LibraryApp:
Los atributos de Cliente.java son private, mientras que sus getters y setters son public para permitir un acceso controlado.

Pregunta 6. ¿Para qué sirven los métodos get y set?

Respuesta investigada:
Los getters permiten obtener el valor de un atributo y los setters permiten modificarlo sin acceder directamente al atributo privado.

Explicación con mis palabras:
Son la forma controlada de leer y cambiar datos privados de un objeto.

Ejemplo en LibraryApp:
Libro.getTitulo() devuelve el título y Libro.setTitulo(String titulo) permite cambiarlo.

Pregunta 7. ¿Qué significa encapsulamiento?

Respuesta investigada:
El encapsulamiento es un principio de POO que consiste en proteger el estado interno de un objeto y controlar el acceso a sus datos mediante métodos definidos por la clase.

Explicación con mis palabras:
En lugar de permitir que cualquier clase cambie directamente los datos, se mantienen privados y se accede a ellos usando métodos.

Ejemplo en LibraryApp:
En Autor.java, los atributos están declarados como private y se utilizan getters y setters para acceder a ellos.

Pregunta 8. ¿Cuál es la diferencia entre una clase y una interfaz?

Respuesta investigada:
Una clase puede contener atributos, constructores y métodos con implementación. Una interfaz define principalmente un contrato de métodos que otras clases deben implementar.

Explicación con mis palabras:
La clase representa un objeto completo, mientras que una interfaz indica qué operaciones debe ofrecer una clase sin obligar a que todas se programen de la misma forma.

Ejemplo en LibraryApp:
En las siguientes semanas, una interfaz como LibroDAO puede declarar operaciones de persistencia y una clase LibroDAOImpl puede implementar su funcionamiento con JDBC.

Pregunta 9. ¿Qué significa implements?

Respuesta investigada:
implements se utiliza cuando una clase adopta una interfaz y se compromete a implementar los métodos definidos por ella.

Explicación con mis palabras:
Indica que una clase va a cumplir el contrato definido por una interfaz.

Ejemplo en LibraryApp:
Una posible clase LibroDAOImpl implements LibroDAO debe proporcionar el código de los métodos declarados en LibroDAO.

Pregunta 10. ¿Qué significan static y final?

Respuesta investigada:
static indica que un miembro pertenece a la clase y no a una instancia particular. final impide modificar una variable después de asignarla, sobrescribir un método o heredar de una clase, según dónde se utilice.

Explicación con mis palabras:
static permite usar algo sin crear un objeto de la clase. final se utiliza cuando algo no debe cambiar.

Ejemplo en LibraryApp:
En Principal.java, cambiarEscena() es static, por lo que puede llamarse desde otros controladores sin crear un objeto Principal. En Conexion.java, CONFIG_FILE es static final porque la ruta del archivo de configuración debe mantenerse constante.

Base de datos y persistencia

Pregunta 11. ¿Qué es una tabla relacional?

Respuesta investigada:
Una tabla relacional organiza información en filas y columnas. Cada fila representa un registro y cada columna representa un atributo del registro.

Explicación con mis palabras:
Es una estructura de la base de datos donde se guardan datos del mismo tipo de entidad.

Ejemplo en LibraryApp:
Una tabla libros puede guardar ISBN, título, fecha de publicación, precio, categoría, editorial y stock.

Pregunta 12. ¿Qué es una clave primaria?

Respuesta investigada:
Una clave primaria identifica de forma única cada registro de una tabla y no debe repetirse.

Explicación con mis palabras:
Es el dato que permite distinguir un registro de todos los demás.

Ejemplo en LibraryApp:
El ISBN puede funcionar como clave primaria para identificar cada libro.

Pregunta 13. ¿Qué es una clave foránea?

Respuesta investigada:
Una clave foránea es un campo que referencia la clave primaria de otra tabla y permite establecer relaciones entre tablas.

Explicación con mis palabras:
Sirve para conectar información que pertenece a tablas diferentes.

Ejemplo en LibraryApp:
idCategoria dentro de un libro puede apuntar a idCategoria de la tabla categorias.

Pregunta 14. ¿Qué significa CRUD?

Respuesta investigada:
CRUD corresponde a Create, Read, Update y Delete: crear, consultar, actualizar y eliminar registros.

Explicación con mis palabras:
Son las cuatro operaciones principales que normalmente se realizan con información almacenada.

Ejemplo en LibraryApp:
Para libros: registrar un libro, consultar libros, modificar sus datos y eliminar o desactivar un registro.

Pregunta 15. ¿Qué es JDBC?

Respuesta investigada:
JDBC significa Java Database Connectivity y es la API de Java utilizada para conectarse y ejecutar operaciones sobre bases de datos relacionales.

Explicación con mis palabras:
Es el puente que permite que el programa Java se comunique con MySQL.

Ejemplo en LibraryApp:
Conexion.java utiliza DriverManager.getConnection(url, user, password) para obtener una conexión JDBC con MySQL.

Pregunta 16. ¿Qué es un procedimiento almacenado?

Respuesta investigada:
Un procedimiento almacenado es un conjunto de instrucciones SQL guardadas en el servidor de base de datos que puede ejecutarse mediante una llamada.

Explicación con mis palabras:
Es una operación SQL preparada dentro de MySQL para reutilizarla sin escribir toda la consulta desde Java cada vez.

Ejemplo en LibraryApp:
El comentario de LineaFactura.java menciona sp_buscar_factura, un procedimiento que puede obtener datos de venta, cliente, libro y usuario para mostrar una factura.

Pregunta 17. ¿Qué es DAO?

Respuesta investigada:
DAO significa Data Access Object. Es un patrón que separa la lógica de acceso a datos del resto de la aplicación.

Explicación con mis palabras:
Una clase DAO se encarga de hablar con la base de datos para que los modelos y controladores no tengan SQL mezclado con otras responsabilidades.

Ejemplo en LibraryApp:
LibroDAO puede declarar métodos como guardar, buscar, actualizar y eliminar libros, mientras LibroDAOImpl realiza esas operaciones con JDBC.

Pregunta 18. ¿Por qué se debe separar el SQL del Controller?

Respuesta investigada:
Separar el SQL del controlador mejora la organización, el mantenimiento, las pruebas y la reutilización del código. Cada capa mantiene una responsabilidad específica.

Explicación con mis palabras:
El Controller debe atender eventos de la interfaz, no encargarse directamente de escribir consultas SQL. Para eso existe el DAO.

Ejemplo en LibraryApp:
Un LibroController debería solicitar libroDAO.listar() y después mostrar los resultados, en vez de abrir una conexión y ejecutar un SELECT dentro del botón.

JavaFX y arquitectura

Pregunta 19. ¿Qué es JavaFX?

Respuesta investigada:
JavaFX es una plataforma de Java para desarrollar interfaces gráficas de escritorio utilizando controles, escenas, ventanas, CSS y FXML.

Explicación con mis palabras:
Es la tecnología que permite construir las ventanas, botones, tablas y formularios de LibraryApp.

Ejemplo en LibraryApp:
Principal.java extiende Application y trabaja con Stage, Scene, Parent y FXMLLoader.

Pregunta 20. ¿Qué es FXML?

Respuesta investigada:
FXML es un lenguaje basado en XML utilizado por JavaFX para describir la estructura visual de una interfaz separándola del código Java.

Explicación con mis palabras:
FXML guarda el diseño de una pantalla, mientras que el Controller contiene el comportamiento de sus controles.

Ejemplo en LibraryApp:
Principal.cambiarEscena() utiliza FXMLLoader para cargar archivos ubicados en rutas como /org/kt/view/fxml/....

Pregunta 21. ¿Qué función cumple Scene Builder?

Respuesta investigada:
Scene Builder es una herramienta visual para diseñar interfaces JavaFX y generar o modificar archivos FXML.

Explicación con mis palabras:
Permite colocar botones, campos, tablas y otros controles de forma visual sin escribir manualmente todo el FXML.

Ejemplo en LibraryApp:
Se puede utilizar para diseñar la pantalla de libros y asignarle un fx:controller y métodos de eventos.

Pregunta 22. ¿Qué es un Controller?

Respuesta investigada:
En una aplicación JavaFX con MVC, un Controller es la clase que recibe eventos de la vista, valida entradas y coordina la interacción con el modelo o la capa de datos.

Explicación con mis palabras:
Es el intermediario entre lo que hace el usuario en la pantalla y los datos del sistema.

Ejemplo en LibraryApp:
Un LibroController puede recibir el clic del botón Guardar, obtener la información de los campos, construir un Libro y enviarlo al DAO.

Pregunta 23. ¿Qué significa @FXML?

Respuesta investigada:
@FXML es una anotación que permite que FXMLLoader acceda a campos y métodos del Controller asociados con elementos definidos en el archivo FXML.

Explicación con mis palabras:
Sirve para conectar controles y eventos del archivo FXML con el código del Controller.

Ejemplo en LibraryApp:
Un campo @FXML private TextField txtTitulo; puede enlazarse con un TextField que tenga fx:id="txtTitulo".

Pregunta 24. ¿Qué significa MVC?

Respuesta investigada:
MVC significa Model-View-Controller. Es un patrón de arquitectura que separa datos y reglas del dominio, interfaz gráfica y control de eventos.

Explicación con mis palabras:
Divide el programa en partes para evitar que toda la lógica quede mezclada en una sola clase.

Ejemplo en LibraryApp:
Libro.java corresponde al Model, un archivo LibroView.fxml corresponde a la View y LibroController.java corresponde al Controller.

Pregunta 25. ¿Qué responsabilidad tiene el Modelo?

Respuesta investigada:
El Modelo representa los datos y conceptos del dominio de la aplicación. Puede contener propiedades y comportamientos propios de esas entidades.

Explicación con mis palabras:
El modelo representa la información real con la que trabaja el sistema.

Ejemplo en LibraryApp:
Libro, Autor, Categoria, Cliente, Venta y Usuario son clases del modelo porque representan datos del negocio.

Pregunta 26. ¿Qué responsabilidad tiene la Vista?

Respuesta investigada:
La Vista presenta la información al usuario y contiene los elementos visuales con los que interactúa.

Explicación con mis palabras:
Es lo que el usuario ve: botones, campos, etiquetas, tablas y formularios.

Ejemplo en LibraryApp:
Una vista FXML de libros puede mostrar una tabla de libros, campos para ISBN y título y botones para guardar o modificar.

Pregunta 27. ¿Qué responsabilidad tiene el Controller?

Respuesta investigada:
El Controller procesa las acciones realizadas por el usuario, coordina la vista con el modelo y llama a los servicios o DAO necesarios.

Explicación con mis palabras:
Recibe lo que ocurre en la pantalla y decide qué debe hacer el programa con esos datos.

Ejemplo en LibraryApp:
Cuando el usuario presiona Guardar Libro, el Controller valida los campos, crea un objeto Libro, llama al DAO y actualiza la tabla de la interfaz.

Pregunta 28. Dibuje el recorrido de un dato desde la interfaz hasta MySQL.

Respuesta investigada:
En una arquitectura JavaFX con MVC y DAO, el dato normalmente pasa desde la Vista al Controller, luego al Modelo o al DAO, después por JDBC y finalmente llega a MySQL. Para consultar información, el recorrido regresa en sentido contrario.

Explicación con mis palabras:
El usuario escribe un dato; el Controller lo recoge y lo organiza en un objeto. El DAO toma ese objeto y ejecuta la operación en la base de datos usando JDBC.

Ejemplo en LibraryApp:

Usuario
   ↓
Vista FXML
   ↓
Controller JavaFX
   ↓
Objeto Model, por ejemplo Libro
   ↓
LibroDAO / LibroDAOImpl
   ↓
Conexion.java + JDBC
   ↓
MySQL

Para una consulta:

MySQL
   ↓
JDBC
   ↓
DAO
   ↓
Objeto Libro
   ↓
Controller
   ↓
Vista FXML
   ↓
Usuario

Pregunta extra. ¿Cuál es la diferencia técnica entre un tipo primitivo y una clase envolvente (Wrapper) en Java al manejar datos para el Modelo?

Respuesta investigada:
Un tipo primitivo en Java almacena directamente un valor simple, como int, double, long o boolean. Una clase Wrapper representa ese tipo primitivo como un objeto, por ejemplo Integer, Double, Long o Boolean. Las clases Wrapper pueden utilizar valores null y también proporcionan métodos adicionales.

Explicación con mis palabras:
Un tipo primitivo sirve para guardar un valor directamente, mientras que una clase Wrapper convierte ese tipo de dato en un objeto. Esto es útil cuando necesitamos trabajar con colecciones, valores nulos o funciones que requieren objetos.

Ejemplo en LibraryApp:
En Libro.java el atributo stock utiliza int porque siempre representa una cantidad numérica. Si fuera necesario permitir que el stock todavía no tuviera un valor asignado, se podría utilizar Integer, ya que Integer puede contener null.