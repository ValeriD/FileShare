package org.elsysbg.fileshare.services.files;

import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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
        File file = new File();
        if(multipartFile.getContentType().equals("dir")) {
            file = this.saveDir(multipartFile,user, parent, file);
        }
        else{
            file = this.saveFile(multipartFile, user, parent, file);
        }
        return fileRepository.save(file);
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

    private File saveFile(MultipartFile multipartFile, User user, Long parent, File file) throws IOException {
        file.setName(multipartFile.getName());
        file.setFileType("file");
        file.setData(multipartFile.getBytes());
        file.setParent(fileRepository.findById(parent).get());
        file.setBelongsTo(user);
        return file;
    }
}
