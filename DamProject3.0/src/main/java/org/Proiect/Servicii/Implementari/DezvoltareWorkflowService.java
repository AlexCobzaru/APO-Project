package org.Proiect.Servicii.Implementari;

import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.App.TipUtilizator;
import org.Proiect.Domain.Dezvoltare.Badge;
import org.Proiect.Domain.Dezvoltare.Curs;
import org.Proiect.Domain.Dezvoltare.UtilizatorCurs;
import org.Proiect.Servicii.Repository.AppUserRepository;
import org.Proiect.Servicii.Repository.BadgeRepository;
import org.Proiect.Servicii.Repository.CursRepository;
import org.Proiect.Servicii.Repository.UtilizatorCursRepository;
import org.Proiect.Servicii.IDezvoltareWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DezvoltareWorkflowService implements IDezvoltareWorkflowService {

    @Autowired
    private CursRepository cursRepository;

    @Autowired
    private AppUserRepository utilizatorRepository;

    @Autowired
    private UtilizatorCursRepository utilizatorCursRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Override
    public Curs creeazaCurs(String titlu, Integer adminId) {
        Utilizator admin = utilizatorRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Adminul nu există."));

        // Verificăm dacă utilizatorul are drepturi de a crea cursuri
        if (admin.getTipUtilizator() != TipUtilizator.ADMIN) {
            throw new SecurityException("Doar utilizatorii de tip Admin pot crea cursuri.");
        }

        Curs curs = new Curs();
        curs.setTitlu(titlu);
        curs.setAdmin(admin); // Asigură-te că setăm corect utilizatorul admin

        // Salvăm cursul
        return cursRepository.save(curs);
    }

    @Override
    public void asigneazaUtilizatoriLaCurs(Integer cursId, List<Integer> utilizatorIds) {
        Curs curs = cursRepository.findById(cursId)
                .orElseThrow(() -> new IllegalArgumentException("Cursul nu există."));

        for (Integer utilizatorId : utilizatorIds) {
            Utilizator utilizator = utilizatorRepository.findById(utilizatorId)
                    .orElseThrow(() -> new IllegalArgumentException("Utilizatorul nu există."));

            UtilizatorCurs utilizatorCurs = new UtilizatorCurs();
            utilizatorCurs.setCurs(curs);
            utilizatorCurs.setUtilizator(utilizator);

            // Folosim java.util.Date în loc de java.time.LocalDate
            utilizatorCurs.setDataInrolare(new java.util.Date());
            utilizatorCurs.setProgres(0);
            utilizatorCurs.setCompletat(false);

            utilizatorCursRepository.save(utilizatorCurs);
        }
    }

    @Override
    public Curs editeazaCurs(Integer cursId, String titlu) {
        Curs curs = cursRepository.findById(cursId)
                .orElseThrow(() -> new IllegalArgumentException("Cursul nu există."));

        curs.setTitlu(titlu);
        return cursRepository.save(curs);
    }

    @Override
    public Optional<Curs> vizualizeazaCurs(Integer cursId, Integer utilizatorId) {
            // Găsim utilizatorul din baza de date
            Utilizator utilizator = utilizatorRepository.findById(utilizatorId)
                    .orElseThrow(() -> new IllegalArgumentException("Utilizatorul nu există."));

            // Verificăm dacă utilizatorul are acces
            boolean areAcces = utilizator.getTipUtilizator() == TipUtilizator.ADMIN ||
                    utilizator.getTipUtilizator() == TipUtilizator.MANAGER ||
                    utilizatorCursRepository.findByUtilizator_UserIdAndCurs_Id(utilizatorId, cursId) != null;

            if (!areAcces) {
                throw new SecurityException("Utilizatorul nu are permisiunea de a vizualiza acest curs.");
            }

            // Returnăm cursul
            return cursRepository.findById(cursId);
        }


    @Override
    public List<UtilizatorCurs> urmaresteProgresAngajati(Integer cursId, Integer utilizatorId) {
        Utilizator utilizator = utilizatorRepository.findById(utilizatorId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizatorul nu există."));
        if (utilizator.getTipUtilizator() != TipUtilizator.MANAGER) {
            throw new SecurityException("Doar Managerii pot urmări progresul angajaților.");
        }
        if (!cursRepository.existsById(cursId)) {
            throw new IllegalArgumentException("Cursul nu există.");
        }
        return utilizatorCursRepository.findByCurs_Id(cursId);
    }

    @Override
    public UtilizatorCurs urmaresteProgresPropriu(Integer cursId, Integer utilizatorId) {
        return utilizatorCursRepository.findByUtilizator_UserIdAndCurs_Id(utilizatorId, cursId);
    }

    @Override
    public Badge genereazaBadgePentruCurs(Integer cursId, Integer utilizatorId) {
        UtilizatorCurs utilizatorCurs = utilizatorCursRepository.findByUtilizator_UserIdAndCurs_Id(utilizatorId, cursId);
        if (utilizatorCurs == null || !utilizatorCurs.isCompletat()) {
            throw new IllegalStateException("Cursul nu a fost completat, badge-ul nu poate fi generat.");
        }

        Badge badge = new Badge();
        badge.setTitlu("Badge pentru " + utilizatorCurs.getCurs().getTitlu());
        badge.setCurs(utilizatorCurs.getCurs());
        return badgeRepository.save(badge);
    }
}
