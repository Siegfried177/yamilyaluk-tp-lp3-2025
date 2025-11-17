package py.edu.uc.lp32025.service;

import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.exception.FechaInvalidaException;
import py.edu.uc.lp32025.repository.PersonaRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Obtener todas las personas
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    // Buscar por ID con excepción si no existe
    public Persona findByIdThrow(Long id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe persona con ID " + id));
    }

    // Guardar con validaciones
    public Persona save(Persona persona) {
        validarDatosGenerales(persona);
        return personaRepository.save(persona);
    }

    // Actualizar persona existente
    public Persona update(Long id, Persona personaDetails) {
        Persona existing = findByIdThrow(id);

        existing.setNombre(personaDetails.getNombre());
        existing.setApellido(personaDetails.getApellido());
        existing.setNumeroDocumento(personaDetails.getNumeroDocumento());

        if (personaDetails.getFechaNacimiento() != null) {
            validarFechaNacimiento(personaDetails.getFechaNacimiento());
            existing.setFechaNacimiento(personaDetails.getFechaNacimiento());
        }

        return personaRepository.save(existing);
    }

    // Filtrar por nombre, case insensitive
    public List<Persona> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return personaRepository.findAll();
        }
        return personaRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    // Eliminar
    public void delete(Long id) {
        Persona persona = findByIdThrow(id);
        personaRepository.delete(persona);
    }

    /* ==== MÉTODOS PRIVADOS DE VALIDACIÓN ==== */

    private void validarDatosGenerales(Persona persona) {
        if (persona.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        validarFechaNacimiento(persona.getFechaNacimiento());
    }

    private void validarFechaNacimiento(LocalDate fecha) {
        if (fecha.isAfter(LocalDate.now())) {
            throw new FechaInvalidaException("La fecha de nacimiento no puede estar en el futuro.");
        }
        int edad = Period.between(fecha, LocalDate.now()).getYears();
        if (edad < 18) {
            throw new IllegalArgumentException("La persona debe ser mayor de 18 años.");
        }
    }
}
