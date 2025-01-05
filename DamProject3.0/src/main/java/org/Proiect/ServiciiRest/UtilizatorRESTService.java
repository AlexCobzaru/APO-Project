package org.Proiect.ServiciiRest;

import org.Proiect.DTO.UtilizatorDTO;
import org.Proiect.Domain.App.TipUtilizator;
import org.Proiect.Servicii.Repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/utilizatori")
@Transactional
public class UtilizatorRESTService {

    private static final Logger logger = Logger.getLogger(UtilizatorRESTService.class.getName());

    @Autowired
    private AppUserRepository utilizatorRepository;

    @Autowired
    private ModelMapper modelMapper;

    // === GET: Toți utilizatorii ===
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UtilizatorDTO> getAllUtilizatori() {
        logger.info("Fetching all users...");
        return utilizatorRepository.findAll().stream()
                .map(utilizator -> modelMapper.map(utilizator, UtilizatorDTO.class))
                .collect(Collectors.toList());
    }

    // === GET: Lideri ===

    @GetMapping(value = "/lideri", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UtilizatorDTO> getAllLideri() {
        logger.info("Fetching all leaders...");
        return utilizatorRepository.findAllByTipUtilizator(TipUtilizator.LIDER).stream()
                .map(utilizator -> modelMapper.map(utilizator, UtilizatorDTO.class))
                .collect(Collectors.toList());
    }


    // === GET: Utilizator după ID ===

    @GetMapping(value = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilizatorDTO> getUtilizatorById(@PathVariable int id) {
        logger.info("Fetching user with ID: " + id);
        return utilizatorRepository.findById(id)
                .map(utilizator -> modelMapper.map(utilizator, UtilizatorDTO.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
