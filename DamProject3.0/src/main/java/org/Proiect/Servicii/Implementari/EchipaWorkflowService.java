package org.Proiect.Servicii.Implementari;

import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.App.TipUtilizator;
import org.Proiect.Domain.Repository.AppUserRepository;
import org.Proiect.Domain.Repository.EchipaRepository;
import org.Proiect.Servicii.IEchipaWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EchipaWorkflowService implements IEchipaWorkflowService {

    @Autowired
    private EchipaRepository echipaRepository;
    @Autowired
    private AppUserRepository utilizatorRepository;

    @Override
    public Echipa creeazaEchipa(String numeEchipa, Integer liderId) {
        Utilizator lider = utilizatorRepository.findById(liderId)
                .orElseThrow(() -> new IllegalArgumentException("Liderul nu există"));
        if (lider.getTipUtilizator() != TipUtilizator.LIDER) {
            throw new IllegalArgumentException("Doar liderii pot crea echipe");
        }

        Echipa echipa = new Echipa();
        echipa.setDenumire(numeEchipa);
        echipa.setLider(lider);
        return echipaRepository.save(echipa);
    }

    @Override
    public void adaugaMembruInEchipa(Integer echipaId, Integer userId) {
        Echipa echipa = echipaRepository.findById(echipaId)
                .orElseThrow(() -> new IllegalArgumentException("Echipa nu există"));
        Utilizator utilizator = utilizatorRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizatorul nu există"));

        utilizator.setEchipa(echipa);
        utilizatorRepository.save(utilizator);
    }

    @Override
    public void modificaEchipa(Integer echipaId, String numeNou) {
        Echipa echipa = echipaRepository.findById(echipaId)
                .orElseThrow(() -> new IllegalArgumentException("Echipa nu există"));

        echipa.setDenumire(numeNou);
        echipaRepository.save(echipa);
    }

    @Override
    @Transactional
    public void arhiveazaEchipa(Integer echipaId) {


            Echipa echipa = echipaRepository.findById(echipaId)
                    .orElseThrow(() -> new IllegalArgumentException("Echipa nu există"));

            if (echipa.isArhivata()) {
                throw new IllegalArgumentException("Echipa este deja arhivată");
            }

            echipa.setArhivata(true);
            echipaRepository.save(echipa);
    }

    @Override
    public Echipa vizualizeazaEchipa(Integer echipaId) {
        return echipaRepository.findById(echipaId)
                .orElseThrow(() -> new IllegalArgumentException("Echipa nu există"));
    }

    @Override
    public List<Utilizator> vizualizeazaMembriiEchipa(Integer echipaId) {
        Echipa echipa = echipaRepository.findById(echipaId)
                .orElseThrow(() -> new IllegalArgumentException("Echipa nu există"));
        return utilizatorRepository.findAllByEchipa(echipaId);
    }
}
