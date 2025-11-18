package py.edu.uc.lp32025.service;

import lombok.extern.slf4j.Slf4j; // ⬅️ Inyección del Logger con Lombok
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.Empleado;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.exception.RecursoNoEncontradoException;
import py.edu.uc.lp32025.repository.PersonaRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j // **¡Añadido!**
@Service
public class PermisosService {

    private final PersonaRepository personaRepository;

    public PermisosService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // --- Lógica de Vacaciones ---
    public void procesarSolicitudVacaciones(Long empleadoId, LocalDate inicio, LocalDate fin) throws DiasInsuficientesException {

        Empleado empleado = findEmpleadoById(empleadoId);
        long diasSolicitados = ChronoUnit.DAYS.between(inicio, fin.plusDays(1));

        // Uso del Logger de Lombok
        log.info("Procesando solicitud de vacaciones para {} ({} días)", empleado.getNombre(), diasSolicitados);

        // 1. Validación Básica
        empleado.solicitarVacaciones(empleadoId, inicio, fin);

        // 2. Validación de Antigüedad y Días Disponibles
        // Asumiendo que Empleado tiene getFechaContratacion()
        if (empleado.getFechaContratacion() == null || ChronoUnit.YEARS.between(empleado.getFechaContratacion(), LocalDate.now()) < 1) {
            throw new PermisoNoConcedidoException("Antigüedad insuficiente. Se requiere 1 año (Art. 44 C.L.).");
        }

        if (diasSolicitados > 12) {
            log.warn("Solicitud de {} días excede el límite de 12 para Antigüedad 1-5 años.", diasSolicitados);
            throw new PermisoNoConcedidoException("Ha excedido los días de vacaciones disponibles para su antigüedad.");
        }

        log.info("✅ Solicitud de Vacaciones de Empleado ID {} Aprobada ({} días)", empleadoId, diasSolicitados);
    }

    // --- Lógica de Permisos Especiales ---
    public void procesarSolicitudPermisoEspecial(Long empleadoId, LocalDate inicio, LocalDate fin, String motivo) {

        Empleado empleado = findEmpleadoById(empleadoId);
        long diasSolicitados = ChronoUnit.DAYS.between(inicio, fin.plusDays(1));

        // Uso del Logger de Lombok
        log.info("Procesando solicitud de Permiso Especial para {} (Motivo: {})", empleado.getNombre(), motivo);

        // 1. Validación Básica
        empleado.solicitarPermisoEspecial(empleadoId, inicio, fin, motivo);

        // 2. Validación de Límites Legales
        switch (motivo.toUpperCase()) {
            case "MATRIMONIO":
                if (diasSolicitados > 3) {
                    throw new PermisoNoConcedidoException("El permiso por Matrimonio solo es de 3 días corridos.");
                }
                break;
            case "DUELO":
                if (diasSolicitados > 5) {
                    throw new PermisoNoConcedidoException("El permiso por Duelo (familiar directo) solo es de 5 días corridos.");
                }
                break;
            default:
                throw new PermisoNoConcedidoException("Motivo de permiso especial no reconocido o no legalmente justificado.");
        }

        log.info("✅ Solicitud de Permiso Especial de Empleado ID {} Aprobada (Motivo: {})", empleadoId, motivo);
    }

    // --- Utilidad ---
    private Empleado findEmpleadoById(Long empleadoId) {
        return personaRepository.findById(empleadoId)
                .filter(p -> p instanceof Empleado)
                .map(p -> (Empleado) p)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró Empleado con ID: " + empleadoId));
    }
}