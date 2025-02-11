package com.example.demo.Controller;

import com.example.demo.Model.Candidature;
import com.example.demo.Model.Etat;
import com.example.demo.Service.CandidatureService;
import com.example.demo.Service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/recruiters")
public class AdminRecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    CandidatureService candidatureService;

    // Liste des recruteurs
    @GetMapping
    public String listRecruiters(Model model) {
        model.addAttribute("recruiters", recruiterService.getAllRecruiters());
        return "recruiter-management";  // This will render recruiter-management.html
    }

    // Supprimer un recruteur
    @GetMapping("/delete/{id}")
    public String deleteRecruiter(@PathVariable int id) {
        recruiterService.deleteRecruiter(id);
        return "redirect:/admin/recruiters";  // After deleting, redirect back to recruiter list
    }


    // pour l'acceptation ou refusion de condidature

    // Approuver la candidature
    @GetMapping("/candidatures/accepter/{id}")
    public String accepterCandidature(@PathVariable int id) {
        Candidature candidature = candidatureService.getCandidatureById(id);
        if (candidature != null) {
            candidature.setEtat(Etat.ACCEPTER);  // Changer l'état de la candidature
            candidatureService.saveCandidature(candidature);
        }
        return "redirect:/admin/candidatures";
    }

    // Refuser la candidature
    @GetMapping("/candidatures/refuser/{id}")
    public String refuserCandidature(@PathVariable int id) {
        Candidature candidature = candidatureService.getCandidatureById(id);
        if (candidature != null) {
            candidature.setEtat(Etat.ANNULER);  // Changer l'état de la candidature
            candidatureService.saveCandidature(candidature);
        }
        return "redirect:/admin/candidatures";
    }
    // Liste des candidatures pour l'administrateur
    @GetMapping("/candidatures")
    public String listerCandidatures(Model model) {
        model.addAttribute("candidatures", candidatureService.getAllCandidatures());
        return "candidature-management";
    }


/*<tr th:each="candidature : ${candidatures}">
            <td th:text="${candidature.candidat.nom}"></td>
            <td th:text="${candidature.offre.titre}"></td>
            <td th:text="${candidature.etat}"></td>
            <td>
                <a th:href="@{/admin/candidatures/accepter/{id}(id=${candidature.id})}">Accepter</a>
                <a th:href="@{/admin/candidatures/refuser/{id}(id=${candidature.id})}">Refuser</a>
            </td>
        </tr>*/
}