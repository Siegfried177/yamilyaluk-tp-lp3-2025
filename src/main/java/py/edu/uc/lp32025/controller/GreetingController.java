package py.edu.uc.lp32025.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; // Añadido para mayor claridad
import py.edu.uc.lp32025.dto.GreetingDTO;

@RestController // Es crucial para que Spring lo reconozca como un controlador REST
public class GreetingController {

    @GetMapping("/HolaMundo")
    public GreetingDTO saludo(
            @RequestParam(name = "nombre", defaultValue = "Mundo") String nombre) {

        return new GreetingDTO(
                200, // statusCode
                "OK", // technicalMessage
                "Saludo generado correctamente", // userMessage
                "Hola, " + nombre // saludo
        );
    }
}