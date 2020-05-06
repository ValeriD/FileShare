package org.elsysbg.fileshare.controllers;


import org.elsysbg.fileshare.models.MyUserDetails;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.services.files.FileService;
import org.elsysbg.fileshare.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class FileUploadingController {
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes){


    }
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public void renameFile(@RequestParam("name") String name,@RequestParam("id") Long id, RedirectAttributes attributes){
        fileService.rename(id,name);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteFile(@RequestParam("id") Long id, RedirectAttributes attributes) {
            fileService.delete(id);
    }

    @RequestMapping(value = "/getfile/{filename:.+}", method = RequestMethod.GET)
    public void getFail(HttpServletRequest request) throws Exception {
        String file = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/')+1, request.getRequestURI().length());
        if(fileService.findByName(file).isPresent()){
            fileService.findByParent(fileService.findByName(file).get());
        }else {
            throw new Exception("File not found");
        }
    }


    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        Optional<User> user = userService.findByUsername(myUserDetails.getUsername());
        return user.get();
    }

}
