# COMPONENTES SEMANA 1
## Modelo del sistema


# Libro.java

## Paquete

org.kt.model


## Descripción

Clase que representa la información de un libro dentro del sistema.


## Responsabilidad

Almacenar los datos relacionados con un libro.


## Atributos principales

- isbn.
- titulo.
- precio.
- stock.
- idCategoria.
- nitEditorial.


## Relación

Se utiliza posteriormente con LibroDAO para almacenar información en la base de datos.



---

# Usuario.java

## Paquete

org.kt.model


## Descripción

Clase que representa un usuario del sistema.


## Responsabilidad

Guardar la información del usuario que utiliza la aplicación.


## Atributos principales

- id.
- username.
- email.
- passwordHash.
- rol.


## Relación

Se utiliza para autenticación y control de acceso.



---

# Cliente.java

## Paquete

org.kt.model


## Descripción

Clase que representa un cliente que realiza compras.


## Responsabilidad

Guardar información personal del cliente.


## Atributos principales

- cui.
- nombreCliente.
- apellidoCliente.
- correoElectronico.



---

# Venta.java

## Paquete

org.kt.model


## Descripción

Clase que representa una venta realizada en el sistema.


## Responsabilidad

Almacenar la información de una transacción.


## Atributos principales

- noVenta.
- fechaVenta.
- totalVenta.
- cuiCliente.
- idUsuario.



---

# Categoria.java

## Paquete

org.kt.model


## Descripción

Clase que representa una categoría de libros.


## Responsabilidad

Clasificar los libros dentro del sistema.


## Atributos principales

- idCategoria.
- nombreCategoria.



---

# Arquitectura del modelo

El modelo representa los datos del sistema.

Flujo:

Modelo

↓

DAO

↓

Base de datos

# COMPONENTES SEMANA 2
## Arquitectura DAO


# Conexion.java

## Paquete

org.kt.util


## Descripción

Clase encargada de crear y administrar la conexión entre la aplicación Java y la base de datos MySQL.


## Responsabilidad

- Cargar los datos de conexión.
- Crear conexiones JDBC.
- Mantener una única instancia mediante Singleton.


## Tecnologías utilizadas

- Java.
- JDBC.
- MySQL.


## Funcionamiento

Los DAO solicitan una conexión a esta clase para poder realizar operaciones en la base de datos.



---

# LibroDAO.java

## Paquete

org.kt.dao


## Descripción

Interfaz encargada de definir las operaciones que se pueden realizar con los libros.


## Responsabilidad

Define los métodos CRUD para la entidad Libro.


## Métodos principales

- crear()
- actualizar()
- eliminar()
- buscarPorId()
- listarTodos()


## Dependencia

Utiliza el modelo:

Libro.java



---

# LibroDAOImpl.java

## Paquete

org.kt.dao.impl


## Descripción

Clase que implementa la interfaz LibroDAO.


## Responsabilidad

Ejecutar las operaciones de libros utilizando JDBC y procedimientos almacenados.


## Tecnologías utilizadas

- Connection.
- CallableStatement.
- ResultSet.


## Funcionamiento

Recibe una solicitud del DAO.

↓

Realiza la conexión mediante Conexion.java.

↓

Ejecuta un procedimiento almacenado.

↓

Devuelve los resultados.



---

# SesionContext.java

## Paquete

org.kt.manager


## Descripción

Clase encargada de administrar la sesión del usuario actual.


## Responsabilidad

- Guardar el usuario autenticado.
- Obtener información de la sesión.
- Cerrar sesión.


## Patrón utilizado

Singleton.


## Métodos principales

- getInstancia()
- getUsuarioActual()
- setUsuarioActual()
- cerrarSesion()



---

# DaoException.java

## Paquete

org.kt.exception


## Descripción

Excepción utilizada para manejar errores relacionados con el acceso a datos.


## Responsabilidad

Controlar errores de conexión, consultas SQL o procedimientos almacenados.



---

# ValidacionException.java

## Paquete

org.kt.exception


## Descripción

Excepción utilizada para controlar errores en la validación de información ingresada por el usuario.


## Responsabilidad

Validar:

- Campos vacíos.
- Correos.
- Números.
- Fechas.
- Longitudes de datos.



---

# Flujo general del sistema

Controlador

↓

DAO

↓

DAOImpl

↓

Conexion

↓

MySQL

↓

Resultado








# Componentes Semana 3
## LibraryApp


## Controller

Paquete:

org.kt.controller


Responsabilidad:

Controlar las acciones realizadas desde las vistas FXML.


Clases principales:

- InicioSesionController
- LibroController
- ClienteController
- UsuarioController
- VentaController


---

## View

Paquete:

org.kt.view


Contiene la interfaz gráfica del sistema.


Estructura:

view
|
├── fxml
|
└── style


---

## FXML

Ubicación:

org.kt.view.fxml


Responsabilidad:

Definir la estructura visual de las pantallas JavaFX.


Archivos principales:

- InicioSesionView.fxml
- AdminDashborad.fxml
- LibroView.fxml
- ClienteView.fxml
- UsuarioView.fxml


---

## CSS

Ubicación:

org.kt.view.style


Responsabilidad:

Aplicar estilos visuales a las interfaces.


Archivos:

- base.css
- estilo.css
- admindashborad.css


---

## FXMLLoader

Responsabilidad:

Cargar las vistas FXML dentro de la aplicación.


---

## Principal

Paquete:

org.kt.system


Responsabilidad:

Iniciar JavaFX y controlar el cambio de escenas.


---

## SesionContext

Paquete:

org.kt.manager


Responsabilidad:

Guardar la información del usuario que inició sesión mediante el patrón Singleton.


---

## Flujo de funcionamiento

Usuario inicia sesión.

↓

InicioSesionController valida datos.

↓

UsuarioDAO consulta la base de datos.

↓

SesionContext guarda el usuario activo.

↓

Principal cambia la escena según el rol.

↓

Se muestra el dashboard correspondiente.
