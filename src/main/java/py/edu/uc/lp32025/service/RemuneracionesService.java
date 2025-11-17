package py.edu.uc.lp32025.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.Contratista;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.dto.ReporteEmpleadoDto;
import py.edu.uc.lp32025.repository.PersonaRepository;
import py.edu.uc.lp32025.dto.ReportePolimorfismoDTO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<ReporteEmpleadoDto> generarReporteCompleto() {
        List<Persona> personas = personaRepository.findAll();

        return personas.stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    private ReporteEmpleadoDto convertirADto(Persona p) {
        ReporteEmpleadoDto dto = new ReporteEmpleadoDto();

        dto.setNombre(p.getNombre());
        dto.setApellido(p.getApellido());
        dto.setNumeroDocumento(p.getNumeroDocumento());
        dto.setFechaNacimiento(p.getFechaNacimiento());

        // Salario bruto polimórfico
        dto.setSalario(p.calcularSalario());

        dto.setTipoEmpleado(obtenerTipoEmpleado(p));

        return dto;
    }

    private String obtenerTipoEmpleado(Persona p) {
        if (p instanceof EmpleadoTiempoCompleto) return "Empleado Tiempo Completo";
        if (p instanceof EmpleadoPorHora) return "Empleado por Hora";
        if (p instanceof Contratista) return "Contratista";
        return "Persona";
    }

    public List<ReportePolimorfismoDTO> generarReportePolimorfismo() {
        List<Persona> empleados = listarTodosLosEmpleados();
        List<ReportePolimorfismoDTO> reporte = new ArrayList<>();

        for (Persona e : empleados) {
            boolean validacion;
            try {
                validacion = e.validarDatosEspecificos();
            } catch (Exception ex) {
                validacion = false;
            }

            reporte.add(
                    new ReportePolimorfismoDTO(
                            e.getNombre() + " " + e.getApellido(),
                            e.getClass().getSimpleName(),
                            e.obtenerInformacionCompleta(),
                            e.calcularImpuestos(),
                            validacion
                    )
            );
        }

        return reporte;
    }
}
