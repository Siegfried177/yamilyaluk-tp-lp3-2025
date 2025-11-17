package py.edu.uc.lp32025.controller;

import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Contratista;
import py.edu.uc.lp32025.service.ContratistaService;

import java.util.List;

@RestController
@RequestMapping("/api/contratistas")
public class ContratistaController {

    private final ContratistaService service;

    public ContratistaController(ContratistaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contratista> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Contratista obtener(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratista no encontrado con id: " + id));
    }

    @PostMapping
    public Contratista crear(@RequestBody Contratista contratista) {
        return service.save(contratista);
    }

    @PutMapping("/{id}")
    public Contratista actualizar(@PathVariable Long id, @RequestBody Contratista contratista) {
        contratista.setId(id);
        return service.save(contratista);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.delete(id);
    }
}
