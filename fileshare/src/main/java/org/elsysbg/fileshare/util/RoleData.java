package org.elsysbg.fileshare.util;

import org.elsysbg.fileshare.models.Role;
import org.elsysbg.fileshare.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RoleData {


        @Autowired
        private  RoleRepository repo;

        @EventListener
        public void appReady(ApplicationReadyEvent event) {

            repo.save(new Role((long)1,"ROLE_USER"));
            repo.save(new Role((long)2,"ROLE_ADMIN"));
        }

}
