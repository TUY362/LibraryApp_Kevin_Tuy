# GLOSARIO SEMANA 1
## Modelo y Programación Orientada a Objetos


## Modelo

Es la parte del sistema encargada de representar la información que maneja la aplicación.

Contiene las clases que representan objetos reales del sistema.

Ejemplo:

Libro, Usuario, Cliente.


## Clase

Es una estructura que define características y comportamientos de un objeto.

Contiene atributos y métodos.


## Objeto

Es una instancia creada a partir de una clase.

Representa un elemento específico dentro del programa.


## Atributo

Es una característica o dato que pertenece a una clase.

Ejemplo:

Un libro puede tener:

- ISBN.
- Título.
- Precio.


## Método

Es una acción o comportamiento que puede realizar un objeto.

Ejemplo:

guardarLibro().


## Encapsulamiento

Es un principio de programación orientada a objetos que protege los datos de una clase.

Se aplica utilizando modificadores como:

- private.
- public.


## Getter

Método utilizado para obtener el valor de un atributo.


## Setter

Método utilizado para modificar el valor de un atributo.


## Constructor

Es un método especial que permite crear e inicializar objetos.


## Herencia

Permite que una clase pueda obtener características y métodos de otra clase.


## Polimorfismo

Permite que un mismo método tenga diferentes comportamientos dependiendo del objeto.


## Abstracción

Consiste en representar solamente las características importantes de un objeto.


## Java

Lenguaje de programación orientado a objetos utilizado para desarrollar LibraryApp.


## POO

Programación Orientada a Objetos.

Es un paradigma basado en clases y objetos para organizar el código.


## Modelo entidad

Es una clase Java que representa una entidad de la base de datos.

Ejemplo:

Libro.java representa la tabla libros.


## Javadoc

Herramienta de Java utilizada para documentar clases, métodos y atributos.



















# GLOSARIO SEMANA 2
## DAO, JDBC Y MYSQL


## DAO

DAO significa Data Access Object.

Es un patrón de diseño que permite separar el acceso a la base de datos de la lógica principal del sistema.

Se encarga de realizar operaciones como guardar, buscar, actualizar y eliminar información.


## DAOImpl

Es la implementación del DAO.

Contiene el código necesario para conectarse a la base de datos y ejecutar las operaciones utilizando JDBC.


## JDBC

Java Database Connectivity (JDBC) es una herramienta de Java que permite conectar una aplicación con una base de datos.

Permite ejecutar consultas SQL y procedimientos almacenados.


## Connection

Es la clase que representa la conexión entre Java y la base de datos MySQL.


## CallableStatement

Es una clase de JDBC utilizada para ejecutar procedimientos almacenados.

En el proyecto se utiliza para llamar funciones creadas en MySQL.


## ResultSet

Es un objeto que contiene los datos obtenidos después de realizar una consulta a la base de datos.


## Singleton

Es un patrón de diseño que permite crear solamente una instancia de una clase.

En el proyecto se utiliza en:

- Conexion.
- SesionContext.


## Procedimiento almacenado

Es un conjunto de instrucciones SQL guardadas dentro de la base de datos.

Permite ejecutar operaciones sin escribir nuevamente las consultas.


## MySQL

Es un sistema gestor de bases de datos utilizado para almacenar la información del sistema.


## Excepción

Es un mecanismo utilizado para controlar errores que pueden ocurrir durante la ejecución del programa.


## DaoException

Es una excepción creada para controlar errores relacionados con la conexión o las operaciones de base de datos.


## ValidacionException

Es una excepción utilizada para controlar errores en la validación de datos ingresados por el usuario.


## Inyección SQL

Es un ataque donde un usuario intenta ingresar código SQL malicioso mediante campos de entrada.

Se evita utilizando parámetros y procedimientos almacenados.


## CRUD

Significa:

- Create (Crear)
- Read (Leer)
- Update (Actualizar)
- Delete (Eliminar)

Son las operaciones básicas realizadas sobre los datos.


## Modelo

Es la parte del sistema que representa la información que se maneja.

Ejemplo:

Libro, Usuario, Cliente.


## Persistencia

Es la capacidad de guardar información para que pueda mantenerse después de cerrar el programa.


## SesionContext

Es una clase encargada de almacenar el usuario que tiene una sesión activa dentro del sistema.
