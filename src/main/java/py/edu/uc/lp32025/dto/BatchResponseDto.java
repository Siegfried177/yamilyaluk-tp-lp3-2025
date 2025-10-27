package py.edu.uc.lp32025.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class BatchResponseDto {

    @NotBlank(message = "Mensaje no puede estar vacío")
    private String mensaje;

    @NotNull(message = "Cantidad de registros procesados no puede ser nula")
    private Integer registrosProcesados;

    private List<String> errores;

    // GETTERS Y SETTERS
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Integer getRegistrosProcesados() { return registrosProcesados; }
    public void setRegistrosProcesados(Integer registrosProcesados) { this.registrosProcesados = registrosProcesados; }

    public List<String> getErrores() { return errores; }
    public void setErrores(List<String> errores) { this.errores = errores; }
}
