package py.edu.uc.lp32025.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.service.PersonaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;

    // Inyección por constructor en lugar de @Autowired en campo
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    // GET -> listar todas las personas
    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        List<Persona> personas = personaService.findAll();
        return ResponseEntity.ok(personas);
    }

    // GET -> obtener persona por ID
    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Optional<Persona> persona = personaService.findById(id);
        return persona.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST -> crear persona
    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        Persona newPersona = personaService.save(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPersona);
    }

    // PUT -> actualizar persona
    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id,
                                                 @RequestBody Persona personaDetails) {
        Persona updatedPersona = personaService.update(id, personaDetails);
        if (updatedPersona != null) {
            return ResponseEntity.ok(updatedPersona);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE -> eliminar persona
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        if (personaService.deleteById(id)) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build();  // 404
        }
    }
}
