package py.edu.uc.lp32025.dto;

public class GreetingDTO extends BaseDTO {
    private String saludo;

    public GreetingDTO(int statusCode, String technicalMessage, String userMessage, String saludo) {
        super(statusCode, technicalMessage, userMessage);
        this.saludo = saludo;
    }

    public String getSaludo() {
        return saludo;
    }
    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }
}
