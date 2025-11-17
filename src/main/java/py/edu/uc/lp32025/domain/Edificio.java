package py.edu.uc.lp32025.domain;

// Nota: No hereda de Persona, pero sí implementa Mapeable.

import py.edu.uc.lp32025.interfaces.Mapeable;

public class Edificio implements Mapeable {

    private String nombre;
    private String direccion;
    private PosicionGPS ubicacionFija;
    private String urlFotoFachada;

    public Edificio(String nombre, String direccion, PosicionGPS ubicacionFija, String urlFotoFachada) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ubicacionFija = ubicacionFija;
        this.urlFotoFachada = urlFotoFachada;
    }

    // =========================================================
    // IMPLEMENTACIÓN DE MAPEABLE
    // =========================================================

    @Override
    public PosicionGPS ubicarElemento() {
        return this.ubicacionFija;
    }

    @Override
    public Avatar obtenerImagen() {
        // Usamos el nombre del edificio como "nick" y la fachada como "imagen"
        return new Avatar(this.urlFotoFachada, this.nombre);
    }

    // Getters (simplificados)
    public String getNombre() { return nombre; }
}