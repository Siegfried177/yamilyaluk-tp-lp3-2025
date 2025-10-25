package py.edu.uc.lp32025.dto;

import java.time.LocalDateTime;

public class ErrorResponseDTO extends BaseDTO {
    private LocalDateTime timestamp;

    public ErrorResponseDTO(int statusCode, String technicalMessage, String userMessage) {
        super(statusCode, technicalMessage, userMessage);
        this.timestamp = LocalDateTime.now(); // se genera automáticamente
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
