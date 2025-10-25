package py.edu.uc.lp32025.dto;

public class ResponseDTO<T> extends BaseDTO {
    private T data;

    public ResponseDTO(int statusCode, String technicalMessage, String userMessage, T data) {
        super(statusCode, technicalMessage, userMessage);
        this.data = data;
    }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
