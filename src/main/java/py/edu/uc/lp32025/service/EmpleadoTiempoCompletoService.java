package py.edu.uc.lp32025.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.repository.EmpleadoTiempoCompletoRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmpleadoTiempoCompletoService {

    private final EmpleadoTiempoCompletoRepository repository;

    public EmpleadoTiempoCompletoService(EmpleadoTiempoCompletoRepository repository) {
        this.repository = repository;
    }

    // ------------------------
    // MÉTODOS CRUD
    // ------------------------
    public List<EmpleadoTiempoCompleto> findAll() {
        log.info("Buscando todos los empleados de tiempo completo...");
        List<EmpleadoTiempoCompleto> empleados = repository.findAll();
        log.debug("Se encontraron {} empleados", empleados.size());
        return empleados;
    }

    public Optional<EmpleadoTiempoCompleto> findById(Long id) {
        log.info("Buscando empleado por ID: {}", id);
        return repository.findById(id);
    }

    public EmpleadoTiempoCompleto save(EmpleadoTiempoCompleto empleado) {
        log.info("Guardando empleado: {}", empleado.obtenerInformacionCompleta());
        return repository.save(empleado);
    }

    public void deleteById(Long id) {
        log.warn("Eliminando empleado con ID: {}", id);
        repository.deleteById(id);
    }

    public List<EmpleadoTiempoCompleto> findByDepartamento(String departamento) {
        log.info("Buscando empleados por departamento: {}", departamento);
        return repository.findByDepartamento(departamento);
    }

    // ------------------------
    // MÉTODO DE BATCH
    // ------------------------
    @Transactional
    public List<EmpleadoTiempoCompleto> guardarEmpleadosEnBatch(List<EmpleadoTiempoCompleto> empleados) {
        final int CHUNK_SIZE = 100;
        log.info("Guardando {} empleados en batch...", empleados.size());

        int total = empleados.size();
        for (int i = 0; i < total; i += CHUNK_SIZE) {
            int end = Math.min(i + CHUNK_SIZE, total);
            List<EmpleadoTiempoCompleto> chunk = empleados.subList(i, end);
            log.debug("Procesando lote de empleados del índice {} al {}", i, end);

            for (EmpleadoTiempoCompleto e : chunk) {
                if (!e.validarDatosEspecificos()) {
                    log.error("Empleado inválido detectado: {}", e.obtenerInformacionCompleta());
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Empleado inválido: " + e.obtenerInformacionCompleta()
                    );
                }
                repository.save(e);
            }
        }
        log.info("Todos los empleados fueron guardados correctamente.");
        return empleados;
    }

    // ------------------------
    // MÉTODO DE IMPUESTOS POR ID
    // ------------------------
    public Map<String, Object> calcularImpuestosPorId(Long id) {
        log.info("Calculando impuestos para empleado con ID: {}", id);

        return repository.findById(id)
                .map(empleado -> {
                    BigDecimal salario = empleado.calcularSalario();
                    BigDecimal deducciones = empleado.calcularDeducciones();
                    BigDecimal impuestoBase = salario.multiply(BigDecimal.valueOf(0.10));
                    BigDecimal impuestoTotal = empleado.calcularImpuestos();

                    Map<String, Object> response = new HashMap<>();
                    response.put("id", empleado.getId());
                    response.put("nombre", empleado.getNombre() + " " + empleado.getApellido());
                    response.put("departamento", empleado.getDepartamento());
                    response.put("salarioNeto", salario);
                    response.put("deducciones", deducciones);
                    response.put("impuestoBase", impuestoBase);
                    response.put("impuestoTotal", impuestoTotal);
                    response.put("informacionCompleta", empleado.obtenerInformacionCompleta());
                    response.put("validacion", empleado.validarDatosEspecificos());

                    log.debug("Cálculo de impuestos completo: {}", response);
                    return response;
                })
                .orElseThrow(() -> {
                    log.error("Empleado no encontrado con ID: {}", id);
                    return new IllegalArgumentException("Empleado no encontrado con id: " + id);
                });
    }

    // ------------------------
    // 4.2 CÁLCULO DE NÓMINA TOTAL
    // ------------------------
    public Map<String, BigDecimal> calcularNominaTotal() {
        log.info("Calculando nómina total...");
        List<EmpleadoTiempoCompleto> empleados = repository.findAll();

        Map<String, BigDecimal> nominaPorTipo = empleados.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getClass().getSimpleName(),
                        Collectors.mapping(
                                EmpleadoTiempoCompleto::calcularSalario,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        log.info("Nómina total calculada: {}", nominaPorTipo);
        return nominaPorTipo;
    }
}
