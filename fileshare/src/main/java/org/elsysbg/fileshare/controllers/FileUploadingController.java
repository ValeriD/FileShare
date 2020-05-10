package org.elsysbg.fileshare.controllers;


import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.MyUserDetails;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.repositories.FileRepository;
import org.elsysbg.fileshare.services.files.FileService;
import org.elsysbg.fileshare.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class FileUploadingController {
    @Autowired
    FileService fileService;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file ){
        Long id;
        try {
            id = fileService.saveFile(file,getCurrentUser());
        } catch (IOException e) {
            return new ResponseEntity<>("Invalid file format!!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(String.valueOf(id), HttpStatus.OK);

    }


    @RequestMapping(value = "/addParent", method = RequestMethod.POST)
    public ResponseEntity<String> addParent(@RequestParam(value = "fileId") String fileId, @RequestParam(value = "parentId") String parentId){
       if(parentId.equals("NaN")){
          fileService.delete(Long.valueOf(fileId));
           return new ResponseEntity<String>("File not uploaded", HttpStatus.BAD_REQUEST);
       }
        if(!fileService.addParent(Long.valueOf(fileId), Long.valueOf(parentId))){
            return new ResponseEntity<String>("File not uploaded", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("File uploaded", HttpStatus.OK);
    }


    @RequestMapping(value = "/uploadFolder", method = RequestMethod.POST)
    public ResponseEntity<String>uploadFolder(@RequestParam(value = "name") String name, @RequestParam(value = "parentId") String parentId){
        if(parentId.equals("NaN")){
            return new ResponseEntity<String>("Folder not uploaded", HttpStatus.BAD_REQUEST);
        }
        if (fileService.saveDir(name,getCurrentUser(), Long.valueOf(parentId))==null){
            return new ResponseEntity<String>("Folder not uploaded", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Folder uploaded", HttpStatus.OK);
    }
    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public @ResponseBody Set<File> getFiles(@RequestParam(value = "parentId") String parentId){



        Set<File> files = fileService.getFiles(getCurrentUser(), parentId);

        return files;

    }

    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public void renameFile(@RequestParam("name") String name,@RequestParam("id") Long id, RedirectAttributes attributes){
        fileService.rename(id,name);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteFile(@RequestParam("id") Long id, RedirectAttributes attributes) {
            fileService.delete(id);
    }


    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        Optional<User> user = userService.findByUsername(myUserDetails.getUsername());
        return user.get();
    }

}
