package py.edu.uc.lp32025.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.repository.EmpleadoTiempoCompletoRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return repository.findAll();
    }

    public Optional<EmpleadoTiempoCompleto> findById(Long id) {
        return repository.findById(id);
    }

    public EmpleadoTiempoCompleto save(EmpleadoTiempoCompleto empleado) {
        if (!empleado.validarDatosEspecificos()) {
            throw new IllegalArgumentException("Empleado inválido: " + empleado.obtenerInformacionCompleta());
        }
        return repository.save(empleado);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<EmpleadoTiempoCompleto> findByDepartamento(String departamento) {
        return repository.findByDepartamento(departamento);
    }

    // ------------------------
    // MÉTODO DE BATCH
    // ------------------------
    @Transactional
    public List<EmpleadoTiempoCompleto> guardarEmpleadosEnBatch(List<EmpleadoTiempoCompleto> empleados) {
        final int CHUNK_SIZE = 100;
        int total = empleados.size();
        for (int i = 0; i < total; i += CHUNK_SIZE) {
            int end = Math.min(i + CHUNK_SIZE, total);
            List<EmpleadoTiempoCompleto> chunk = empleados.subList(i, end);
            for (EmpleadoTiempoCompleto e : chunk) {
                // Validación polimórfica usando método de la clase Persona
                if (!e.validarDatosEspecificos()) {
                    throw new IllegalArgumentException(
                            "Empleado inválido en batch: " + e.obtenerInformacionCompleta()
                    );
                }
                repository.save(e);
            }
        }
        return empleados;
    }

    // ------------------------
    // CÁLCULO DE IMPUESTOS POR ID
    // ------------------------
    public Map<String, Object> calcularImpuestosPorId(Long id) {
        return repository.findById(id)
                .map(empleado -> {
                    BigDecimal salario = empleado.calcularSalario();
                    BigDecimal deducciones = empleado.calcularDeducciones();
                    BigDecimal impuestoBase = empleado.calcularImpuestoBase(salario);
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

                    return response;
                })
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con id: " + id));
    }
}
