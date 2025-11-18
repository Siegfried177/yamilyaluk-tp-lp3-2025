package py.edu.uc.lp32025.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.uc.lp32025.domain.Empleado;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.repository.PersonaRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class NominaService {

    private final PersonaRepository personaRepository;

    public NominaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Solicita vacaciones para un empleado.
     */
    @Transactional
    public void solicitarVacaciones(Long empleadoId, LocalDate inicio, LocalDate fin)
            throws PermisoNoConcedidoException, DiasInsuficientesException {

        Persona p = personaRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + empleadoId));

        if (!(p instanceof Empleado empleado)) {
            throw new RuntimeException("El ID no corresponde a un Empleado: " + empleadoId);
        }

        empleado.solicitarVacaciones(empleadoId, inicio, fin);
        // Persistir cambios si llevas control de días
        personaRepository.save(empleado);
    }

    /**
     * Solicita un permiso especial para un empleado.
     */
    @Transactional
    public void solicitarPermisoEspecial(Long empleadoId, LocalDate inicio, LocalDate fin, String motivo)
            throws PermisoNoConcedidoException {

        Persona p = personaRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + empleadoId));

        if (!(p instanceof Empleado empleado)) {
            throw new RuntimeException("El ID no corresponde a un Empleado: " + empleadoId);
        }

        empleado.solicitarPermisoEspecial(empleadoId, inicio, fin, motivo);
        personaRepository.save(empleado);
    }

    /**
     * Obtiene la nómina incluyendo los días de vacaciones y permisos solicitados.
     */
    public List<Empleado> obtenerNominaCompleta() {
        return personaRepository.findAll()
                .stream()
                .filter(e -> e instanceof Empleado)
                .map(e -> (Empleado) e)
                .toList(); // Contiene ya los días solicitados en cada empleado
    }
}
