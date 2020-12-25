package dao.dbDAOImpl;

import dao.DAO;
import model.Archive;
import model.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;

public class dbDAOImpl extends DAO {
    private Properties crud = new Properties();
    private final ConnectionFactory provider = new PostgresConnectionImpl();

    public dbDAOImpl() {
        try {
            crud.load(new FileInputStream("src/main/resources/crud.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Archive> getArchives() {
        var archives = new ArrayList<Archive>();
        try (var con = provider.getConnection();
             var set = con.createStatement().executeQuery(crud.getProperty("getAllArchives"))) {

            ArrayList<File> files;
            Archive archive;
            while (set.next()) {
                archive = getArchive(set);
                archive.setFiles(getFiles(archive.id()));
                archives.add(archive);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return archives;
    }

    private Archive getArchive(ResultSet set) throws SQLException, ParseException {
        return new Archive(set.getInt("archive_id"),
            set.getString("archive_name"),
            parseDate(set.getString("creation_date")));
    }

    @Override
    public ArrayList<File> getFiles(int archiveId) {
        var files = new ArrayList<File>();
        try (var con = provider.getConnection();
             var stm = con.prepareStatement(crud.getProperty("getAllFiles"))) {

            stm.setInt(1, archiveId);
            var set = stm.executeQuery();
            while (set.next()) {
                files.add(getFile(set));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return files;
    }

    private File getFile(ResultSet set) throws SQLException, ParseException {
        return new File(set.getInt("file_id"),
            set.getString("file_name"),
            parseDate(set.getString("creation_date")));
    }

    @Override
    public void insert(Archive archive) {
        try (var con = provider.getConnection();
             var archStm = con.prepareStatement(crud.getProperty("insertArchive"), new String[]{"archive_id"});
             var fileStm = con.prepareStatement(crud.getProperty("insertFiles"))) {

            archStm.setString(1, archive.getName());
            archStm.setString(2, String.valueOf(archive.getCreationDate()));

            archStm.executeUpdate();
            var set = archStm.getGeneratedKeys();
            var archive_id = "null";
            while (set.next())
                archive_id = set.getString("archive_id");
            for (File file : archive.getFiles()) {
                fileStm.setString(1, file.name());
                fileStm.setString(2, String.valueOf(file.getCreationDate()));
                fileStm.setInt(3, Integer.parseInt(archive_id));
                fileStm.addBatch();
            }
            fileStm.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Archive archive) {
        try (var con = provider.getConnection();
             var archStm = con.prepareStatement(crud.getProperty("update"))) {

            archStm.setString(1, archive.getName());
            archStm.setString(2, String.valueOf(archive.getCreationDate()));
            archStm.setInt(3, archive.id());

            deleteFiles(archive.id());
            archStm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Archive getArchiveById(int archiveId) {
        Archive archive = null;
        try (var con = provider.getConnection();
             var set = con.createStatement().executeQuery(crud.getProperty("getById"))) {

            set.next();
            archive = getArchive(set);
            archive.setFiles(getFiles(archive.id()));

        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return archive;
    }

    @Override
    public void deleteArchive(int archiveId) {
        try (var con = provider.getConnection();
             var stm = con.prepareStatement(crud.getProperty("deleteArchive"))) {
            stm.setInt(1, archiveId);
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteFiles(int archiveId) {
        try (var con = provider.getConnection();
             var stm = con.prepareStatement(crud.getProperty("deleteFiles"))) {
            stm.setInt(1, archiveId);
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
