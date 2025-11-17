package py.edu.uc.lp32025.mapeable;

import py.edu.uc.lp32025.domain.Empleado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class MapeableDataFactory {

    private MapeableDataFactory() { /* utilidad estática */ }

    public static List<Mapeable> crearListaEjemplo() {
        List<Mapeable> list = new ArrayList<>();

        // Empleados
        PosicionGPS posEmp1 = new PosicionGPS(-25.2637, -57.5759); // Asunción ejemplo
        Avatar avEmp1 = new Avatar("https://example.com/avatars/juan.png", "juan.perez");
        Empleado emp1 = new Empleado(
                "Juan",
                "Pérez",
                "DOC001",
                LocalDate.of(1990, 5, 20),
                posEmp1,
                avEmp1
        );
        emp1.setId(1L);
        list.add(emp1);

        PosicionGPS posEmp2 = new PosicionGPS(-34.6037, -58.3816); // Buenos Aires ejemplo
        Avatar avEmp2 = new Avatar("https://example.com/avatars/maria.png", "maria.g");
        Empleado emp2 = new Empleado(
                "María",
                "González",
                "DOC002",
                LocalDate.of(1985, 9, 10),
                posEmp2,
                avEmp2
        );
        emp2.setId(2L);
        list.add(emp2);

        // Vehículos
        PosicionGPS posVeh = new PosicionGPS(-25.2866, -57.6359);
        Avatar avVeh = new Avatar("https://example.com/vehicles/truck1.png", "truck-01");
        Vehiculo v = new Vehiculo("ABC-123", "Ford Transit", posVeh, avVeh);
        list.add(v);

        // Edificios
        PosicionGPS posEd = new PosicionGPS(-25.2900, -57.6100);
        Avatar avEd = new Avatar("https://example.com/buildings/office.png", "HQ");
        Edificio ed = new Edificio("Headquarters", "Av. Principal 1234", posEd, avEd);
        list.add(ed);

        return list;
    }
}
