package it.unibz.model.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PdfGenerator {

    private ObjectMapper mapper = new ObjectMapper();

    public void transformJsonIntoPDF(String jsonFilePath, String filePath)
    {
        PDDocument document = new PDDocument();
        PDPage pagePDF = new PDPage();
        document.addPage(pagePDF);

        PDPageContentStream pageContent = null;

        try {
            JsonNode rootNode = mapper.readTree(new File(jsonFilePath));
            pageContent = new PDPageContentStream(document, pagePDF);
            int yPosition = 750; //to revise
            int xPosition = 10; //to revise

            String topicName = rootNode.get("topicName").asText();
            pageContent.beginText();
            pageContent.setFont(PDType1Font.HELVETICA_BOLD, 20);
            pageContent.newLineAtOffset(xPosition, yPosition);
            pageContent.showText(replaceCharacters("Topic: " + topicName));
            pageContent.endText();
            yPosition -= 30;

            for (JsonNode topicNode : rootNode.get("subtopics"))
            {
                String subtopicName = topicNode.get("subtopicName").asText();

                pageContent.beginText();
                pageContent.setFont(PDType1Font.HELVETICA_BOLD, 16);
                pageContent.newLineAtOffset(xPosition, yPosition);
                pageContent.showText(replaceCharacters("Subtopic: " + subtopicName));
                pageContent.endText();
                yPosition -= 30;

                for (JsonNode questionNode : topicNode.get("questions"))
                {
                    if (yPosition < 50)
                    {
                        pageContent.close();
                        pagePDF = new PDPage();
                        document.addPage(pagePDF);
                        pageContent = new PDPageContentStream(document, pagePDF);
                        yPosition = 750;
                    }

                    String question = questionNode.get("questionStatement").asText();
                    pageContent.beginText();
                    pageContent.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    pageContent.newLineAtOffset(xPosition, yPosition);
                    pageContent.showText(replaceCharacters("Q: " + question));
                    pageContent.endText();
                    yPosition -= 35;

                    List<String> answers = new ArrayList<>();

                    for (JsonNode wrongAnswerNode : questionNode.get("wrongAnswers"))
                    {
                        answers.add(wrongAnswerNode.asText());
                    }
                    answers.add(questionNode.get("rightAnswer").asText());

                    Collections.shuffle(answers);

                    char label = 'A';
                    for (String answer : answers)
                    {
                        pageContent.beginText();
                        pageContent.setFont(PDType1Font.HELVETICA, 10);
                        pageContent.newLineAtOffset(xPosition, yPosition);
                        pageContent.showText(replaceCharacters(label + ": " + answer));
                        pageContent.endText();
                        yPosition -= 20;
                        label++;

                        if (yPosition < 50)
                        {
                            pageContent.close();
                            pagePDF = new PDPage();
                            document.addPage(pagePDF);
                            pageContent = new PDPageContentStream(document, pagePDF);
                            yPosition = 750;
                        }
                    }
                    yPosition -= 35;
                }
            }

            pageContent.close();
            document.save(filePath);
            System.out.println("PDF generated successfully at " + filePath);
            System.out.println("Type 'exit' to see it in the folder");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pageContent != null)
            {
                try {
                    pageContent.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //helper for linear algebra (to handle \n and non ASCII chars, issues with FONT and PDFBox)
    private String replaceCharacters(String text)
    {
        return text.replace("\n", " ")
                .replaceAll("[^\\p{ASCII}]", " ");
    }
}
