import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    //Конструктор
    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    //Архивация
    public static void zipFiles(String zipFilePath, List<String> filesToZip) {

        try {

            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (String filePath : filesToZip) {
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                fis.close();
                zos.closeEntry();
            }
            zos.close();
            fos.close();
            System.out.println("Архив создан");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Удаление файлов сохранений, не лежащих в архиве
        for (String filePath : filesToZip) {
            File file = new File(filePath);
            if (file.exists() && !file.getAbsolutePath().equals(zipFilePath)) {
                file.delete();
            }
        }
    }

    //Открытие архива
    public static void openZip(String zipFilePath, String extractFolder) {
        try {
            FileInputStream fileInputStream = new FileInputStream(zipFilePath);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                File file = new File(extractFolder, entry.getName());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.close();
                zipInputStream.closeEntry();
                entry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Чтение с файла
    public static GameProgress openProgress(String progressFilePath) {
        GameProgress gameProgress = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(progressFilePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            gameProgress = (GameProgress) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameProgress;
    }

    //Сохранение
    public void saveGame(String path) {

        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }
}