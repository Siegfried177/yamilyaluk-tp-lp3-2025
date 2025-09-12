package py.edu.uc.lp32025.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import py.edu.uc.lp32025.dto.GreetingDTO;

@RestController
public class IndexController {

    @GetMapping("/")
    public RedirectView redirectToHolaMundo() {
        return new RedirectView("/HolaMundo");
    }

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
