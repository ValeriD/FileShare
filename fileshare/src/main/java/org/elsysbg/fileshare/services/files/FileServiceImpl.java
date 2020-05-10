package org.elsysbg.fileshare.services.files;

import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Optional<File> findById(Long aLong) {
        return fileRepository.findById(aLong);
    }

    @Override
    public Optional<File> findByName(String name) {
        return fileRepository.findByName(name);
    }

    @Override
    public Optional<File> findByParent(File file) {
        return fileRepository.findByParent(file);
    }

    @Override
    public Optional<File> findByBelongsTo(User user) {
        return fileRepository.findByBelongsTo(user);
    }

    @Override
    public File store(MultipartFile multipartFile, User user, Long parent) throws IOException {
        return null;
    }

    @Override
    public File rename(Long id, String name) {
        File file = fileRepository.findById(id).get();
        file.setName(name);
        return fileRepository.save(file);
    }

    @Override
    public void delete(Long id) {
        fileRepository.deleteById(id);
    }

    private File saveDir(MultipartFile multipartFile, User user, Long parent, File dir){
        dir.setBelongsTo(user);
        dir.setFileType("dir");
        dir.setName(multipartFile.getName());
        if(parent==0){
            dir.setParent(null);
        }else {
            dir.setParent(fileRepository.findById(parent).get());
        }
        return dir;
    }
    @Override
   public Long saveFile(MultipartFile file, User user) throws IOException {
        File uploadFile = new File();
        uploadFile.setName(file.getOriginalFilename());
        uploadFile.setBelongsTo(user);
        uploadFile.setData(file.getBytes());
        uploadFile.setFileType(file.getContentType());
        File uploadedFile = fileRepository.save(uploadFile);

        return uploadedFile.getId();
    }

    @Override
    public Long saveDir(String name, User user, Long parentId) {
        File dir = new File();
        dir.setName(name);
        dir.setFileType("dir");
        dir.setBelongsTo(user);
        dir.setParent(fileRepository.findById(parentId).get());
        File savedDir = fileRepository.save(dir);
        return savedDir.getId();
    }

    @Override
    public Set<File> getFiles(User user, String parentId) {
        File directory;
        if(parentId.equals("NaN")){

            directory = fileRepository.findByBelongsToAndParent(user,null).get();
        }else{
            directory = fileRepository.findById(Long.valueOf(parentId)).get();
        }
        return directory.getFiles();
    }


    @Override
    public boolean addParent(Long fileId, Long parentId) {
        if(fileRepository.findById(fileId).isPresent() && fileRepository.findById(parentId).isPresent()){
            File file = fileRepository.findById(fileId).get();
            file.setParent(fileRepository.findById(parentId).get());
            fileRepository.save(file);
            return true;
        }
        return false;
    }
}
