package com.caprispine.caprispine.pojo;

/**
 * Created by sunil on 20-12-2017.
 */

public class MultipleFileUploadPOJO {
    String file;
    String thumb;
    String type;

    public MultipleFileUploadPOJO(String file, String thumb, String type) {
        this.file = file;
        this.thumb = thumb;
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
