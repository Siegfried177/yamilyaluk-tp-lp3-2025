package py.edu.uc.lp32025.dto;

import java.time.LocalDateTime;

public class ErrorResponseDTO {
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

    // Getters y Setters
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getTechnicalMessage() { return technicalMessage; }
    public void setTechnicalMessage(String technicalMessage) { this.technicalMessage = technicalMessage; }

    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
