import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Задание №2
        GameProgress gameProgress01 = new GameProgress(1000, 5, 100, 8);
        GameProgress gameProgress02 = new GameProgress(100, 1, 10, 2);
        GameProgress gameProgress03 = new GameProgress(600, 3, 80, 4);

        //Сохранение
        String path01 = "D://ТЕМП//Games//savegames//safe1.data";
        String path02 = "D://ТЕМП//Games//savegames//safe2.data";
        String path03 = "D://ТЕМП//Games//savegames//safe3.data";
        gameProgress01.saveGame(path01);
        gameProgress01.saveGame(path02);
        gameProgress01.saveGame(path03);

        //Архивация и разархивация
        String zipFilePath = "D://ТЕМП//Games//savegames//data.zip";
        String FilePath = "D://ТЕМП//Games//savegames//";
        //Архивация
        List<String> filesToZip = List.of(path01, path02, path03);
        GameProgress.zipFiles(zipFilePath, filesToZip);

        //Задание №3
        //Открвыаем ZIP
        GameProgress.openZip(zipFilePath, FilePath);
        //Читаем файл
        System.out.println(GameProgress.openProgress(path01));
    }
}