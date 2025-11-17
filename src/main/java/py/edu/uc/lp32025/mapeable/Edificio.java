package py.edu.uc.lp32025.mapeable;

import java.util.Objects;

public class Edificio implements Mapeable {

    private final String nombre;
    private final String direccion;
    private final PosicionGPS posicion;
    private final Avatar avatar; // imagen representativa

    public Edificio(String nombre, String direccion, PosicionGPS posicion, Avatar avatar) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.posicion = posicion;
        this.avatar = avatar;
    }

    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }

    @Override
    public PosicionGPS ubicarElemento() { return posicion; }

    @Override
    public Avatar obtenerImagen() { return avatar; }

    @Override
    public String toString() {
        return "Edificio{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", posicion=" + posicion +
                ", avatar=" + avatar +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edificio)) return false;
        Edificio edificio = (Edificio) o;
        return Objects.equals(nombre, edificio.nombre) &&
                Objects.equals(direccion, edificio.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, direccion);
    }
}
