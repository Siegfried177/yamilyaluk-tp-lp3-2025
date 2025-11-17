package py.edu.uc.lp32025.domain;

// Nota: No hereda de Persona, pero sí implementa Mapeable.

import lombok.Getter;
import lombok.Setter;
import py.edu.uc.lp32025.interfaces.Mapeable;

public class Vehiculo implements Mapeable {

    // Getters y Setters (simplificados)
    @Getter
    private String placa;
    private String modelo;
    @Setter
    private PosicionGPS ubicacionActual;
    private String urlIcono;

    public Vehiculo(String placa, String modelo, PosicionGPS ubicacionActual, String urlIcono) {
        this.placa = placa;
        this.modelo = modelo;
        this.ubicacionActual = ubicacionActual;
        this.urlIcono = urlIcono;
    }

    // =========================================================
    // IMPLEMENTACIÓN DE MAPEABLE
    // =========================================================

    @Override
    public PosicionGPS ubicarElemento() {
        return this.ubicacionActual;
    }

    @Override
    public Avatar obtenerImagen() {
        // Usamos la placa como "nick" y el icono como "imagen"
        return new Avatar(this.urlIcono, this.placa + " (" + this.modelo + ")");
    }
}