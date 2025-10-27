package py.edu.uc.lp32025.controller;

import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.service.EmpleadoPorHoraService;
import java.util.List;

@RestController
@RequestMapping("/empleados-por-hora")
public class EmpleadoPorHoraController {

    private final EmpleadoPorHoraService service;

    public EmpleadoPorHoraController(EmpleadoPorHoraService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmpleadoPorHora> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EmpleadoPorHora obtener(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    @PostMapping
    public EmpleadoPorHora crear(@RequestBody EmpleadoPorHora empleado) {
        return service.save(empleado);
    }

    @PutMapping("/{id}")
    public EmpleadoPorHora actualizar(@PathVariable Long id, @RequestBody EmpleadoPorHora empleado) {
        empleado.setId(id);
        return service.save(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.delete(id);
    }
}
