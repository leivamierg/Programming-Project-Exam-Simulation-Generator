package it.unibz.app;

import it.unibz.model.FileLoading;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, URISyntaxException {
        System.out.println( "Hello World!" );
        FileLoading fl = new FileLoading();
        List<Topic> bank = fl.load("src/main/java/it/unibz/bank/");
    }
}
