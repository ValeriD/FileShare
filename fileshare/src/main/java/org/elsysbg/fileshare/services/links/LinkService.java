package org.elsysbg.fileshare.services.links;

import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.Link;
import org.springframework.stereotype.Service;

@Service
public interface LinkService {
    boolean addLink(String id, String url);
    boolean removeLink(String id);
    Link getLink(String name);
    File getFileByUrl(String name);
}
