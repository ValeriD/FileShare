package org.elsysbg.fileshare.repositories;

import org.elsysbg.fileshare.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findById(Long id);
    Optional<Link> findByLink(String link);


}
