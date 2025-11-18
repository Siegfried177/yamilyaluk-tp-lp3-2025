package py.edu.uc.lp32025.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponseDTO {
    // Getters y Setters
    private int statusCode;
    private String technicalMessage;
    private String userMessage;
    private LocalDateTime timestamp;

    public ErrorResponseDTO(int statusCode, String technicalMessage, String userMessage) {
        this.statusCode = statusCode;
        this.technicalMessage = technicalMessage;
        this.userMessage = userMessage;
        this.timestamp = LocalDateTime.now();
    }

}
