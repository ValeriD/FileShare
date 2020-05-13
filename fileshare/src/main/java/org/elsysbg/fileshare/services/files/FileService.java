package org.elsysbg.fileshare.services.files;


import org.elsysbg.fileshare.dto.FileDto;
import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public interface FileService {

    File rename(String id, String name);
    boolean delete(String id);
    boolean moveFile(String id, String folder, User user);
    Long saveFile(MultipartFile file, User user) throws IOException;
    boolean addParent(Long fileId, Long parentId);
    Long saveDir(String name, User user, Long parentId);
    FileDto getFiles(User user, String id);

}
