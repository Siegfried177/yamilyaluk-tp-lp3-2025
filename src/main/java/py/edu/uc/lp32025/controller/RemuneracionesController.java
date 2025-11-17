package py.edu.uc.lp32025.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import py.edu.uc.lp32025.dto.ReporteEmpleadoDto;
import py.edu.uc.lp32025.service.RemuneracionesService;

import java.util.List;

// RemuneracionesController.java
@RestController
@RequestMapping("/api/remuneraciones")
public class RemuneracionesController {

    private final RemuneracionesService remuneracionesService;

    public RemuneracionesController(RemuneracionesService remuneracionesService) {
        this.remuneracionesService = remuneracionesService;
    }

    @GetMapping("/reporte")
    public ResponseEntity<List<ReporteEmpleadoDto>> obtenerReporteCompleto() {
        List<ReporteEmpleadoDto> reporte = remuneracionesService.generarReporteCompleto();
        return ResponseEntity.ok(reporte);
    }
}
