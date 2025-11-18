package py.edu.uc.lp32025.controller;

import lombok.extern.slf4j.Slf4j; // ⬅️ Logger de Lombok
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.dto.SolicitudPermisoRequest;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.service.PermisosService;
import jakarta.validation.Valid;

@Slf4j // Usando Lombok para el logger
@RestController
@RequestMapping("/api/permisos")
public class PermisosController {

    private final PermisosService permisosService;

    public PermisosController(PermisosService permisosService) {
        this.permisosService = permisosService;
    }

    // Mapeamos a 202 ACCEPTED ya que la solicitud es asíncrona en RRHH.
    @PostMapping("/{empleadoId}/vacaciones")
    public ResponseEntity<String> solicitarVacaciones(
            @PathVariable Long empleadoId,
            @Valid @RequestBody SolicitudPermisoRequest request) {

        log.info("POST /api/permisos/{}/vacaciones recibido. Solicitando de {} a {}",
                empleadoId, request.getFechaInicio(), request.getFechaFin());

        try {
            permisosService.procesarSolicitudVacaciones(
                    empleadoId,
                    request.getFechaInicio(),
                    request.getFechaFin());

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Solicitud de vacaciones enviada y aprobada provisionalmente para el empleado " + empleadoId);

        } catch (PermisoNoConcedidoException e) {
            log.warn("Solicitud rechazada para Empleado ID {}: {}", empleadoId, e.getMessage());
            // Usamos 400 Bad Request para indicar un fallo de negocio en la solicitud.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DiasInsuficientesException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{empleadoId}/especial")
    public ResponseEntity<String> solicitarPermisoEspecial(
            @PathVariable Long empleadoId,
            @Valid @RequestBody SolicitudPermisoRequest request) {

        log.info("POST /api/permisos/{}/especial recibido. Motivo: {}", empleadoId, request.getMotivo());

        try {
            permisosService.procesarSolicitudPermisoEspecial(
                    empleadoId,
                    request.getFechaInicio(),
                    request.getFechaFin(),
                    request.getMotivo());

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Solicitud de permiso especial enviada y aprobada para el empleado " + empleadoId);

        } catch (PermisoNoConcedidoException e) {
            log.warn("Solicitud rechazada para Empleado ID {}: {}", empleadoId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}