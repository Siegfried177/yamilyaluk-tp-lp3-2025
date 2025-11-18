package py.edu.uc.lp32025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Nota: No usamos @RequestMapping aquí si las rutas varían. Si quisiéramos una base /api, la pondríamos.
@RestController
public abstract class BaseController {

    // Aquí puedes definir inyecciones de dependencias comunes
    // Por ejemplo: private final GlobalService globalService;

    /**
     * Método común (opcional) que podría ser usado por todos los controladores
     * o sobrescrito.
     */
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Servicio Base: OK");
    }

    // Método abstracto para forzar a los hijos a implementar una funcionalidad clave
    // public abstract ResponseEntity<?> getDatosGenerales();
}