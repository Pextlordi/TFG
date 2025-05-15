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

@Controller
public class MatriculasController {

    @Autowired
    private matricularepo matricularepo;

    @Autowired
    private usuariorepo usuariorepo;

    @GetMapping("/matriculas")
    public String mostrarMatriculas(Model model) {
        model.addAttribute("matriculasList", matricularepo.findAll());
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
        model.addAttribute("usuariosList", usuariorepo.findAll());
        model.addAttribute("matricula", matricularepo.findById(id));
        model.addAttribute("nuevo", false);
        return "formularioMatricula";
    }

    @GetMapping("/matricula/{id}/borrar")
    public String borrarMatricula(@PathVariable("id") String id) {
        matricularepo.deleteById(id);
        return "redirect:/matriculas";
    }
}
