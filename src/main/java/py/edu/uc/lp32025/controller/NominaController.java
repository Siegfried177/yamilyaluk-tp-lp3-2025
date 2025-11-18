package py.edu.uc.lp32025.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.dto.SolicitudDiasDto;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.service.NominaService;

import java.util.Map;

@RestController
@RequestMapping("/api/nomina")
@Slf4j
public class NominaController {

    private final NominaService nominaService;

    public NominaController(NominaService nominaService) {
        this.nominaService = nominaService;
    }

    @PostMapping("/solicitarVacaciones")
    public ResponseEntity<?> solicitarVacaciones(@RequestBody SolicitudDiasDto dto) {
        try {
            nominaService.solicitarVacaciones(dto.getEmpleadoId(), dto.getFechaInicio(), dto.getFechaFin());
            return ResponseEntity.ok("Vacaciones solicitadas correctamente.");
        } catch (PermisoNoConcedidoException | DiasInsuficientesException ex) {
            log.error("Error al solicitar vacaciones", ex);
            return ResponseEntity
                    .badRequest() // HTTP 400
                    .body(Map.of(
                            "error", ex.getClass().getSimpleName(),
                            "message", ex.getMessage()
                    ));
        }
    }

    @PostMapping("/solicitarPermisoEspecial")
    public ResponseEntity<?> solicitarPermisoEspecial(@RequestBody SolicitudDiasDto dto) {
        try {
            nominaService.solicitarPermisoEspecial(dto.getEmpleadoId(), dto.getFechaInicio(), dto.getFechaFin(), dto.getMotivo());
            return ResponseEntity.ok("Permiso especial solicitado correctamente.");
        } catch (PermisoNoConcedidoException ex) {
            log.error("Error al solicitar permiso especial", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/completa")
    public ResponseEntity<?> obtenerNominaCompleta() {
        return ResponseEntity.ok(nominaService.obtenerNominaCompleta());
    }
}
