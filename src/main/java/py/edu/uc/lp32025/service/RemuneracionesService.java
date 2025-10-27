package py.edu.uc.lp32025.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.repository.PersonaRepository;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class RemuneracionesService {

    private final PersonaRepository personaRepository;

    public RemuneracionesService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // ------------------------------------------------------------
    // 1. Listar todos los empleados
    // ------------------------------------------------------------
    public List<Persona> listarTodosLosEmpleados() {
        log.info("Listando todos los empleados...");
        List<Persona> empleados = personaRepository.findAll();
        log.debug("Se encontraron {} empleados", empleados.size());
        return empleados;
    }

    // ------------------------------------------------------------
    // 2. Cálculo de nómina total por tipo de empleado
    // ------------------------------------------------------------
    public Map<String, BigDecimal> calcularNominaTotal() {
        log.info("Calculando nómina total por tipo de empleado...");
        List<Persona> empleados = listarTodosLosEmpleados();
        Map<String, BigDecimal> nominaPorTipo = new HashMap<>();

        for (Persona e : empleados) {
            String tipo = e.getClass().getSimpleName();
            BigDecimal salario = e.getSalario();
            nominaPorTipo.merge(tipo, salario, BigDecimal::add);
        }

        log.debug("Nómina por tipo calculada: {}", nominaPorTipo);
        return nominaPorTipo;
    }

    // ------------------------------------------------------------
    // 3. Reporte completo (para devolver como JSON)
    // ------------------------------------------------------------
    public List<Map<String, Object>> generarReporteCompleto() {
        log.info("Generando reporte completo de empleados...");
        List<Persona> empleados = listarTodosLosEmpleados();
        List<Map<String, Object>> reporte = new ArrayList<>();

        for (Persona e : empleados) {
            Map<String, Object> info = new HashMap<>();
            info.put("id", e.getId());
            info.put("tipoEmpleado", e.getClass().getSimpleName());
            info.put("informacionCompleta", e.obtenerInformacionCompleta());
            info.put("impuestos", e.calcularImpuestos());
            info.put("datosValidos", e.validarDatosEspecificos());
            reporte.add(info);
        }

        log.debug("Reporte completo generado con {} entradas", reporte.size());
        return reporte;
    }

    // ------------------------------------------------------------
    // 4. REPORTE DE POLIMORFISMO (para consola)
    // ------------------------------------------------------------
    public void generarReportePolimorfismo() {
        log.info("Generando reporte de polimorfismo...");

        List<Persona> empleados = listarTodosLosEmpleados();
        System.out.println("=== REPORTE DE POLIMORFISMO ===");

        for (Persona e : empleados) {
            System.out.println("Empleado: " + e.getNombre() + " " + e.getApellido());
            System.out.println("Tipo: " + e.getClass().getSimpleName());
            System.out.println("Información completa: " + e.obtenerInformacionCompleta());
            System.out.println("Impuestos calculados: " + e.calcularImpuestos());
            System.out.println("Datos válidos: " + e.validarDatosEspecificos());
            System.out.println("-----------------------------");
        }

        log.info("Reporte de polimorfismo generado correctamente.");
    }
}
