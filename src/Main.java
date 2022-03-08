import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static final String PATH = "C:/" +
            "/Users" +
            "/poddu" +
            "/IdeaProjects" +
            "/myHomeworksNetology" +
            "/Java Core" +
            "/Homework3InputOutputStreamsFilesSerialize" +
            "/Homework1.3.1setup" +
            "/Games" +
            "/savegames";


    public static void main(String[] args) {

        List<String> savesList = openZip(PATH + "/zip.zip", PATH);
        if (!savesList.isEmpty()) {
            openProgress(savesList.get(1));
        }
    }

    public static List<String> openZip(String pathZip, String pathDir) {
        List<String> savesList = new ArrayList<>();
        String saveFullName;
        ZipEntry entry;
        int data;
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(pathZip))) {
            while ((entry = zipInputStream.getNextEntry()) != null) {
                saveFullName = pathDir + "/" + entry.getName();
                try (FileOutputStream saveFile = new FileOutputStream(saveFullName)) {
                    while ((data = zipInputStream.read()) != -1) {
                        saveFile.write(data);
                    }
                    saveFile.flush();
                    savesList.add(saveFullName);
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
                zipInputStream.closeEntry();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return savesList;
    }

    public static void openProgress(String savePath) {
        GameProgress gameProgress = null;
        File saveFile = new File(savePath);
        if (saveFile.exists() && saveFile.canRead()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(savePath))) {
                gameProgress = (GameProgress) objectInputStream.readObject();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        } else System.out.println("Не могу найти файл или он недоступен для чтения");
        System.out.println(gameProgress);
    }
}