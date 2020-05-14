package org.elsysbg.fileshare.services.links;

import org.elsysbg.fileshare.dto.FileDto;
import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.Link;
import org.elsysbg.fileshare.repositories.FileRepository;
import org.elsysbg.fileshare.repositories.LinkRepository;
import org.elsysbg.fileshare.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;


@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    LinkRepository linkRepository;

    @Autowired
    FileRepository fileRepository;


    @Override
    public boolean addLink(String id, String url) {
        Link link = new Link();
        if(fileRepository.findById(Long.valueOf(id)).isPresent()){
            link.setFile(fileRepository.findById(Long.valueOf(id)).get());
            if(link.getFile().getFileType().equals("dir")){
                link.setLink(generateLink(url, true));
            }else{
                link.setLink(generateLink(url, false));
            }

            linkRepository.save(link);
            return true;
        }

        return false;
    }

    private String generateLink(String url, boolean isDir) {
        if(isDir){
            url+="/api/folder/";
        }else{
            url += "/api/file/";
        }
        String token = RandomUtil.generateRandomStringNumber(10).toUpperCase();
        return url+token;
    }

    @Override
    public boolean removeLink(String id) {
        if(linkRepository.findById(Long.valueOf(id)).isPresent()){
            linkRepository.delete(linkRepository.findById(Long.valueOf(id)).get());
            return true;
        }
        return false;
    }

    @Override
    public Link getLink(String name) {
        if(linkRepository.findByLink(name).isPresent()){
            return linkRepository.findByLink(name).get();
        }
        return null;
    }

    @Override
    public File getFileByUrl(String name) {
        Link link = getLink(name);
        if(link!=null){
            return link.getFile();
        }
        return null;
    }

    @Override
    public FileDto getFolderByUrl(String url) {
        File file = getFileByUrl(url);
        FileDto fileDto = new FileDto();
        fileDto.setParent(file);
        fileDto.setFiles(file.getFiles());
        return fileDto;
    }
}
