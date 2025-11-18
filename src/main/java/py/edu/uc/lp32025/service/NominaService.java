package py.edu.uc.lp32025.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.uc.lp32025.domain.*; // Importa todas las clases de dominio
import py.edu.uc.lp32025.dto.EmpleadoCreationDto;
import py.edu.uc.lp32025.dto.PersonaResponseDto;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class NominaService {

    private final PersonaRepository personaRepository;
    private final GerenteRepository gerenteRepository;
    private final EmpleadoTiempoCompletoRepository empleadoTiempoCompletoRepository;
    private final ContratistaRepository contratistaRepository;
    private final EmpleadoPorHoraRepository empleadoPorHoraRepository;

    public NominaService(PersonaRepository personaRepository, GerenteRepository gerenteRepository, EmpleadoTiempoCompletoRepository empleadoTiempoCompletoRepository, ContratistaRepository contratistaRepository, EmpleadoPorHoraRepository empleadoPorHoraRepository) {
        this.personaRepository = personaRepository;
        this.gerenteRepository = gerenteRepository;
        this.empleadoTiempoCompletoRepository = empleadoTiempoCompletoRepository;
        this.contratistaRepository = contratistaRepository;
        this.empleadoPorHoraRepository = empleadoPorHoraRepository;
    }

    // ---------------------------------------------------
    // 🆕 MÉTODO DE CREACIÓN GENÉRICA (FACTORÍA) - ¡COMPLETO!
    // ---------------------------------------------------
    @Transactional
    public PersonaResponseDto crearPersona(EmpleadoCreationDto dto) {
        Persona nuevaPersona;

        // 1. Lógica de Factoría: Determinar la subclase a instanciar
        switch (dto.getTipoEmpleado().toUpperCase()) {
            case "GERENTE":
                nuevaPersona = crearGerente(dto);
                break;

            case "EMPLEADO_TIEMPO_COMPLETO":
                nuevaPersona = crearEmpleadoTiempoCompleto(dto);
                break;

            case "CONTRATISTA":
                nuevaPersona = crearContratista(dto);
                break;

            case "EMPLEADO_POR_HORA":
                nuevaPersona = crearEmpleadoPorHora(dto);
                break;

            default:
                // Si el tipo no coincide con ningún caso
                throw new IllegalArgumentException("Tipo de empleado no válido para la creación: " + dto.getTipoEmpleado());
        }

        // 2. Mapear y devolver el DTO de respuesta
        return mapToResponseDto(nuevaPersona);
    }

    // ---------------------------------------------------
    // MÉTODOS PRIVADOS DE INSTANCIACIÓN Y PERSISTENCIA
    // ---------------------------------------------------

    // Método para Gerente (ya existente)
    private Gerente crearGerente(EmpleadoCreationDto dto) {
        Gerente gerente = new Gerente(
                dto.getNombre(),
                dto.getApellido(),
                dto.getNumeroDocumento(),
                dto.getFechaNacimiento(),
                dto.getPosicionGPS(),
                null, // Avatar (simplificado)
                dto.getFechaContratacion(),
                dto.getAñosAntiguedad(),
                dto.getAreaResponsabilidad()
        );
        return gerenteRepository.save(gerente);
    }

    // 🆕 Método para EmpleadoTiempoCompleto
    private EmpleadoTiempoCompleto crearEmpleadoTiempoCompleto(EmpleadoCreationDto dto) {
        EmpleadoTiempoCompleto empleado = new EmpleadoTiempoCompleto(
                dto.getNombre(),
                dto.getApellido(),
                dto.getNumeroDocumento(),
                dto.getFechaNacimiento(),
                dto.getPosicionGPS(),         // <-- Campo de Empleado
                null,                         // Avatar (simplificado, asumiendo nulo o un valor por defecto)
                dto.getFechaContratacion(),   // <-- Campo de Empleado
                dto.getSalarioMensual(),      // <-- Campo específico de ETC
                dto.getDepartamento()         // <-- Campo específico de ETC
        );
        return empleadoTiempoCompletoRepository.save(empleado);
    }

    // 🆕 Método para Contratista
    private Contratista crearContratista(EmpleadoCreationDto dto) {
        Contratista contratista = new Contratista(
                dto.getNombre(),
                dto.getApellido(),
                dto.getNumeroDocumento(),
                dto.getFechaNacimiento(),
                dto.getPosicionGPS(), // Usa campo de Empleado
                null, // Avatar
                dto.getFechaContratacion(), // Usa campo de Empleado
                dto.getMontoPorProyecto(), // Campo específico de Contratista
                dto.getProyectosCompletados(), // Campo específico de Contratista
                dto.getFechaFinContrato() // Campo específico de Contratista
        );
        return contratistaRepository.save(contratista);
    }

    // 🆕 Método para EmpleadoPorHora
    private EmpleadoPorHora crearEmpleadoPorHora(EmpleadoCreationDto dto) {
        EmpleadoPorHora empHora = new EmpleadoPorHora(
                dto.getNombre(),
                dto.getApellido(),
                dto.getNumeroDocumento(),
                dto.getFechaNacimiento(),
                dto.getPosicionGPS(), // Usa campo de Empleado
                null, // Avatar
                dto.getFechaContratacion(), // Usa campo de Empleado
                dto.getTarifaPorHora(), // Campo específico de EPH
                dto.getHorasTrabajadas() // Campo específico de EPH
        );
        return empleadoPorHoraRepository.save(empHora);
    }


    private PersonaResponseDto mapToResponseDto(Persona persona) {
        // ... (Se mantiene el mapeo existente) ...
        PersonaResponseDto response = new PersonaResponseDto();
        response.setId(persona.getId());
        response.setTipoPersona(persona.getClass().getSimpleName());
        response.setNombreCompleto(persona.getNombre() + " " + persona.getApellido());
        response.setNumeroDocumento(persona.getNumeroDocumento());

        if (persona instanceof Empleado empleado) {
            response.setFechaContratacion(empleado.getFechaContratacion());
        }

        response.setMensaje("Creado exitosamente como " + response.getTipoPersona());
        return response;
    }


    // ---------------------------------------------------
// 🆕 MÉTODO DE LECTURA POR ID (READ)
// ---------------------------------------------------

    /**
     * Recupera una Persona (Empleado, Gerente, etc.) por su ID y la mapea a DTO.
     * @param id El ID del recurso a buscar.
     * @return PersonaResponseDto si se encuentra.
     * @throws RuntimeException si el recurso no es encontrado (debería ser una NotFoundException real).
     */
    @Transactional(readOnly = true)
    public PersonaResponseDto obtenerPersonaPorId(Long id) {

        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona con ID " + id + " no encontrada."));
        // 👆 NOTA: En una aplicación real, deberías lanzar una excepción HTTP 404 personalizada (e.g., ResourceNotFoundException).

        // Reutilizamos el método de mapeo que ya tenemos para la creación
        return mapToResponseDto(persona);
    }

    // 🆕 ACTUALIZACIÓN (UPDATE)
// ---------------------------------------------------

    @Transactional
    public PersonaResponseDto actualizarPersona(Long id, EmpleadoCreationDto dto) {
        Persona personaExistente = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona con ID " + id + " no encontrada para actualizar."));

        // 1. Aplicar campos comunes (Persona/Empleado)
        personaExistente.setNombre(dto.getNombre());
        personaExistente.setApellido(dto.getApellido());
        personaExistente.setNumeroDocumento(dto.getNumeroDocumento());
        personaExistente.setFechaNacimiento(dto.getFechaNacimiento());

        if (personaExistente instanceof Empleado empleado) {
            empleado.setPosicionGPS(dto.getPosicionGPS());
            empleado.setFechaContratacion(dto.getFechaContratacion());
        }

        // 2. Aplicar campos específicos basados en el tipo de instancia
        if (personaExistente instanceof Gerente gerente) {
            gerente.setAreaResponsabilidad(dto.getAreaResponsabilidad());
            gerente.setAñosAntiguedad(dto.getAñosAntiguedad());
        } else if (personaExistente instanceof EmpleadoTiempoCompleto etc) {
            etc.setSalarioMensual(dto.getSalarioMensual());
            etc.setDepartamento(dto.getDepartamento());
        } else if (personaExistente instanceof Contratista contratista) {
            contratista.setMontoPorProyecto(dto.getMontoPorProyecto());
            contratista.setProyectosCompletados(dto.getProyectosCompletados());
            contratista.setFechaFinContrato(dto.getFechaFinContrato());
        } else if (personaExistente instanceof EmpleadoPorHora eph) {
            eph.setTarifaPorHora(dto.getTarifaPorHora());
            eph.setHorasTrabajadas(dto.getHorasTrabajadas());
        }

        // 3. Guardar el objeto actualizado (Spring Data JPA guarda el objeto correcto)
        Persona personaActualizada = personaRepository.save(personaExistente);

        return mapToResponseDto(personaActualizada);
    }

// ---------------------------------------------------
// 🆕 BORRADO (DELETE)
// ---------------------------------------------------

    @Transactional
    public void eliminarPersona(Long id) {
        if (!personaRepository.existsById(id)) {
            throw new RuntimeException("Persona con ID " + id + " no encontrada para eliminar.");
        }
        personaRepository.deleteById(id);
    }

    // ---------------------------------------------------
    // MÉTODOS EXISTENTES (se omiten por brevedad)
    // ---------------------------------------------------

// ---------------------------------------------------
    // MÉTODO DE NEGOCIO: SOLICITAR VACACIONES
    // ---------------------------------------------------

    /**
     * Solicita vacaciones para un empleado, delegando la validación de días y fechas
     * a la lógica de dominio de la clase Empleado.
     */
    @Transactional
    public void solicitarVacaciones(Long empleadoId, LocalDate inicio, LocalDate fin)
            throws PermisoNoConcedidoException, DiasInsuficientesException {

        Persona p = personaRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + empleadoId));

        if (!(p instanceof Empleado empleado)) {
            throw new RuntimeException("El ID no corresponde a un Empleado: " + empleadoId);
        }
        // Usando el método tal como lo tienes actualmente (con el ID redundante, pero funcional):
        empleado.solicitarVacaciones(empleadoId, inicio, fin);

        // Persistir los cambios del estado (días disponibles y solicitados)
        personaRepository.save(empleado);
    }

    @Transactional
    public void solicitarPermisoEspecial(Long empleadoId, LocalDate inicio, LocalDate fin, String motivo)
            throws PermisoNoConcedidoException {
        // ... (Lógica existente) ...
    }

    public List<Empleado> obtenerNominaCompleta() {
        // ... (Lógica existente) ...
        return personaRepository.findAll()
                .stream()
                .filter(e -> e instanceof Empleado)
                .map(e -> (Empleado) e)
                .toList();
    }
}