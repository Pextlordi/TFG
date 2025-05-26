package org.corella.tfg.paginamatriculas.controller;

import org.corella.tfg.paginamatriculas.repository.administradorrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String index(Model model) {
        return "login";
    }
}