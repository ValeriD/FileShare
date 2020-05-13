package org.elsysbg.fileshare.controllers;

import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.services.links.LinkService;
import org.elsysbg.fileshare.util.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping(value = "/api")
public class LinkController {

    @Autowired
    LinkService linkService;

    @RequestMapping(value = "/addLink", method = RequestMethod.POST)
    public ResponseEntity<String> addLink(@RequestParam(name = "id") String id, ServletRequest request) throws UnknownHostException {

        String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + request.getLocalPort();
        if(linkService.addLink(id, url)){
            return new ResponseEntity<String>("Successfully added a link", HttpStatus.OK);
        }
        return new ResponseEntity<>("Link not added", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/deleteLink", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteLink(@RequestParam(name = "id") String id){
        if(linkService.removeLink(id)){
            return new ResponseEntity<String>("Successfully deleted a link", HttpStatus.OK);
        }
        return new ResponseEntity<>("Link not added", HttpStatus.BAD_REQUEST);
    }
    @RequestMapping(value = "/file/*", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> downloadFile(HttpServletRequest request){

        File file = linkService.getFileByUrl(request.getRequestURL().toString());
        ByteArrayResource resource = new ByteArrayResource(file.getData());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaTypeUtils.getMediaTypeForFileName(file.getFileType()))
                .contentLength(file.getData().length)
                .body(resource);
    }

}
