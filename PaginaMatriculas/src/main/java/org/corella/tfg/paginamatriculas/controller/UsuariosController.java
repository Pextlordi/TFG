package org.corella.tfg.paginamatriculas.controller;

import org.corella.tfg.paginamatriculas.model.Matricula;
import org.corella.tfg.paginamatriculas.model.Usuario;
import org.corella.tfg.paginamatriculas.repository.administradorrepo;
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

@Controller
public class UsuariosController {
    @Autowired
    private usuariorepo usuariorepo;

    @Autowired
    private administradorrepo administradorrepo;

    @GetMapping("/usuarios")
    public String mostrarUsuarios(@RequestParam(required = false) boolean activos, Model model) {
        List<Usuario> usuarios;
        if (activos) {
            LocalDate hoy = LocalDate.now();
            usuarios = usuariorepo.findActivas(hoy);
        } else {
            usuarios = usuariorepo.findAll();
        }
        model.addAttribute("usuariosList", usuarios);
        return "usuarios";
    }

    @GetMapping("/usuario/{id}")
    public String verUsuario(@PathVariable String id, Model model) {
        Optional<Usuario> usuario = usuariorepo.findById(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
        } else {
            return "redirect:/usuarios";
        }
        return "usuario";
    }


    @GetMapping("/usuario/crear")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("nuevo", true);
        model.addAttribute("listaAdmins", administradorrepo.findByPermisoTrue());
        return "formularioUsuario";
    }

    @PostMapping("/usuario/guardar")
    public String guardarUsuario(Usuario user) {
        usuariorepo.save(user);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuario/{id}/modificar")
    public String modificarUsuario(Model model, @PathVariable("id") String id) {
        Optional<Usuario> usuario = usuariorepo.findById(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
        } else {
            model.addAttribute("usuario", new Usuario());
        }
        model.addAttribute("nuevo", false);
        model.addAttribute("listaAdmins", administradorrepo.findByPermisoTrue());
        return "formularioUsuario";
    }

    @GetMapping("/usuario/{id}/borrar")
    public String borrarUsuario(@PathVariable("id") String id) {
        usuariorepo.deleteById(id);
        return "redirect:/usuarios";
    }
}
