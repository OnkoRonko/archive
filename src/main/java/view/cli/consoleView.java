package view.cli;

import dao.dbDAOImpl.dbDAOImpl;
import model.Archive;
import model.File;
import businessLogic.ArchiveAPI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class consoleView {

    private static ArchiveAPI controller = new ArchiveAPI(new dbDAOImpl());

    public static void main(String[] args) {
        view();
//        update();
//        delete();
//        add();
    }

    private static void view() {
        controller.getArchives().forEach(System.out::println);
    }

    //add files to archive (also for delete)
    private static void update() {
        System.out.println("До изменения:");
        System.out.println(controller.getArchiveById(1));

        controller.addFile(1, new File("kek", new Date()));
        System.out.println("После измненения");
        System.out.println(controller.getArchiveById(1));

    }

    private static void delete() {
        System.out.println("До изменения:");
        System.out.println("Сейчас архивов: " + controller.getArchives().size());

        controller.deleteArchive(3);
        System.out.println("После удаления");
        System.out.println("Сейчас архивов: " + controller.getArchives().size());
    }

    private static void add() {
        System.out.println("До изменения:");
        System.out.println("Сейчас архивов: " + controller.getArchives().size());

        controller.addArchive(new Archive("new Archive1",
            new Date(100000),
            List.of(
                new File("file11", new Date()),
                new File("file22", new Date(100000000)))));
        System.out.println("После добавления нового архива:");
        System.out.println("Сейчас архивов: " + controller.getArchives().size());
    }
}
