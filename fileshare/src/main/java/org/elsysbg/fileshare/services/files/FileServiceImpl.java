package org.elsysbg.fileshare.services.files;

import org.elsysbg.fileshare.dto.FileDto;
import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileRepository fileRepository;


    @Override
    public File rename(String id, String name) {
        if(fileRepository.findById(Long.valueOf(id)).isPresent()){
            File file = fileRepository.findById(Long.valueOf(id)).get();
            file.setName(name);
            return fileRepository.save(file);
        }
        return null;

    }

    @Override
    public boolean delete(String id) {
        if (fileRepository.findById(Long.valueOf(id)).isPresent()) {
            fileRepository.deleteById(Long.valueOf(id));
            return true;
        }
        return false;
    }

    @Override
    public boolean moveFile(String id, String folder, User user) {
        if(fileRepository.findByNameAndBelongsTo(folder, user).isPresent()){
            File dir = fileRepository.findByNameAndBelongsTo(folder,user).get();
            if(dir.getFileType().equals("dir") && fileRepository.findById(Long.valueOf(id)).isPresent()){
                File file = fileRepository.findById(Long.valueOf(id)).get();
                file.setParent(dir);
                fileRepository.save(file);
                return true;
            }
        }
        return false;


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
        if(parentId==null){
            dir.setParent(null);
        }
        else if(!fileRepository.findById(parentId).isPresent()){
            return (long)0;
        }
        else {
            dir.setParent(fileRepository.findById(parentId).get());
        }
        File savedDir = fileRepository.save(dir);
        return savedDir.getId();
    }

    @Override
    public FileDto getFiles(User user, String parentId) {
        File file;
        if(parentId.equals("NaN")){
            file = fileRepository.findByBelongsToAndParent(user,null).get();
        }else{
            file = fileRepository.findByIdAndBelongsTo(Long.valueOf(parentId), user).get();
        }
        FileDto fileDto = new FileDto();
        fileDto.setParent(file);
        fileDto.setLinks(file.getLinks());
        fileDto.setGrandparentId(file.getParent());
        if(file.getFileType().equals("dir")) {
            fileDto.setFiles(file.getFiles());
        }
        return fileDto;
    }


    @Override
    public boolean addParent(Long fileId, Long parentId) {
        if(fileRepository.findById(fileId).isPresent() && fileRepository.findById(parentId).isPresent() && fileRepository.findById(parentId).get().getFileType().equals("dir")){
            File file = fileRepository.findById(fileId).get();
            file.setParent(fileRepository.findById(parentId).get());
            fileRepository.save(file);
            return true;
        }
        return false;
    }
}
