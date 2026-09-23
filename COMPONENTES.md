COMPONENTES.md

Análisis de componentes de LibraryApp — Semana 1

Este documento registra la responsabilidad, capa arquitectónica, entradas, salidas y dependencias de las clases estudiadas durante la Semana 1. El objetivo es comprender el proyecto antes de reconstruir las capas DAO y MVC.

1. Principal.java



Flujo:

main()
  ↓
JavaFX Application.launch()
  ↓
start(Stage)
  ↓
cambiarEscena(rutaFXML)
  ↓
FXMLLoader
  ↓
Scene
  ↓
Stage principal

2. Autor.java



Flujo del dato:

Base de datos / formulario
  ↓
Autor
  ↓
Controller / DAO / Vista

3. AutorLibro.java


Flujo del dato:

Autor.idAutor + Libro.isbn
  ↓
AutorLibro
  ↓
Relación autor ↔ libro

4. Categoria.java



Flujo del dato:

MySQL / formulario de categoría
  ↓
Categoria
  ↓
Libro.idCategoria / ComboBox / tabla

5. Cliente.java



Flujo del dato:

Formulario cliente / MySQL
  ↓
Cliente
  ↓
Venta / factura / vista

6. DetalleVenta.java



Flujo del dato:

Venta + Libro + cantidad
  ↓
DetalleVenta
  ↓
DAO
  ↓
MySQL

7. Editorial.java


Flujo del dato:

Formulario / MySQL
  ↓
Editorial
  ↓
Libro.nitEditorial

8. Libro.java


Flujo del dato:

Formulario de libro
  ↓
Libro
  ↓
LibroDAO
  ↓
Conexion / JDBC
  ↓
MySQL

Flujo de consulta:

MySQL
  ↓
LibroDAO
  ↓
Libro
  ↓
Controller
  ↓
Tabla JavaFX

9. LineaFactura.java

Paquete: org.kt.model
Capa arquitectónica: Modelo / proyección de consulta.
Responsabilidad única: Representar una fila de información de factura obtenida de una consulta que combina venta, cliente, libro y usuario.
Entradas: Número de factura, fecha, datos del cliente, datos del libro, cantidad, precio, subtotal, usuario y total.
Salidas: Información lista para mostrar o imprimir en una factura.
Dependencias directas: No contiene objetos de otras clases, pero depende conceptualmente de datos de Venta, Cliente, Libro y Usuario. El propio código menciona el procedimiento sp_buscar_factura.

Flujo del dato:

MySQL / sp_buscar_factura
  ↓
DAO de factura
  ↓
LineaFactura
  ↓
Controller
  ↓
Vista de factura

10. LineaVenta.java

Paquete: org.kt.model
Capa arquitectónica: Modelo auxiliar.
Responsabilidad única: Representar temporalmente un libro y su cantidad durante el proceso de venta antes de guardar el detalle en la base de datos.
Entradas: Objeto Libro y cantidad.
Salidas: ISBN, título, precio y subtotal calculado.
Dependencias directas: Libro.

Flujo del dato:

Libro seleccionado + cantidad
  ↓
LineaVenta
  ↓
getSubtotal()
  ↓
Pantalla de venta
  ↓
DetalleVenta al confirmar

11. Usuario.java

Paquete: org.kt.model
Capa arquitectónica: Modelo.
Responsabilidad única: Representar la información de un usuario del sistema y su rol.
Entradas: ID, username, email, nombre, apellido, hash de contraseña, rol, estado y fecha de creación.
Salidas: Datos del usuario; toString() devuelve el username.
Dependencias directas: java.sql.Timestamp para la fecha de creación.

Flujo del dato:

Login / registro / MySQL
  ↓
Usuario
  ↓
SesionContext
  ↓
Principal.rutaDashboardSegunRol()
  ↓
Dashboard correspondiente

12. Venta.java

Paquete: org.kt.model
Capa arquitectónica: Modelo.
Responsabilidad única: Representar los datos generales de una venta.
Entradas: Número de venta, fecha, total, CUI del cliente e ID del usuario.
Salidas: Información de encabezado de una venta.
Dependencias directas: Se relaciona conceptualmente con Cliente, Usuario y DetalleVenta.

Flujo del dato:

Cliente + Usuario + líneas de venta
  ↓
Venta
  ↓
DAO
  ↓
MySQL

13. Conexion.java

Paquete: org.kt.util
Capa arquitectónica: Utilidad / infraestructura de persistencia.
Responsabilidad única: Cargar la configuración de conexión y proporcionar conexiones JDBC nuevas hacia MySQL.
Entradas: Propiedades db.url, db.user y db.password obtenidas desde /db.properties.
Salidas: Objeto java.sql.Connection.
Dependencias directas: JDBC, DriverManager, driver com.mysql.cj.jdbc.Driver, Properties e InputStream.
Patrón observado: Singleton mediante constructor privado y getInstancia().

Flujo del dato:

db.properties
  ↓
Conexion()
  ↓
getInstancia()
  ↓
conectar()
  ↓
DriverManager
  ↓
MySQL

14. SecurityUtil.java

Paquete: org.kt.util
Capa arquitectónica: Utilidad.
Responsabilidad única: Generar un hash SHA-256 a partir de una contraseña.
Entradas: Contraseña en formato String.
Salidas: Cadena hexadecimal con el hash SHA-256.
Dependencias directas: MessageDigest, NoSuchAlgorithmException y StandardCharsets.UTF_8.

Flujo del dato:

Contraseña escrita por el usuario
  ↓
SecurityUtil.hashSHA256()
  ↓
MessageDigest SHA-256
  ↓
Hash hexadecimal
  ↓
Registro / comparación en base de datos

Mapa general de arquitectura observado

                      ┌─────────────────┐
                      │     Usuario     │
                      └────────┬────────┘
                               │
                               ▼
                      ┌─────────────────┐
                      │   Vista FXML    │
                      └────────┬────────┘
                               │ eventos
                               ▼
                      ┌─────────────────┐
                      │   Controller    │
                      └───────┬─────────┘
                              │
                ┌─────────────┴─────────────┐
                ▼                           ▼
       ┌─────────────────┐         ┌─────────────────┐
       │      Model      │         │       DAO       │
       │ Libro, Cliente, │         │ acceso a datos  │
       │ Venta, etc.     │         └────────┬────────┘
       └─────────────────┘                  │
                                            ▼
                                   ┌─────────────────┐
                                   │ Conexion + JDBC │
                                   └────────┬────────┘
                                            │
                                            ▼
                                   ┌─────────────────┐
                                   │      MySQL      │
                                   └─────────────────┘

En la Semana 1 el proyecto se concentra principalmente en comprender y reconstruir el Modelo. Las capas DAO, Controller y Vista se completan en las semanas siguientes.

Relación entre las clases principales del modelo

Categoria ────────┐
                  │ idCategoria
                  ▼
               Libro ◄──────── Editorial
                 │                nitEditorial
                 │
        ┌────────┴────────┐
        │                 │
        ▼                 ▼
   AutorLibro         LineaVenta
        ▲                 │
        │                 ▼
      Autor          DetalleVenta
                         │
                         ▼
                       Venta
                      /     \
                     ▼       ▼
                 Cliente   Usuario