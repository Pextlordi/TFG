package org.corella.tfg.paginamatriculas.controller;

import org.corella.tfg.paginamatriculas.repository.administradorrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Esta clase se dedica a la página principal del portal web.
 * @author Petteri Ketola
 * @version 27-05-2025
 */

@Controller
public class AppController {

    //Página login
    @GetMapping("/")
    public String index(Model model) {
        return "login";
    }
}