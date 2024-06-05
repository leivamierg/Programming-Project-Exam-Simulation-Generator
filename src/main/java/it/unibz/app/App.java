package it.unibz.app;

import it.unibz.controller.Controller;
import it.unibz.model.ModelInt;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )
    {
        ModelInt model = new ModelImpl;
        Controller controller = new Controller(model);
        controller.elaborateArgs(args);
    }
}
