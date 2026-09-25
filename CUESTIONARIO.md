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