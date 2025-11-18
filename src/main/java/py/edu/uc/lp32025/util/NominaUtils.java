package py.edu.uc.lp32025.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import py.edu.uc.lp32025.domain.Empleado;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.domain.Persona;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class NominaUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    // Private constructor to prevent instantiation
    private NominaUtils() { }

    /**
     * Computes the total number of requested days from all employees.
     */
    public static int computeTotalRequestedDays(List<? extends Persona> employees) {
        if (employees == null || employees.isEmpty()) return 0;

        return employees.stream()
                .filter(e -> e instanceof EmpleadoTiempoCompleto)
                .map(e -> (EmpleadoTiempoCompleto) e)
                .mapToInt(EmpleadoTiempoCompleto::getDiasSolicitados)
                .sum();
    }

    /**
     * Generates a JSON report of employees whose requested days exceed the given threshold.
     */
    List<Map<String, Object>> generateExceedReport(List<Empleado> empleados, int minDias) {
        return empleados.stream()
                .filter(e -> e.getDiasSolicitados() > minDias)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", e.getId());
                    map.put("nombre", e.getNombre() + " " + e.getApellido());
                    map.put("diasSolicitados", e.getDiasSolicitados());
                    return map;
                })
                .collect(Collectors.toList());
    }

}
