package it.unibz.model.interfaces;

public interface ModelInt {

    
    /**
     * Prints a list of all general topics known by the programm
     * 
     * private
     * @param topic of witch you want to know the sobtopics
     */
    void listSubtopics(String topic);

    /**
     * Starts an interactive thest abbout the subtopic
     * if subtopic is null it will start a test of the topic with all subtobics.
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

    /**
     * Takes the video or audeo file (mp3 or mp4) pointed to by path and generates a 
     * transcription of the audio not taking into consideration the vidio.
     * it supports English, German and Italian. 
     * set 'verbose' to true to print the transcription to the terminal.
     * 
     * the transcriptions filename will be the same as the file pointed to by path.
     * the file will be stored in the current directory
     * 
     * @param path to an audio or video file
     * @param verbose prints the transcription to the terminal if true
     */
    void transcribe(String path, boolean verbose);

    /**
     * makes notes based on the file in path. the notes will be written in the markup format.
     * the notes are generated by a LLM. 
     * 
     * the notes filename will be the same as the filename in path.
     * the file will be stored in the current directory.
     * 
     * @param path to text file 
     * @param verbose prints notes to terminal if true
     */
    void notes(String path, boolean verbose);

}
