package py.edu.uc.lp32025.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.dto.PersonaDTO;
import py.edu.uc.lp32025.dto.ReportePolimorfismoDTO;
import py.edu.uc.lp32025.dto.ResponseDTO;
import py.edu.uc.lp32025.service.PersonaService;
import py.edu.uc.lp32025.service.RemuneracionesService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;
    private final RemuneracionesService remuneracionesService;

    public PersonaController(PersonaService personaService,
                             RemuneracionesService remuneracionesService) {
        this.personaService = personaService;
        this.remuneracionesService = remuneracionesService;
    }

    /* ======== ENDPOINTS CRUD ======== */

    @GetMapping
    public ResponseEntity<List<Persona>> listar(@RequestParam(required = false) String nombre) {
        return ResponseEntity.ok(personaService.buscarPorNombre(nombre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PersonaDTO>> obtenerPorId(@PathVariable Long id) {
        Persona p = personaService.findByIdThrow(id);

        PersonaDTO dto = new PersonaDTO(
                p.getId(),
                p.getNombre(),
                p.getApellido(),
                p.getNumeroDocumento(),
                p.getFechaNacimiento()
        );

        return ResponseEntity.ok(
                new ResponseDTO<>(200, "Persona encontrada", "OK", dto)
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<Persona>> guardar(@RequestBody Persona persona) {
        Persona saved = personaService.save(persona);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO<>(201, "Persona creada correctamente", "Creado", saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Persona>> actualizar(@PathVariable Long id,
                                                           @RequestBody Persona persona) {
        Persona updated = personaService.update(id, persona);
        return ResponseEntity.ok(
                new ResponseDTO<>(200, "Persona actualizada", "OK", updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> eliminar(@PathVariable Long id) {
        personaService.delete(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(200, "Persona eliminada", "OK", null)
        );
    }

    /* ==== ENDPOINTS RELACIONADOS A REMUNERACIONES ==== */

    @GetMapping("/nomina")
    public ResponseEntity<ResponseDTO<Map<String, BigDecimal>>> obtenerNomina() {
        Map<String, BigDecimal> nomina = remuneracionesService.calcularNominaTotal();
        return ResponseEntity.ok(
                new ResponseDTO<>(200, "Nómina total calculada", "OK", nomina)
        );
    }

    @GetMapping("/reporte/polimorfismo")
    public ResponseEntity<List<ReportePolimorfismoDTO>> generarReportePolimorfismo() {
        List<ReportePolimorfismoDTO> reporte = remuneracionesService.generarReportePolimorfismo();
        return ResponseEntity.ok(reporte);
    }

}
