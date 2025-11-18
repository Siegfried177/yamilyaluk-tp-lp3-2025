package py.edu.uc.lp32025.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ImpuestoDetalleDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String numeroDocumento;

    private String tipo;

    private BigDecimal salarioBruto;
    private BigDecimal deducciones;
    private BigDecimal impuestoBase;
    private BigDecimal impuestoTotal;

}
