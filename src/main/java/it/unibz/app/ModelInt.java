package it.unibz.app;

public interface ModelInt {

    
    /**
     * Prints a list of all general topics known by the programm
     * 
     */
    void list();

    /**
     * Prints a list of all the subtopics of the provided topic
     * 
     * @param topic of witch you want to know the sobtopics
     */
    void listSubtopics(String topic);

    /**
     * Starts an interactive thest abbout the subtopic
     * 
     * @param topic the general topic
     * @param subtopic the subtopic the thest is about
     */
    void test(String topic, String subtopic);


    /**
     * The programm will print all supbopics with a letter in front of every Topic. 
     * The user than will select the subtopics by typing the corrisponing letters
     * into the terminal.
     * Once the user presses `Enter` the test with questions about the subtopics
     * will start.
     * 
     * @param topic
     */
    void testSubtopics(String topic);

}
