package org.app;
import org.Proiect.App.Proiect;
import org.Proiect.ProiectRepository;
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