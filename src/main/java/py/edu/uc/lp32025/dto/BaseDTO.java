package py.edu.uc.lp32025.dto;

public class BaseDTO {
    private int statusCode;
    private String technicalMessage;
    private String userMessage;

    public BaseDTO(int statusCode, String technicalMessage, String userMessage) {
        this.statusCode = statusCode;
        this.technicalMessage = technicalMessage;
        this.userMessage = userMessage;
    }

    // Getters y Setters
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getTechnicalMessage() {
        return technicalMessage;
    }
    public void setTechnicalMessage(String technicalMessage) {
        this.technicalMessage = technicalMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
