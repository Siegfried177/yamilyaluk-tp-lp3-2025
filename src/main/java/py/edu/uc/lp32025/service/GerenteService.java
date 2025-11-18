package py.edu.uc.lp32025.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.Gerente;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.EmpleadoNoEncontradoException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.repository.GerenteRepository; // Asumo la existencia de este repositorio

import java.time.LocalDate;

@Service
@Slf4j
public class GerenteService {

    private final GerenteRepository gerenteRepository;

    // Asumo la existencia de un servicio o repositorio para Empleados que no son Gerentes
    // private final EmpleadoRepository empleadoRepository;

    public GerenteService(GerenteRepository gerenteRepository) {
        this.gerenteRepository = gerenteRepository;
    }

    /**
     * Permite a un Gerente autorizar o denegar una solicitud de un empleado subordinado.
     * @param gerenteId ID del gerente que autoriza.
     * @param subordinadoId ID del empleado cuya solicitud se autoriza.
     * @param comentarioGerente Comentario de la autorización/denegación.
     * @return boolean True si fue autorizado, False si fue denegado.
     * @throws EmpleadoNoEncontradoException Si el gerente no existe.
     * @throws PermisoNoConcedidoException Si el gerente no puede autorizar por alguna regla de negocio.
     */
    public boolean autorizarPermiso(Long gerenteId, Long subordinadoId, String comentarioGerente)
            throws EmpleadoNoEncontradoException, PermisoNoConcedidoException {

        Gerente gerente = gerenteRepository.findById(gerenteId)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Gerente con ID " + gerenteId + " no encontrado."));

        // Aquí se llama a la lógica implementada en la entidad Gerente (autorizarPermisoSubordinado)
        // La entidad lanza PermisoNoConcedidoException si aplica.
        boolean autorizado = gerente.autorizarPermisoSubordinado(subordinadoId, comentarioGerente);

        // Opcionalmente, se podría guardar el estado del empleado subordinado aquí
        // (por ejemplo, actualizando su estado de permiso en la base de datos).

        log.info("Resultado de la autorización: {}", autorizado ? "APROBADO" : "RECHAZADO");
        return autorizado;
    }

    /**
     * Procesa la solicitud de vacaciones de un Gerente, utilizando su lógica de validación especial.
     * @param gerenteId ID del gerente que solicita.
     * @param fechaInicio Fecha de inicio de las vacaciones.
     * @param fechaFin Fecha de fin de las vacaciones.
     * @throws EmpleadoNoEncontradoException Si el gerente no existe.
     * @throws PermisoNoConcedidoException Si la solicitud no cumple con las reglas especiales del Gerente.
     */
    public void solicitarVacacionesGerente(Long gerenteId, LocalDate fechaInicio, LocalDate fechaFin)
            throws EmpleadoNoEncontradoException, PermisoNoConcedidoException, DiasInsuficientesException {

        Gerente gerente = gerenteRepository.findById(gerenteId)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Gerente con ID " + gerenteId + " no encontrado."));

        // Llama al método de la entidad Gerente, que contiene la lógica de validación especial.
        gerente.solicitarVacaciones(gerenteId, fechaInicio, fechaFin);

        // Si no se lanzó excepción, la solicitud es válida.
        // Aquí iría la lógica para registrar la solicitud en la BD o actualizar días.
        gerenteRepository.save(gerente);

        log.info("Solicitud de vacaciones del Gerente ID {} registrada correctamente.", gerenteId);
    }
}