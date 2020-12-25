package model;

import java.util.Date;

public class File {
    private int id = -1;
    private String name;
    private final Date creationDate;

    public File(String name, Date creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public File(int id, String name, Date creationDate) {
        this.name = name;
        this.creationDate = creationDate;
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int id() {
        return id;
    }

    @Override
    public String toString() {
        return "File{" +
            "name='" + name + '\'' +
            ", creationDate=" + creationDate +
            '}';
    }
}
