package org.kt.model;

/**
 * Representa a un autor dentro del sistema LibraryApp.
 * Contiene la información personal y descriptiva del autor.
 */
public class Autor {

    private int idAutor;
    private String nombreAutor;
    private String apellidoAutor;
    private String nacionalidad;
    private String biografia;

    /**
     * Constructor vacío de la clase Autor.
     */
    public Autor() {
    }

    /**
     * Constructor que inicializa todos los atributos del autor.
     *
     * @param idAutor identificador único del autor
     * @param nombreAutor nombre del autor
     * @param apellidoAutor apellido del autor
     * @param nacionalidad nacionalidad del autor
     * @param biografia biografía del autor
     */
    public Autor(int idAutor, String nombreAutor, String apellidoAutor,
                 String nacionalidad, String biografia) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
        this.apellidoAutor = apellidoAutor;
        this.nacionalidad = nacionalidad;
        this.biografia = biografia;
    }

    /**
     * Obtiene el identificador del autor.
     *
     * @return identificador del autor
     */
    public int getIdAutor() {
        return idAutor;
    }

    /**
     * Establece el identificador del autor.
     *
     * @param idAutor nuevo identificador del autor
     */
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    /**
     * Obtiene el nombre del autor.
     *
     * @return nombre del autor
     */
    public String getNombreAutor() {
        return nombreAutor;
    }

    /**
     * Establece el nombre del autor.
     *
     * @param nombreAutor nuevo nombre del autor
     */
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    /**
     * Obtiene el apellido del autor.
     *
     * @return apellido del autor
     */
    public String getApellidoAutor() {
        return apellidoAutor;
    }

    /**
     * Establece el apellido del autor.
     *
     * @param apellidoAutor nuevo apellido del autor
     */
    public void setApellidoAutor(String apellidoAutor) {
        this.apellidoAutor = apellidoAutor;
    }

    /**
     * Obtiene la nacionalidad del autor.
     *
     * @return nacionalidad del autor
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Establece la nacionalidad del autor.
     *
     * @param nacionalidad nueva nacionalidad del autor
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Obtiene la biografía del autor.
     *
     * @return biografía del autor
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * Establece la biografía del autor.
     *
     * @param biografia nueva biografía del autor
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * Devuelve el nombre completo del autor.
     *
     * @return nombre y apellido del autor
     */
    @Override
    public String toString() {
        return nombreAutor + " " + apellidoAutor;
    }
}