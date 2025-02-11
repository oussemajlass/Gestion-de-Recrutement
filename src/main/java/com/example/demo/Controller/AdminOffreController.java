package com.example.demo.Controller;

import com.example.demo.Model.Offre;
import com.example.demo.Service.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/offres")
public class AdminOffreController {

    @Autowired
    private OffreService offreService;

    // Afficher toutes les offres
    @GetMapping
    public String afficherOffres(Model model) {
        List<Offre> offres = offreService.getAllOffres(); // Récupérer toutes les offres
        model.addAttribute("offres", offres); // Ajouter au modèle
        return "gestion-offres"; // Vue affichée (gestion-offres.html)
    }

    // Afficher le formulaire pour créer une nouvelle offre
    @GetMapping("/create")
    public String afficherFormulaireCreation(Model model) {
        model.addAttribute("offre", new Offre());
        return "formulaire-offre"; // Vue du formulaire
    }

    // Enregistrer une nouvelle offre
    @PostMapping("/save")
    public String enregistrerOffre(@ModelAttribute("offre") Offre offre) {
        offreService.saveOffre(offre);
        return "redirect:/admin/offres";
    }

    // Afficher le formulaire pour modifier une offre existante
    @GetMapping("/edit/{id}")
    public String afficherFormulaireEdition(@PathVariable("id") int id, Model model) {
        Offre offre = offreService.getOffreById(id);
        if (offre != null) {
            model.addAttribute("offre", offre);
            return "formulaire-offre";
        }
        return "redirect:/admin/offres";
    }

    // Supprimer une offre par son ID
    @GetMapping("/delete/{id}")
    public String supprimerOffre(@PathVariable("id") int id) {
        offreService.deleteOffre(id);
        return "redirect:/admin/offres";
    }
}
