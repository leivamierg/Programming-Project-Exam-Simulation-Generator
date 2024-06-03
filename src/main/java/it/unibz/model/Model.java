package it.unibz.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.unibz.app.*; // Import the Topic class

public class Model implements ModelInt {

    private static final String BANK = "src/main/resources";
    private List<Topic> topics = new ArrayList<Topic>();
    private Map<Question, String> questionAnswerr = new HashMap<Question, String>();

    public Model() {
        FileLoadingInt fileLoading = new FileLoading(); // TODO: putt implementation of Fileloading
        topics = fileLoading.loadBank(BANK);
    }

    @Override
    public void list() {
        for (Topic topic : topics) {
            System.out.println(topic.getName());
        }
    }

    @Override
    public void listSubtopics(String topic) {
        Topic selectedTopic = null;
        for (Topic t : topics) {
            if (t.getName().equals(topic)) {
                selectedTopic = t;
                for (Subtopic subtopic : t.getSubtopics()) {
                    System.out.println((char) ('A' + t.getSubtopics().indexOf(subtopic)) + ". " + subtopic.getName());
                }
            }
        }

        String selected = "";

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter the letters corresponding to the subtopics: ");
            String input = scanner.nextLine();
            
            if (input.matches("[A-Z]+")) {
                validInput = true;
                String[] selectedSubtopics = input.split("");
                for (String subtopicIndex : selectedSubtopics) {
                    int index = subtopicIndex.charAt(0) - 'A';
                    if (index >= 0 && index < selectedTopic.getSubtopics().size()) {
                        Subtopic selectedSubtopic = selectedTopic.getSubtopics().get(index);
                        selected += selectedSubtopic.getName() + ", ";
                    }
                }
            } else {
                System.out.println("Invalid input. Please enter letters only.");
            }
        }
        scanner.close();

        this.test(topic, selected);
    
    }

    @Override
    public void transcribe(String path, boolean verbose) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transcribe'");
    }

    @Override
    public void notes(String path, boolean verbose) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notes'");
    }
    
}
