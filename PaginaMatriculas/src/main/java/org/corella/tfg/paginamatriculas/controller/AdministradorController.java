package org.corella.tfg.paginamatriculas.controller;

import org.corella.tfg.paginamatriculas.model.Administrador;
import org.corella.tfg.paginamatriculas.repository.administradorrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

/**
 * Esta clase se dedica a las funcionalidades de elementos del panel de admin y objetos administrador del sistema de la página web.
 * @author Petteri Ketola
 * @version 27-05-2025
 */

@Controller
public class AdministradorController {

    @Autowired
    private administradorrepo administradorrepo;

    @GetMapping("/admin")
    public String adminPanel(@RequestParam(required = false) boolean conPermiso, Model model) {
        if (conPermiso) {
            model.addAttribute("adminList", administradorrepo.findByPermisoTrue());
        } else {
            model.addAttribute("adminList", administradorrepo.findAll());
        }
        return "adminPanel";
    }

    @GetMapping("/administrador/{id}")
    public String verUsuario(@PathVariable Integer id, Model model) {
        Optional<Administrador> administrador = administradorrepo.findById(id);
        if (administrador.isPresent()) {
            model.addAttribute("admin", administrador.get());
        } else {
            return "redirect:/admin";
        }
        return "admin";
    }

    @GetMapping("/administrador/crear")
    public String crearAdmin(Model model) {
        model.addAttribute("admin", new Administrador());
        model.addAttribute("nuevo", true);
        return "formularioAdmin";
    }

    @PostMapping("/administrador/guardar")
    public String guardarAdmin(Administrador administrador) {
        administradorrepo.save(administrador);
        return "redirect:/administrador/" + administrador.getId();
    }

    @GetMapping("/administrador/{id}/modificar")
    public String modificarAdmin(Model model, @PathVariable("id") Integer id) {
        Optional<Administrador> administrador = administradorrepo.findById(id);
        if (administrador.isPresent()) {
            model.addAttribute("admin", administrador.get());
        } else {
            model.addAttribute("admin", new Administrador());
        }
        model.addAttribute("nuevo", false);
        return "formularioAdmin";
    }

    @GetMapping("/administrador/{id}/borrar")
    public String borrarAdmin(@PathVariable("id") Integer id) {
        administradorrepo.deleteById(id);
        return "redirect:/admin";
    }
}
