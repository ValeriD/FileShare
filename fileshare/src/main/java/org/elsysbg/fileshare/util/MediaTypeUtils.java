package org.elsysbg.fileshare.util;

import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

public class MediaTypeUtils {
    public static MediaType getMediaTypeForFileName(String mineType) {

        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
