package org.app;
import org.example.Proiect;
import org.example.ProiectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProiectRepositoryTest {

    @Autowired
    private ProiectRepository proiectRepository;

    @Test
    public void testSaveProiect() {
        Proiect proiect = new Proiect();
        proiect.setDenumire("Proiect Test");
        proiectRepository.save(proiect);

        assertThat(proiectRepository.findById(proiect.getId())).isPresent();
    }
}