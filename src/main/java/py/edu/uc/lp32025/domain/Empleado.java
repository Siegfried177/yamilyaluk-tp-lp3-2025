package py.edu.uc.lp32025.domain;

import py.edu.uc.lp32025.mapeable.Avatar;
import py.edu.uc.lp32025.mapeable.Mapeable;
import py.edu.uc.lp32025.mapeable.PosicionGPS;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Empleado extends Persona implements Mapeable {

    private PosicionGPS posicionGPS;
    private Avatar avatar;

    // Constructor vacío para JPA o pruebas
    public Empleado() {
        super();
        this.setNombre("Empleado");
        this.setApellido("Mock");
        this.setNumeroDocumento("MOCK001");
        this.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        this.posicionGPS = new PosicionGPS(-25.2637, -57.5759);
        this.avatar = new Avatar("https://mock.example.com/empleado_default.jpg", "Empleado_MOCK_" + this.getNumeroDocumento());
    }

    // Constructor completo
    public Empleado(String nombre, String apellido, String numeroDocumento, LocalDate fechaNacimiento,
                    PosicionGPS posicionGPS, Avatar avatar) {
        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.posicionGPS = posicionGPS;
        this.avatar = avatar;
    }

    public Empleado(String nombre, String apellido, String numeroDocumento, LocalDate fechaNacimiento) {
        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.posicionGPS = null;
        this.avatar = null;
    }

    // --- Implementación abstracta de Persona ---
    @Override
    public BigDecimal calcularSalario() {
        return new BigDecimal("5000000.00"); // Valor por defecto
    }

    @Override
    protected BigDecimal calcularDeducciones() {
        return new BigDecimal("500000.00"); // Valor por defecto
    }

    @Override
    public boolean validarDatosEspecificos() {
        return true; // Validación básica por defecto
    }

    @Override
    public BigDecimal getSalario() {
        return calcularSalario();
    }

    // --- Implementación de Mapeable ---
    @Override
    public PosicionGPS ubicarElemento() {
        return posicionGPS;
    }

    @Override
    public Avatar obtenerImagen() {
        return avatar;
    }

    public String getDisplaySummary() {
        String coord = (posicionGPS == null) ? "sin-pos" : String.format("lat=%.6f,lng=%.6f",
                posicionGPS.getLatitud(), posicionGPS.getLongitud());
        String nick = (avatar == null || avatar.getNick() == null) ? "no-nick" : avatar.getNick();
        return String.format("%s @ %s", nick, coord);
    }

    // --- Getters y Setters ---
    public PosicionGPS getPosicionGPS() {
        return posicionGPS;
    }

    public void setPosicionGPS(PosicionGPS posicionGPS) {
        this.posicionGPS = posicionGPS;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
