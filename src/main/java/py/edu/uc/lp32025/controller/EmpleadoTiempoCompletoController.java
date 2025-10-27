package py.edu.uc.lp32025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.service.EmpleadoTiempoCompletoService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados-tiempo-completo")
public class EmpleadoTiempoCompletoController {

    private final EmpleadoTiempoCompletoService empleadoService;

    public EmpleadoTiempoCompletoController(EmpleadoTiempoCompletoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoTiempoCompleto> getById(@PathVariable Long id) {
        return empleadoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmpleadoTiempoCompleto> create(@RequestBody EmpleadoTiempoCompleto empleado) {
        EmpleadoTiempoCompleto saved = empleadoService.save(empleado);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoTiempoCompleto> update(@PathVariable Long id,
                                                         @RequestBody EmpleadoTiempoCompleto empleado) {
        return empleadoService.findById(id)
                .map(existing -> {
                    empleado.setId(id);
                    EmpleadoTiempoCompleto updated = empleadoService.save(empleado);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (empleadoService.findById(id).isPresent()) {
            empleadoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/departamento/{departamento}")
    public List<EmpleadoTiempoCompleto> getByDepartamento(@PathVariable String departamento) {
        return empleadoService.findByDepartamento(departamento);
    }

    // ------------------------------------------------------------
    // NUEVO ENDPOINT: CONSULTAR IMPUESTOS DEL EMPLEADO POR ID
    // ------------------------------------------------------------
    @GetMapping("/{id}/impuesto")
    public ResponseEntity<Map<String, Object>> getImpuesto(@PathVariable Long id) {
        return empleadoService.findById(id)
                .map(empleado -> {
                    BigDecimal salario = empleado.calcularSalario();
                    BigDecimal deducciones = salario.multiply(BigDecimal.valueOf(0.05)); // según lógica en Persona
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

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoTiempoCompleto>> listarTodos() {
        List<EmpleadoTiempoCompleto> empleados = empleadoService.findAll();
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleados);
    }

    // -------------------------------
    // NUEVO ENDPOINT: CREAR VARIOS
    // -------------------------------
    @PostMapping("/batch")
    public ResponseEntity<List<EmpleadoTiempoCompleto>> createBatch(
            @RequestBody List<EmpleadoTiempoCompleto> empleados) {
        List<EmpleadoTiempoCompleto> guardados = empleadoService.guardarEmpleadosEnBatch(empleados);
        return ResponseEntity.ok(guardados);
    }
}
