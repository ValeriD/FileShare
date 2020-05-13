package org.elsysbg.fileshare.dto;

import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.Link;

import java.util.Set;

public class FileDto {
    private File parent;
    private Set<File> files;
    private Set<Link> links;

    public File getParent() {
        return parent;
    }

    public void setParent(File parent) {
        this.parent = parent;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }
}
