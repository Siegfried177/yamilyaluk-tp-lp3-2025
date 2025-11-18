package py.edu.uc.lp32025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.service.GerenteService;
import py.edu.uc.lp32025.exception.EmpleadoNoEncontradoException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/gerentes") // Ruta base específica para gerentes
public class GerenteController extends BaseController {

    private final GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    /**
     * POST /api/gerentes/{gerenteId}/autorizar?subordinadoId={id}&comentario={cmt}
     * Permite a un Gerente autorizar o denegar un permiso de un subordinado.
     * Las excepciones (EmpleadoNoEncontradoException, PermisoNoConcedidoException) son manejadas por el GlobalExceptionHandler.
     */
    @PostMapping("/{gerenteId}/autorizar")
    public ResponseEntity<String> autorizarPermiso(
            @PathVariable Long gerenteId,
            @RequestParam Long subordinadoId,
            @RequestParam String comentario)
            throws EmpleadoNoEncontradoException, PermisoNoConcedidoException {

        boolean autorizado = gerenteService.autorizarPermiso(gerenteId, subordinadoId, comentario);

        String mensaje = autorizado
                ? String.format("Gerente ID %d autorizó el permiso para Subordinado ID %d.", gerenteId, subordinadoId)
                : String.format("Gerente ID %d DENEGÓ el permiso para Subordinado ID %d.", gerenteId, subordinadoId);

        return ResponseEntity.ok(mensaje);
    }

    /**
     * POST /api/gerentes/{gerenteId}/solicitar-vacaciones
     * Permite al Gerente solicitar sus propias vacaciones usando su lógica especial.
     */
    @PostMapping("/{gerenteId}/solicitar-vacaciones")
    public ResponseEntity<String> solicitarVacacionesGerente(
            @PathVariable Long gerenteId,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin)
            throws EmpleadoNoEncontradoException, PermisoNoConcedidoException, DiasInsuficientesException {

        gerenteService.solicitarVacacionesGerente(gerenteId, fechaInicio, fechaFin);

        return ResponseEntity.ok("Vacaciones solicitadas correctamente para Gerente ID " + gerenteId + " bajo reglas especiales.");
    }

    /**
     * Sobrescritura del método de BaseController (opcional)
     * GET /api/gerentes/status
     */
    @Override
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Servicio Gerentes: Listo para producción.");
    }
}