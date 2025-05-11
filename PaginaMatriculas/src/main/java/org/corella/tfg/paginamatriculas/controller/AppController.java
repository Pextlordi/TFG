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

    @Autowired
    private usuariorepo usuariorepo;
    @Autowired
    private matricularepo matricularepo;

    @GetMapping("/")
    public String index(Model model) {
        System.out.println("Inicio de pagina index.html");
        return "index";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuariosList", usuariorepo.findAll());
        return "usuarios";
    }

    @GetMapping("/matriculas")
    public String plates(Model model) {
        model.addAttribute("matriculasList", matricularepo.findAll());
        return "matriculas";
    }

    @GetMapping("/crear")
    public String createForm(Model model) {
        model.addAttribute("usuarioNuevo", new Usuario());
        model.addAttribute("matriculaNueva", new Matricula());
        model.addAttribute("users", usuariorepo.findAll());
        return "formulario";
    }

    @PostMapping("/usuario/guardar")
    public String createUser(Usuario user) {
        usuariorepo.save(user);
        return "redirect:/users";
    }

    @PostMapping("/matricula/guardar")
    public String createPlate(Matricula matricula, @RequestParam Long userId) {
        //matricula.setUser(usuariorepo.findById(userId));
        matricularepo.save(matricula);
        return "redirect:/plates";
    }

    @GetMapping("/usuario/{id}/editar")
    public String editPage(Model model) {
        model.addAttribute("usuariosList", usuariorepo.findAll());
        return "edit";
    }
    @GetMapping("/matricula/{id}/editar")
    public String editUserPage(@RequestParam Long id, Model model) {
        model.addAttribute("matriculasList", usuariorepo.findAll());
        return "edit";
    }

    @PostMapping("/usuario/borrar")
    public String deleteUser(@RequestParam Long id) {
        usuariorepo.deleteById(id);
        return "redirect:/usuarios";
    }

    @PostMapping("/matricula/borrar")
    public String deletePlate(@RequestParam Long id) {
        matricularepo.deleteById(id);
        return "redirect:/matriculas";
    }
}