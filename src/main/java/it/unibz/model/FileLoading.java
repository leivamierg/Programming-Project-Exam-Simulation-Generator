package it.unibz.model;

import com.sun.tools.javac.Main;
import it.unibz.app.App;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileLoading implements FileLoadingInt {
    // attributes
    // methods
    // loadFile
    @Override
    public Topic loadFile(String fileName) {
        return null;
    }
    // load
    @Override
    public List<Topic> load(String bankPath) {
        List<String> fileNames = new ArrayList<>();

        File folder = new File(bankPath);

        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".csv"))
                fileNames.add(file.getName());
        }
        List<Topic> bank = new ArrayList<>();
        for (String fileName : fileNames) {
            bank.add(loadFile(bankPath + fileName));
        }
        return bank;
        /*Path path = new File(
                Main.class.getResource(bankPath + "la_bank.csv").toURI()
        ).getParentFile().toPath();
        try {
            List<String> bank = Files.walk(path)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(n -> n.endsWith(".csv"))
                    .toList();
            System.out.println(bank);
        } catch (IOException unfoundedPath) {
            System.out.println("Unfounded bank");
            throw unfoundedPath;
        }*/
    }
}
