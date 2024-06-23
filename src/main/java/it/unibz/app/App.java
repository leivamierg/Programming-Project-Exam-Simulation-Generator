package it.unibz.app;

import it.unibz.controller.Controller;
import it.unibz.model.implementations.Model;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )
    {
        Controller controller = new Controller(new Model());
        controller.elaborateArgs(args);
    }
}
