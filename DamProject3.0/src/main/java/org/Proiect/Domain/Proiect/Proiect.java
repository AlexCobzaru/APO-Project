package org.Proiect.Domain.Proiect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.Proiect.DTO.ProiectDTO;
import org.Proiect.Domain.Angajati.Echipa;

import jakarta.persistence.*;
import lombok.*;
import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.App.StatusProiect;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@JsonIgnoreProperties({"tasks", "echipe", "rapoarte", "hibernateLazyInitializer", "handler"})
public class Proiect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NotBlank(message = "Denumirea proiectului este obligatorie.")
    @Size(max = 100, message = "Denumirea proiectului nu poate depăși 100 de caractere.")
 private String denumire;

    @Size(max = 255, message = "Descrierea proiectului nu poate depăși 255 de caractere.")
    private String descriere;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProiect status;

    @Column(nullable = false)
    private Date dataIncepere;

    private Date dataFinalizare;

    @JsonManagedReference
    @OneToMany(mappedBy = "proiect", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Utilizator lider;

    @OneToMany(mappedBy = "proiect", cascade = CascadeType.ALL)
    private List<Raport> rapoarte;
    @OneToMany (mappedBy = "proiect", cascade = CascadeType.ALL)
    private List<Echipa> echipe;

    @Transient
    private int taskCount;

    @Override
    public String toString() {
        return "Proiect{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", descriere='" + descriere + '\'' +
                ", status=" + status +
                ", dataIncepere=" + dataIncepere +
                ", dataFinalizare=" + dataFinalizare +
                '}';
    }

    @PrePersist
    public void onPrePersist() {
        System.out.println(">>> JPA Trigger: @PrePersist - Salvare entitate nouă.");
        this.taskCount = (this.tasks == null) ? 0 : this.tasks.size();
    }

    @PreUpdate
    public void onPreUpdate() {
        System.out.println(">>> JPA Trigger: @PreUpdate - Actualizare entitate.");
        this.taskCount = (this.tasks == null) ? 0 : this.tasks.size();
    }

    @PreRemove
    public void onPreRemove() {
        System.out.println(">>> JPA Trigger: @PreRemove - Ștergere entitate.");
    }

    // === Mapper: Proiect -> ProiectDTO ===
    public ProiectDTO toDTO() {
        ProiectDTO dto = new ProiectDTO();
        dto.setId(this.id);
        dto.setDenumire(this.denumire);
        dto.setDescriere(this.descriere);
        dto.setStatus(this.status != null ? this.status.toString() : null);
        dto.setDataIncepere(this.dataIncepere);
        dto.setDataFinalizare(this.dataFinalizare);
        dto.setLiderId(this.lider != null ? this.lider.getUserId() : null);
        return dto;
    }

    // === Mapper: ProiectDTO -> Proiect ===
    public static Proiect fromDTO(ProiectDTO dto) {
        Proiect proiect = new Proiect();
        proiect.setId(dto.getId());
        proiect.setDenumire(dto.getDenumire());
        proiect.setDescriere(dto.getDescriere());
        proiect.setStatus(dto.getStatus() != null ? StatusProiect.valueOf(dto.getStatus()) : null);
        proiect.setDataIncepere(dto.getDataIncepere());
        proiect.setDataFinalizare(dto.getDataFinalizare());
        return proiect;
    }
}