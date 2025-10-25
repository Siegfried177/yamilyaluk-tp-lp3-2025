package py.edu.uc.lp32025.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.service.PersonaService;
import py.edu.uc.lp32025.dto.ResponseDTO;
import py.edu.uc.lp32025.dto.PersonaDTO;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    // GET -> listar todas las personas
    @GetMapping
    public ResponseEntity<ResponseDTO<List<PersonaDTO>>> getAllPersonas() {
        List<PersonaDTO> personasDTO = personaService.findAll().stream()
                .map(p -> new PersonaDTO(p.getId(), p.getNombre(), p.getApellido(), p.getNumeroDocumento(), p.getFechaNacimiento()))
                .toList();

        ResponseDTO<List<PersonaDTO>> response = new ResponseDTO<>(
                200,
                "Lista de personas obtenida correctamente",
                "Personas encontradas",
                personasDTO
        );
        return ResponseEntity.ok(response);
    }

    // GET -> obtener persona por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PersonaDTO>> getPersonaById(@PathVariable Long id) {
        return personaService.findById(id)
                .map(p -> {
                    PersonaDTO dto = new PersonaDTO(p.getId(), p.getNombre(), p.getApellido(), p.getNumeroDocumento(), p.getFechaNacimiento());
                    ResponseDTO<PersonaDTO> response = new ResponseDTO<>(
                            200,
                            "Persona encontrada correctamente",
                            "Persona encontrada",
                            dto
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>(404, "No existe persona con ID " + id, "Persona no encontrada", null)));
    }

    // POST -> crear persona
    @PostMapping
    public ResponseEntity<ResponseDTO<PersonaDTO>> createPersona(@RequestBody Persona persona) {
        Persona newPersona = personaService.save(persona);
        PersonaDTO dto = new PersonaDTO(newPersona.getId(), newPersona.getNombre(), newPersona.getApellido(), newPersona.getNumeroDocumento(), newPersona.getFechaNacimiento());
        ResponseDTO<PersonaDTO> response = new ResponseDTO<>(
                201,
                "Persona creada correctamente",
                "Persona creada",
                dto
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT -> actualizar persona
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PersonaDTO>> updatePersona(@PathVariable Long id,
                                                                 @RequestBody Persona personaDetails) {
        Persona updatedPersona = personaService.update(id, personaDetails);
        if (updatedPersona != null) {
            PersonaDTO dto = new PersonaDTO(updatedPersona.getId(), updatedPersona.getNombre(), updatedPersona.getApellido(), updatedPersona.getNumeroDocumento(), updatedPersona.getFechaNacimiento());
            ResponseDTO<PersonaDTO> response = new ResponseDTO<>(
                    200,
                    "Persona actualizada correctamente",
                    "Persona actualizada",
                    dto
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO<>(404, "No existe persona con ID " + id, "Persona no encontrada", null));
        }
    }

    // DELETE -> eliminar persona
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deletePersona(@PathVariable Long id) {
        if (personaService.deleteById(id)) {
            ResponseDTO<Void> response = new ResponseDTO<>(
                    200,
                    "Persona eliminada correctamente",
                    "Persona eliminada",
                    null
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO<>(404, "No existe persona con ID " + id, "Persona no encontrada", null));
        }
    }
}
