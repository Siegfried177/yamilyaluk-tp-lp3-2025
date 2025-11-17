package py.edu.uc.lp32025.domain;

import java.util.Objects;

public final class PosicionGPS {
    private final double latitud;
    private final double longitud;

    public PosicionGPS(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    @Override
    public String toString() {
        return "PosicionGPS{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PosicionGPS that = (PosicionGPS) o;
        return Double.compare(that.latitud, latitud) == 0 &&
                Double.compare(that.longitud, longitud) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitud, longitud);
    }
}
