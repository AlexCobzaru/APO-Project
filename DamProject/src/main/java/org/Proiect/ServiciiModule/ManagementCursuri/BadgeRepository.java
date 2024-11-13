package org.Proiect.ServiciiModule.ManagementCursuri;

import org.Proiect.Domain.Dezvoltare.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    List<Badge> findByCursId(Long cursId);
}
