package py.edu.uc.lp32025.service;

import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.exception.FechaInvalidaException;
import py.edu.uc.lp32025.repository.PersonaRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    // Inyección por constructor (recomendado)
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Obtener todas las personas
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    // Buscar por ID
    public Optional<Persona> findById(Long id) {
        return personaRepository.findById(id);
    }

    // Guardar con regla de negocio
    public Persona save(Persona persona) {
        if (persona.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }

        // Verificar que no sea en el futuro
        if (persona.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new FechaInvalidaException("La fecha de nacimiento no puede estar en el futuro.");
        }

        // Verificar mayoría de edad
        int edad = Period.between(persona.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 18) {
            throw new IllegalArgumentException("La persona debe ser mayor de 18 años.");
        }

        return personaRepository.save(persona);
    }

    // Actualizar persona existente
    public Persona update(Long id, Persona personaDetails) {
        Optional<Persona> personaOptional = personaRepository.findById(id);

        if (personaOptional.isPresent()) {
            Persona existingPersona = personaOptional.get();

            existingPersona.setNombre(personaDetails.getNombre());
            existingPersona.setApellido(personaDetails.getApellido());
            existingPersona.setNumeroDocumento(personaDetails.getNumeroDocumento());

            if (personaDetails.getFechaNacimiento() != null) {
                // Verificar que no sea en el futuro
                if (personaDetails.getFechaNacimiento().isAfter(LocalDate.now())) {
                    throw new FechaInvalidaException("La fecha de nacimiento no puede estar en el futuro.");
                }

                int edad = Period.between(personaDetails.getFechaNacimiento(), LocalDate.now()).getYears();
                if (edad < 18) {
                    throw new IllegalArgumentException("La persona debe ser mayor de 18 años.");
                }

                existingPersona.setFechaNacimiento(personaDetails.getFechaNacimiento());
            }

            return personaRepository.save(existingPersona);
        }

        return null; // O lanzar una excepción personalizada (ej. PersonaNoEncontradaException)
    }

    // Filtrar por nombre, case insensitive
    public List<Persona> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return personaRepository.findAll();
        }
        return personaRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    // Eliminar por ID
    public boolean deleteById(Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isPresent()) {
            personaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
