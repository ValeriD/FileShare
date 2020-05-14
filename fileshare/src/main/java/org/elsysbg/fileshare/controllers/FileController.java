package org.elsysbg.fileshare.controllers;


import org.elsysbg.fileshare.dto.FileDto;
import org.elsysbg.fileshare.models.MyUserDetails;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.services.files.FileService;
import org.elsysbg.fileshare.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/api")
public class FileController {

    @Autowired
    FileService fileService;

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
          fileService.delete(fileId);
           return new ResponseEntity<String>("File not uploaded", HttpStatus.BAD_REQUEST);
       }
       System.out.println(Long.valueOf(fileId)+ " " + Long.valueOf(parentId));
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
        if (fileService.saveDir(name,getCurrentUser(), Long.valueOf(parentId))==0){
            return new ResponseEntity<String>("Folder not uploaded", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Folder uploaded", HttpStatus.OK);
    }

    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public @ResponseBody FileDto getFiles(@RequestParam(value = "parentId") String parentId){
        return fileService.getFiles(getCurrentUser(), parentId);
    }

    @RequestMapping(value ="/moveFile", method = RequestMethod.POST)
    public ResponseEntity<String> moveFile(@RequestParam(value = "id") String id, @RequestParam(value="parentName") String parentName){
        if(fileService.moveFile(id, parentName, getCurrentUser())){
            return new ResponseEntity<String>("File moved successfully", HttpStatus.OK);
        }
        return new ResponseEntity<String>("File not moved", HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public ResponseEntity<String> renameFile( @RequestParam("id") String id, @RequestParam("name") String name){
        if(fileService.rename(id,name)!=null){
            return new ResponseEntity<String>("File renamed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<String>("File not renamed", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFile(@RequestParam("id") String id) {

        if(fileService.delete(id)){
            return new ResponseEntity<String>("File deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<String>("File not deleted", HttpStatus.BAD_REQUEST);
    }


    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        Optional<User> user = userService.findByUsername(myUserDetails.getUsername());
        return user.get();
    }

}
