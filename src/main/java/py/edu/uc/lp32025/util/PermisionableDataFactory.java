package py.edu.uc.lp32025.util;

import lombok.extern.slf4j.Slf4j;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.domain.Gerente; // ⬅️ Nuevo: Importar Gerente
import py.edu.uc.lp32025.interfaces.Permisionable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PermisionableDataFactory {

    public static List<Permisionable> getEmpleadosPermisionables() {
        List<Permisionable> permisionables = new ArrayList<>();

        log.info("Cargando entidades Permisionables de prueba...");

        // Empleado ID 1 (EmpleadoTiempoCompleto): 2 años de antigüedad
        EmpleadoTiempoCompleto empleado1 = new EmpleadoTiempoCompleto();
        empleado1.setId(1L);
        empleado1.setNombre("Ana");
        empleado1.setApellido("Suarez");
        empleado1.setFechaContratacion(LocalDate.now().minusYears(2));
        permisionables.add(empleado1);

        // Empleado ID 2 (EmpleadoTiempoCompleto): 6 meses de antigüedad
        EmpleadoTiempoCompleto empleado2 = new EmpleadoTiempoCompleto();
        empleado2.setId(2L);
        empleado2.setNombre("Javier");
        empleado2.setApellido("Perez");
        empleado2.setFechaContratacion(LocalDate.now().minusMonths(6));
        permisionables.add(empleado2);

        // ⬅️ Nuevo: Gerente ID 3 (Gerente): 6 años de antigüedad (cumple > 5 años)
        Gerente gerente3 = new Gerente();
        gerente3.setId(3L);
        gerente3.setNombre("Carlos");
        gerente3.setApellido("Gomez");
        gerente3.setFechaContratacion(LocalDate.now().minusYears(6)); // Antigüedad > 5 años
        gerente3.setAreaResponsabilidad("Sistemas");
        permisionables.add(gerente3);


        log.info("Se cargaron {} entidades Permisionables (incluyendo un Gerente).", permisionables.size());
        return permisionables;
    }
}