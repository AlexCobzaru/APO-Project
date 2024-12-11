package org.Proiect.ServiciiRest;

import jakarta.validation.Valid;
import org.Proiect.DTO.BadgeDTO;
import org.Proiect.DTO.CursDTO;
import org.Proiect.DTO.UtilizatorCursDTO;
import org.Proiect.Servicii.IDezvoltareWorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/servicii/dezvoltare")
public class DezvoltareController {

    private final IDezvoltareWorkflowService workflowService;

    public DezvoltareController(IDezvoltareWorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    // Creare curs
    @PostMapping("/cursuri")
    public ResponseEntity<CursDTO> creeazaCurs(@Valid @RequestBody CursDTO cursDTO) {
        var curs = workflowService.creeazaCurs(cursDTO.getTitlu(), cursDTO.getAdminId());
        var rezultatDTO = new CursDTO();
        rezultatDTO.setId(curs.getId());
        rezultatDTO.setTitlu(curs.getTitlu());
        rezultatDTO.setDescriere(curs.getDescriere());
        rezultatDTO.setDurataOre(curs.getDurataOre());
        rezultatDTO.setAdminId(curs.getAdmin().getUserId());
        return ResponseEntity.ok(rezultatDTO);
    }

    // Vizualizare toate badge-urile
    @GetMapping("/badge")
    public ResponseEntity<List<BadgeDTO>> getAllBadges() {
        var badges = workflowService.getAllBadges()
                .stream()
                .map(badge -> {
                    var badgeDTO = new BadgeDTO();
                    badgeDTO.setId(badge.getId());
                    badgeDTO.setTitlu(badge.getTitlu());
                    badgeDTO.setDescriere(badge.getDescriere());
                    badgeDTO.setDificultate(badge.getDificultate());
                    badgeDTO.setCursId(badge.getCurs().getId());
                    return badgeDTO;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(badges);
    }

    // Urmărire progres utilizator
    @GetMapping("/utilizator-progres")
    public ResponseEntity<UtilizatorCursDTO> urmaresteProgres(@RequestParam Integer cursId, @RequestParam Integer utilizatorId) {
        var progres = workflowService.urmaresteProgresPropriu(cursId, utilizatorId);
        var progresDTO = new UtilizatorCursDTO();
        progresDTO.setId(progres.getId());
        progresDTO.setCursId(progres.getCurs().getId());
        progresDTO.setUtilizatorId(progres.getUtilizator().getUserId());
        progresDTO.setCompletat(progres.isCompletat());
        progresDTO.setProgres(progres.getProgres());
        progresDTO.setDataInrolare(progres.getDataInrolare());
        progresDTO.setDataFinalizare(progres.getDataFinalizare());
        return ResponseEntity.ok(progresDTO);
    }

    // Generare badge pentru un utilizator
    @PostMapping("/badge")
    public ResponseEntity<BadgeDTO> genereazaBadge(@RequestParam Integer cursId, @RequestParam Integer utilizatorId) {
        var badge = workflowService.genereazaBadgePentruCurs(cursId, utilizatorId);
        var badgeDTO = new BadgeDTO();
        badgeDTO.setId(badge.getId());
        badgeDTO.setTitlu(badge.getTitlu());
        badgeDTO.setDescriere(badge.getDescriere());
        badgeDTO.setDificultate(badge.getDificultate());
        badgeDTO.setCursId(badge.getCurs().getId());
        return ResponseEntity.ok(badgeDTO);
    }
}
