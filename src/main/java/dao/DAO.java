package dao;

import model.Archive;
import model.File;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class DAO {

    public abstract ArrayList<Archive> getArchives();

    public abstract ArrayList<File> getFiles(int archiveId);

    public abstract void insert(Archive archive);

    public abstract void update(Archive archive);

    public abstract Archive getArchiveById(int archiveId);

    public abstract void deleteArchive(int archiveId);

    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("MMM dd HH:mm:ss yyyy");
        try {
            return format1.parse(date);
        } catch (ParseException e) {
            var temp = date.split(" ");
            date = temp[1] + " " + temp[2] + " " + temp[3] + " " + temp[5];
            return format1.parse(date);
        }
    }
}
