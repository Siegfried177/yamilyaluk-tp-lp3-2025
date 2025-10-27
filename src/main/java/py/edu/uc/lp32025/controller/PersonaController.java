package py.edu.uc.lp32025.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.service.RemuneracionesService;
import py.edu.uc.lp32025.dto.ResponseDTO;
import py.edu.uc.lp32025.dto.PersonaDTO;
import py.edu.uc.lp32025.service.PersonaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final RemuneracionesService remuneracionesService;
    private final PersonaService personaService;

    public PersonaController(PersonaService personaService, RemuneracionesService remuneracionesService) {
        this.remuneracionesService = remuneracionesService;
        this.personaService = personaService;
    }

    // GET -> listar todos los empleados de la jerarquía
    @GetMapping("/listar-todos")
    public ResponseEntity<List<Persona>> getAllEmpleados() {
        return ResponseEntity.ok(remuneracionesService.listarTodosLosEmpleados());
    }

    // Filtrar por nombre (case insensitive)
    @GetMapping
    public ResponseEntity<List<Persona>> buscarPorNombre(@RequestParam(required = false) String nombre) {
        List<Persona> personas = personaService.buscarPorNombre(nombre);
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/reporte-polimorfismo")
    public ResponseEntity<String> reportePolimorfismo() {
        remuneracionesService.generarReportePolimorfismo();
        return ResponseEntity.ok("Reporte de polimorfismo generado. Revisa la consola del servidor.");
    }

    // GET -> calcular nómina total por tipo de empleado
    @GetMapping("/nomina")
    public ResponseEntity<ResponseDTO<Map<String, BigDecimal>>> obtenerNomina() {
        Map<String, BigDecimal> nominaTotal = remuneracionesService.calcularNominaTotal();
        ResponseDTO<Map<String, BigDecimal>> response = new ResponseDTO<>(
                200,
                "Nómina total calculada correctamente",
                "Cálculo exitoso",
                nominaTotal
        );
        return ResponseEntity.ok(response);
    }

    // GET -> obtener empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PersonaDTO>> getEmpleadoById(@PathVariable Long id) {
        return remuneracionesService.listarTodosLosEmpleados().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(p -> {
                    PersonaDTO dto = new PersonaDTO(
                            p.getId(),
                            p.getNombre(),
                            p.getApellido(),
                            p.getNumeroDocumento(),
                            p.getFechaNacimiento()
                    );
                    ResponseDTO<PersonaDTO> response = new ResponseDTO<>(
                            200,
                            "Empleado encontrado correctamente",
                            "Empleado encontrado",
                            dto
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>(404, "No existe empleado con ID " + id, "Empleado no encontrado", null)));
    }
}
