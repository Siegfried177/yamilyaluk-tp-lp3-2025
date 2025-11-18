package py.edu.uc.lp32025.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus; // Necesario para ResponseEntity.status(HttpStatus.CREATED)
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.dto.SolicitudDiasDto;
import py.edu.uc.lp32025.dto.EmpleadoCreationDto; // <-- ¡Nuevo DTO para creación genérica!
import py.edu.uc.lp32025.dto.PersonaResponseDto; // <-- DTO de respuesta que devuelve el servicio
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.service.NominaService;

@RestController
@RequestMapping("/api/nomina")
@Slf4j
public class NominaController {

    private final NominaService nominaService;

    public NominaController(NominaService nominaService) {
        this.nominaService = nominaService;
    }

    // -----------------------------------------------------------------------
    // 🆕 ENDPOINT GENÉRICO DE CREACIÓN (POST /api/nomina)
    // -----------------------------------------------------------------------
    /**
     * POST /api/nomina
     * Crea cualquier tipo de Empleado (Gerente, Empleado T. Completo, etc.)
     * La lógica de instanciación se delega al NominaService (patrón Factoría).
     */
    @PostMapping
    public ResponseEntity<PersonaResponseDto> crearEmpleadoGenerico(@RequestBody EmpleadoCreationDto dto) {

        // El servicio recibe el DTO genérico y devuelve la entidad creada
        PersonaResponseDto nuevaPersonaDto = nominaService.crearPersona(dto);

        // Retorna HTTP 201 Created (El código estándar para la creación exitosa de un recurso)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPersonaDto);
    }
    // -----------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponseDto> obtenerPersonaPorId(@PathVariable Long id) {
        PersonaResponseDto dto = nominaService.obtenerPersonaPorId(id);
        return ResponseEntity.ok(dto);
    }

    // ---------------------------------------------------
    // 🆕 ENDPOINT DE ACTUALIZACIÓN (PUT /api/nomina/{id})
    // ---------------------------------------------------
    // Usamos EmpleadoCreationDto como DTO de actualización para simplificar
    @PutMapping("/{id}")
    public ResponseEntity<PersonaResponseDto> actualizarPersona(
            @PathVariable Long id,
            @RequestBody EmpleadoCreationDto dto) {

        PersonaResponseDto dtoActualizado = nominaService.actualizarPersona(id, dto);
        return ResponseEntity.ok(dtoActualizado);
    }

    // ---------------------------------------------------
    // 🆕 ENDPOINT DE BORRADO (DELETE /api/nomina/{id})
    // ---------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        nominaService.eliminarPersona(id);
        // Retorna HTTP 204 No Content
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para solicitar vacaciones.
     * El manejo de errores es delegado al GlobalExceptionHandler.
     */
    @PostMapping("/solicitarVacaciones")
    public ResponseEntity<String> solicitarVacaciones(@RequestBody SolicitudDiasDto dto)
            throws PermisoNoConcedidoException, DiasInsuficientesException {

        nominaService.solicitarVacaciones(dto.getEmpleadoId(), dto.getFechaInicio(), dto.getFechaFin());

        return ResponseEntity.ok("Vacaciones solicitadas correctamente.");
    }

    /**
     * Endpoint para solicitar permiso especial.
     * El manejo de errores es delegado al GlobalExceptionHandler.
     */
    @PostMapping("/solicitarPermisoEspecial")
    public ResponseEntity<String> solicitarPermisoEspecial(@RequestBody SolicitudDiasDto dto)
            throws PermisoNoConcedidoException {

        nominaService.solicitarPermisoEspecial(dto.getEmpleadoId(), dto.getFechaInicio(), dto.getFechaFin(), dto.getMotivo());

        return ResponseEntity.ok("Permiso especial solicitado correctamente.");
    }

    /**
     * Endpoint para obtener la nómina completa.
     */
    @GetMapping("/completa")
    public ResponseEntity<?> obtenerNominaCompleta() {
        return ResponseEntity.ok(nominaService.obtenerNominaCompleta());
    }
}