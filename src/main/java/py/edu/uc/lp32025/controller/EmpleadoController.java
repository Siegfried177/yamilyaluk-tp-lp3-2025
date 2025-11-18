package py.edu.uc.lp32025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.dto.BatchEmpleadosRequest;
import py.edu.uc.lp32025.service.ContratistaService;
import py.edu.uc.lp32025.service.EmpleadoPorHoraService;
import py.edu.uc.lp32025.service.EmpleadoTiempoCompletoService;

@RestController
@RequestMapping("/api/empleados") // Ruta base específica para empleados
public class EmpleadoController extends BaseController {

    private final EmpleadoTiempoCompletoService empleadoTCService;
    private final EmpleadoPorHoraService empleadoPHService;
    private final ContratistaService contratistaService;

    public EmpleadoController(EmpleadoTiempoCompletoService empleadoTCService, EmpleadoPorHoraService empleadoPHService, ContratistaService contratistaService) {
        this.empleadoTCService = empleadoTCService;
        this.empleadoPHService = empleadoPHService;
        this.contratistaService = contratistaService;
    }

    /**
     * POST /api/empleados/batch
     * Hereda: GET /api/empleados/status (si BaseController no tiene @RequestMapping)
     * o GET /status (si BaseController tuviera @RequestMapping("/api"))
     */
    @PostMapping("/batch")
    public ResponseEntity<?> guardarBatch(@RequestBody BatchEmpleadosRequest request) {
        // Lógica de guardar en batch
        // ...
        return ResponseEntity.ok("Batch de empleados procesado.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEmpleadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok("Detalles del empleado ID: " + id);
    }
}