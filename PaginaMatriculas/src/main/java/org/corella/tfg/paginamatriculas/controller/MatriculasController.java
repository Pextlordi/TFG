package org.corella.tfg.paginamatriculas.controller;

import org.corella.tfg.paginamatriculas.model.Matricula;
import org.corella.tfg.paginamatriculas.repository.matricularepo;
import org.corella.tfg.paginamatriculas.repository.usuariorepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Esta clase se dedica a las funcionalidades de elementos de la sección de matrículas de la página web.
 * @author Petteri Ketola
 * @version 27-05-2025
 */

@Controller
public class MatriculasController {

    @Autowired
    private matricularepo matricularepo;

    @Autowired
    private usuariorepo usuariorepo;

    @GetMapping("/matriculas")
    public String mostrarMatriculas(@RequestParam(required = false) boolean activos, Model model) {
        List<Matricula> matriculas;

        if (activos) {
            LocalDate hoy = LocalDate.now();
            matriculas = matricularepo.findActivas(hoy);
        } else {
            matriculas = matricularepo.findAll();
        }
        model.addAttribute("matriculasList", matriculas);
        return "matriculas";
    }

    @GetMapping("/matricula/crear")
    public String crearMatricula(Model model) {
        model.addAttribute("usuariosList", usuariorepo.findAll());
        model.addAttribute("matricula", new Matricula());
        model.addAttribute("nuevo", true);
        return "formularioMatricula";
    }

    @PostMapping("/matricula/guardar")
    public String guardarMatricula(Matricula matricula) {
        matricularepo.save(matricula);
        return "redirect:/matriculas";
    }

    @GetMapping("/matricula/{id}/modificar")
    public String modificarMatricula(Model model, @PathVariable("id") String id) {
        Optional<Matricula> matricula = matricularepo.findById(id);
        if (matricula.isPresent()) {
            model.addAttribute("matricula", matricula.get());
        } else {
            model.addAttribute("matricula", new Matricula());
        }
        model.addAttribute("usuariosList", usuariorepo.findAll());
        model.addAttribute("nuevo", false);
        return "formularioMatricula";
    }

    @GetMapping("/matricula/{id}/borrar")
    public String borrarMatricula(@PathVariable("id") String id) {
        matricularepo.deleteById(id);
        return "redirect:/matriculas";
    }
}
