package org.elsysbg.fileshare.repositories;

import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findById(Long aLong);
    Optional<File> findByName(String name);
    Optional<File> findByParent(File file);
    Optional<File> findByBelongsTo(User user);
    Optional<File> findByBelongsToAndParent(User user, File file);
    Optional<File> findByNameAndBelongsTo(String name, User user);
    Optional<File> findByIdAndBelongsTo(Long id, User user);
}
