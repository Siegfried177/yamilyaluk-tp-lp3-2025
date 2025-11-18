package py.edu.uc.lp32025.mapper;

import org.springframework.stereotype.Component;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.dto.ImpuestoDetalleDto;
import py.edu.uc.lp32025.exception.MapeoInvalidoException;

import java.math.BigDecimal;

@Component
public class ImpuestoDetalleMapper extends BaseMapper {

    @Override
    public ImpuestoDetalleDto toDto(Persona entity) {

        if (!(entity instanceof EmpleadoTiempoCompleto empleado)) {
            throw new MapeoInvalidoException(
                    "Se esperaba EmpleadoTiempoCompleto pero se recibió " + entity.getClass().getSimpleName()
            );
        }

        ImpuestoDetalleDto dto = new ImpuestoDetalleDto();

        dto.setId(empleado.getId());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setNumeroDocumento(empleado.getNumeroDocumento());

        dto.setTipo("empleadoCompleto");
        dto.setSalarioBruto(empleado.getSalarioMensual());
        dto.setDeducciones(empleado.calcularDeducciones());
        dto.setImpuestoBase(empleado.calcularImpuestoBase(empleado.getSalario()));
        dto.setImpuestoTotal(empleado.calcularImpuestos());

        return dto;
    }
}
