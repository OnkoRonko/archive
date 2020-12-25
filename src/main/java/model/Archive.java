package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Archive {
    private int id = -1;
    private List<File> files;
    private String name;
    private Date creationDate;

    public Archive(String name, Date creationDate) {
        this.name = name;
        this.creationDate = creationDate;
        this.files = new ArrayList<>();
    }

    public Archive(int id, String name, Date creationDate) {
        this.name = name;
        this.creationDate = creationDate;
        this.id = id;
        files = new ArrayList<>();
    }

    public Archive(String name, Date creationDate, List<File> files) {
        this.name = name;
        this.creationDate = creationDate;
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int id() {
        return id;
    }

    //only for CLI
    public void setId(int id) {
        this.id = id;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Archive{" +
            "id=" + id +
            ", files=" + files.toString() +
            ", name='" + name + '\'' +
            ", creationDate=" + creationDate +
            '}';
    }
}