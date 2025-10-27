package py.edu.uc.lp32025.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class BatchEmpleadosRequest {

    @Valid
    @NotEmpty(message = "La lista de empleados tiempo completo no puede estar vacía")
    private List<ReporteEmpleadoDto> empleadosTiempoCompleto;

    @Valid
    @NotEmpty(message = "La lista de empleados por hora no puede estar vacía")
    private List<ReporteEmpleadoDto> empleadosPorHora;

    @Valid
    @NotEmpty(message = "La lista de contratistas no puede estar vacía")
    private List<ReporteEmpleadoDto> contratistas;

    // ------------------------
    // GETTERS Y SETTERS
    // ------------------------
    public List<ReporteEmpleadoDto> getEmpleadosTiempoCompleto() {
        return empleadosTiempoCompleto;
    }

    public void setEmpleadosTiempoCompleto(List<ReporteEmpleadoDto> empleadosTiempoCompleto) {
        this.empleadosTiempoCompleto = empleadosTiempoCompleto;
    }

    public List<ReporteEmpleadoDto> getEmpleadosPorHora() {
        return empleadosPorHora;
    }

    public void setEmpleadosPorHora(List<ReporteEmpleadoDto> empleadosPorHora) {
        this.empleadosPorHora = empleadosPorHora;
    }

    public List<ReporteEmpleadoDto> getContratistas() {
        return contratistas;
    }

    public void setContratistas(List<ReporteEmpleadoDto> contratistas) {
        this.contratistas = contratistas;
    }
}
