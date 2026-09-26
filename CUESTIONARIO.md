# CUESTIONARIO SEMANA 1
## Modelo y Programación Orientada a Objetos


## 1. ¿Qué es una clase?

Es una plantilla que define atributos y métodos de un objeto.


## 2. ¿Qué es un objeto?

Es una instancia creada a partir de una clase.


## 3. ¿Qué son los atributos?

Son las características o datos que tiene una clase.


## 4. ¿Qué son los métodos?

Son acciones que puede realizar un objeto.


## 5. ¿Qué es encapsulamiento?

Es proteger los datos internos de una clase mediante modificadores de acceso.


## 6. ¿Para qué sirven los getters?

Sirven para obtener información de los atributos.


## 7. ¿Para qué sirven los setters?

Sirven para modificar los valores de los atributos.


## 8. ¿Qué es un constructor?

Es un método utilizado para inicializar objetos.


## 9. ¿Qué es POO?

Es una forma de programación basada en objetos y clases.


## 10. ¿Cuáles son los pilares de la POO?

Son:

- Encapsulamiento.
- Herencia.
- Polimorfismo.
- Abstracción.


## 11. ¿Qué es herencia?

Es cuando una clase obtiene características de otra clase.


## 12. ¿Qué es polimorfismo?

Es la capacidad de tener diferentes comportamientos con el mismo método.


## 13. ¿Qué es abstracción?

Es mostrar solamente la información necesaria de un objeto.


## 14. ¿Qué representa una clase modelo?

Representa una entidad del sistema.


## 15. ¿Por qué se utilizan modelos?

Para organizar la información y representar los datos del sistema.


## 16. ¿Qué lenguaje se utiliza para crear las clases del proyecto?

Java.


## 17. ¿Qué herramienta se utiliza para documentar clases Java?

Javadoc.


## 18. ¿Qué contiene normalmente una clase modelo?

Atributos, constructores, getters y setters.


## 19. ¿Qué significa private?

Indica que un elemento solo puede ser utilizado dentro de la clase.


## 20. ¿Qué significa public?

Indica que un elemento puede ser utilizado desde otras clases.


# CUESTIONARIO SEMANA 2
## DAO, JDBC y MySQL


## 1. ¿Qué significa DAO?

DAO significa Data Access Object.

Es un patrón que permite separar la conexión y operaciones de base de datos del resto del programa.


## 2. ¿Cuál es la función de un DAO?

Su función es realizar operaciones con la base de datos como:

- Crear registros.
- Buscar información.
- Actualizar datos.
- Eliminar registros.


## 3. ¿Qué diferencia existe entre DAO y DAOImpl?

DAO es la interfaz donde se definen los métodos.

DAOImpl es la clase que contiene el código para ejecutar esos métodos usando JDBC.


## 4. ¿Qué es JDBC?

Es una herramienta de Java que permite conectar una aplicación con una base de datos.


## 5. ¿Qué clase se utiliza para abrir una conexión con MySQL?

Se utiliza la clase Connection.


## 6. ¿Qué es CallableStatement?

Es una clase de JDBC que permite ejecutar procedimientos almacenados de MySQL.


## 7. ¿Qué es ResultSet?

Es el objeto que contiene los datos obtenidos de una consulta a la base de datos.


## 8. ¿Qué patrón utiliza la clase Conexion?

Utiliza el patrón Singleton.

Este permite crear una sola instancia de la conexión.


## 9. ¿Para qué sirven los procedimientos almacenados?

Sirven para guardar instrucciones SQL en la base de datos y poder reutilizarlas.


## 10. ¿Qué es una excepción?

Es un mecanismo que permite controlar errores durante la ejecución del programa.


## 11. ¿Para qué sirve DaoException?

Sirve para manejar errores relacionados con el acceso a la base de datos.


## 12. ¿Para qué sirve ValidacionException?

Sirve para controlar errores cuando los datos ingresados por el usuario no cumplen las reglas.


## 13. ¿Qué es Singleton?

Es un patrón de diseño que permite que una clase tenga solamente una instancia.


## 14. ¿Qué es MySQL?

Es un sistema gestor de bases de datos utilizado para almacenar información.


## 15. ¿Cuál es el flujo de trabajo de un DAO?

El flujo es:

Controlador

↓

DAO

↓

DAOImpl

↓

Conexion

↓

Base de datos


## 16. ¿Qué evita la parametrización SQL?

Ayuda a evitar ataques de Inyección SQL.


## 17. ¿Qué información guarda SesionContext?

Guarda la información del usuario que inició sesión.


## 18. ¿Qué patrón utiliza SesionContext?

Utiliza el patrón Singleton.


## 19. ¿Por qué se utilizan interfaces DAO?

Para definir una estructura organizada y separar la lógica de acceso a datos.


## 20. ¿Qué tecnologías se utilizaron en la persistencia?

Se utilizaron:

- Java.
- JDBC.
- MySQL.
- Procedimientos almacenados.




# Cuestionario Semana 3
## JavaFX, FXML y MVC


### 1. ¿Qué es JavaFX?

Es una tecnología de Java utilizada para desarrollar interfaces gráficas.


### 2. ¿Para qué sirve un archivo FXML?

Sirve para diseñar la interfaz gráfica separando el diseño del código Java.


### 3. ¿Qué función tiene un Controller?

Controlar los eventos de la interfaz y conectar la vista con la lógica del programa.


### 4. ¿Qué significa MVC?

Modelo, Vista y Controlador.


### 5. ¿Cuál es la función del Modelo?

Guardar y manejar los datos del sistema.


### 6. ¿Cuál es la función de la Vista?

Mostrar la interfaz con la que interactúa el usuario.


### 7. ¿Cuál es la función del Controlador?

Recibir acciones del usuario y comunicarse con el modelo.


### 8. ¿Qué clase carga un archivo FXML?

FXMLLoader.


### 9. ¿Qué es una Scene en JavaFX?

Es una pantalla que contiene los elementos visuales de la aplicación.


### 10. ¿Qué es un Stage?

Es la ventana principal donde se muestran las escenas.


### 11. ¿Qué permite CSS en JavaFX?

Permite modificar el diseño y apariencia de los componentes.


### 12. ¿Por qué se separa FXML y Controller?

Para tener un código más organizado y fácil de mantener.


### 13. ¿Qué es un evento?

Es una acción realizada por el usuario dentro de la aplicación.


### 14. ¿Qué componente permite mostrar datos en filas y columnas?

TableView.


### 15. ¿Qué patrón ayuda a organizar una aplicación JavaFX?

El patrón MVC.