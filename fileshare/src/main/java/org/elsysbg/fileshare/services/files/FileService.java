package org.elsysbg.fileshare.services.files;


import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public interface FileService {
    Optional<File> findById(Long aLong);
    Optional<File> findByName(String name);
    Optional<File> findByParent(File file);
    Optional<File> findByBelongsTo(User user);
    File store(MultipartFile multipartFile, User user, Long parent) throws IOException;
    File rename(Long id, String name);
    void delete(Long id);
}
