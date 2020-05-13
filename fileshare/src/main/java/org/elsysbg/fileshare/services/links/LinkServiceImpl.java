package org.elsysbg.fileshare.services.links;

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
            link.setLink(generateLink(url));
            linkRepository.save(link);
            return true;
        }

        return false;
    }

    private String generateLink(String url) {
         url += "/api/file/";
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
}
