package py.edu.uc.lp32025.mapeable;

import java.util.Objects;

public class Vehiculo implements Mapeable {

    private final String placa;
    private final String modelo;
    private final PosicionGPS posicion;
    private final Avatar avatar; // por ejemplo: foto del vehiculo y apodo

    public Vehiculo(String placa, String modelo, PosicionGPS posicion, Avatar avatar) {
        this.placa = placa;
        this.modelo = modelo;
        this.posicion = posicion;
        this.avatar = avatar;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    @Override
    public PosicionGPS ubicarElemento() {
        return posicion;
    }

    @Override
    public Avatar obtenerImagen() {
        return avatar;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", posicion=" + posicion +
                ", avatar=" + avatar +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehiculo)) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(placa, vehiculo.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa);
    }
}
