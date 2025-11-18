package py.edu.uc.lp32025.mapper;

import org.springframework.stereotype.Component;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.dto.ImpuestoDetalleDto;

@Component
public class ImpuestoDetalleMapper extends BaseMapper<ImpuestoDetalleDto, EmpleadoTiempoCompleto> {

    /**
     * Convierte la entidad EmpleadoTiempoCompleto al DTO ImpuestoDetalleDto.
     * La firma del método ahora usa el tipo E (EmpleadoTiempoCompleto) definido en la herencia.
     */
    @Override
    public ImpuestoDetalleDto toDto(EmpleadoTiempoCompleto empleado) {

        // La validación de instanceof ya no es estrictamente necesaria aquí
        // porque la firma del método ya garantiza que se recibirá un EmpleadoTiempoCompleto.
        // Si quisieras ser súper estricto:
        /*
        if (empleado == null) {
            throw new IllegalArgumentException("La entidad EmpleadoTiempoCompleto no puede ser nula.");
        }
        */

        ImpuestoDetalleDto dto = new ImpuestoDetalleDto();

        dto.setId(empleado.getId());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setNumeroDocumento(empleado.getNumeroDocumento());

        dto.setTipo("empleadoCompleto");

        // Uso de métodos de la entidad
        dto.setSalarioBruto(empleado.getSalarioMensual());
        dto.setDeducciones(empleado.calcularDeducciones());
        // Se usa getSalario() que llama a calcularSalario()
        dto.setImpuestoBase(empleado.calcularImpuestoBase(empleado.getSalario()));
        dto.setImpuestoTotal(empleado.calcularImpuestos());

        return dto;
    }

    /**
     * Implementación requerida para toEntity.
     */
    @Override
    public EmpleadoTiempoCompleto toEntity(ImpuestoDetalleDto dto) {
        // La implementación DTO -> Entidad es obligatoria al usar la jerarquía genérica.
        throw new UnsupportedOperationException("Mapeo de DTO a Entidad no implementado para ImpuestoDetalleMapper.");
    }
}