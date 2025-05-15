package org.corella.tfg.paginamatriculas.controller;

import org.corella.tfg.paginamatriculas.model.Usuario;
import org.corella.tfg.paginamatriculas.repository.usuariorepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuariosController {
    @Autowired
    private usuariorepo usuariorepo;

    @GetMapping("/usuarios")
    public String mostrarUsuarios(Model model) {
        model.addAttribute("usuariosList", usuariorepo.findAll());
        return "usuarios";
    }

    @GetMapping("/usuario/crear")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("nuevo", true);
        return "formularioUsuario";
    }

    @PostMapping("/usuario/guardar")
    public String guardarUsuario(Usuario user) {
        usuariorepo.save(user);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuario/{id}/modificar")
    public String modificarUsuario(Model model, @PathVariable("id") String id) {
        model.addAttribute("usuario", usuariorepo.findById(id));
        model.addAttribute("nuevo", false);
        return "formularioUsuario";
    }

    @GetMapping("/usuario/{id}/borrar")
    public String borrarUsuario(@PathVariable("id") String id) {
        usuariorepo.deleteById(id);
        return "redirect:/usuarios";
    }
}
