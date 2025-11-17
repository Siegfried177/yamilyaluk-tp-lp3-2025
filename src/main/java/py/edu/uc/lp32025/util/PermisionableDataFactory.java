package py.edu.uc.lp32025.util;

import lombok.extern.slf4j.Slf4j; // ⬅️ Nuevo: Inyecta el logger SLF4J
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.domain.Persona;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j // Usando Lombok para el logger
public class PermisionableDataFactory {

    private static final Map<Long, Persona> REPOSITORY = new HashMap<>();

    static {
        log.info("Inicializando Repositorio de Empleados de prueba para Demo Permisionable.");

        // Empleado ID 1: Antigüedad de 2 años (Derecho a 12 días)
        EmpleadoTiempoCompleto empleado1 = new EmpleadoTiempoCompleto();
        empleado1.setId(1L);
        empleado1.setNombre("Ana");
        empleado1.setApellido("Suarez");
        // Asumimos que la clase EmpleadoTiempoCompleto tiene un setter para fechaContratacion/fechaIngreso
        empleado1.setFechaContratacion(LocalDate.now().minusYears(2));
        REPOSITORY.put(1L, empleado1);

        // Empleado ID 2: Antigüedad de 6 meses (NO tiene derecho a vacaciones)
        EmpleadoTiempoCompleto empleado2 = new EmpleadoTiempoCompleto();
        empleado2.setId(2L);
        empleado2.setNombre("Javier");
        empleado2.setApellido("Perez");
        empleado2.setFechaContratacion(LocalDate.now().minusMonths(6));
        REPOSITORY.put(2L, empleado2);

        // Empleado ID 3: Para probar un permiso especial
        EmpleadoTiempoCompleto empleado3 = new EmpleadoTiempoCompleto();
        empleado3.setId(3L);
        empleado3.setNombre("Carlos");
        empleado3.setApellido("Mendez");
        empleado3.setFechaContratacion(LocalDate.now().minusYears(3));
        REPOSITORY.put(3L, empleado3);

        log.info("Se cargaron {} empleados en el Repositorio Mock.", REPOSITORY.size());
    }

    public static Map<Long, Persona> getRepository() {
        return REPOSITORY;
    }
}