package org.corella.tfg.paginamatriculas.controller;
import org.corella.tfg.paginamatriculas.model.Matricula;
import org.corella.tfg.paginamatriculas.model.Usuario;
import org.corella.tfg.paginamatriculas.repository.matricularepo;
import org.corella.tfg.paginamatriculas.repository.usuariorepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/matriculas";
    }
}