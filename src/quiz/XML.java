package quiz;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import quiz.model.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * collection of static methods for processing the quiz xml
 *
 * @author Wil Gaboury
 */
public class XML
    {
    /**
     * determines whether an xml file is valid or not. If not an error message will be displayed on sceen that contains
     * a list of all the errors in the file.
     * @param xml location of the xml file
     * @return true if the file is valid, false otherwise
     */
    public static boolean isValid(File xml)
        {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;

        try
            { schema = sf.newSchema(new File("quiz_schema.xsd")); }
        catch (SAXException e)
            { e.printStackTrace(); }

        Validator validator = schema.newValidator();
        final List<SAXParseException> exceptions = new ArrayList<>();
        validator.setErrorHandler(new ErrorHandler()
            {
            @Override
            public void warning(SAXParseException exception)
                { exceptions.add(exception); }

            @Override
            public void fatalError(SAXParseException exception)
                { exceptions.add(exception); }

            @Override
            public void error(SAXParseException exception)
                { exceptions.add(exception); }
            });

        try
            { validator.validate(new StreamSource(new FileInputStream(xml))); }
        catch (Exception e)
            { e.printStackTrace(); }

        if (exceptions.size() > 0)
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid XML");
            alert.setHeaderText("Invalid Quiz XML");
            alert.setContentText("quiz xml file does not conform to schema");

// Create expandable Exception.
            StringBuilder sb = new StringBuilder();
            for (SAXParseException e : exceptions)
                {
                sb.append(e.toString());
                sb.append('\n');
                }
            String errorText = sb.toString();

            Label label = new Label("error list:");

            TextArea textArea = new TextArea(errorText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);

            alert.showAndWait();
            }

        return exceptions.size() == 0;
        }

    /**
     * parses the xml file and generate a quiz data structure to represent the quiz
     * @param xml the location of the xml file
     * @return the full quiz object
     */
    public static Quiz parseFile(File xml)
        {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc = null;
        try
            {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xml);
            }
        catch (Exception e)
            { e.printStackTrace(); }

        Element quizNode = doc.getDocumentElement();
        Quiz result = new Quiz();
        result.setName(quizNode.getAttribute("name"));
        result.setComplexity(quizNode.getAttribute("complexity"));

        for (Node n = quizNode.getFirstChild(); n != null; n = n.getNextSibling())
            {
            if (n.getNodeType() == Node.ELEMENT_NODE)
                {
                Element e = (Element)n;

                if (e.getTagName().equals("text"))
                    {
                    Text text = new Text();
                    text.setText(e.getTextContent());
                    result.getElements().add(text);
                    }
                else if (e.getTagName().equals("multipleChoice"))
                    {
                    MultipleChoice multipleChoice = new MultipleChoice();
                    multipleChoice.setPoints(e.getAttribute("points").equals("") ? 1 : Integer.parseInt(e.getAttribute("points")));
                    multipleChoice.setQuestion(e.getElementsByTagName("q").item(0).getTextContent());
                    NodeList answers = e.getElementsByTagName("a");
                    for (int i = 0; i < answers.getLength(); i++)
                        {
                        MultipleChoiceAnswer answer = new MultipleChoiceAnswer();

                        String correct = ((Element)answers.item(i)).getAttribute("correct");
                        if (correct.equals("") || correct.equals("false"))
                            { answer.setAnswer(false); }
                        else
                            { answer.setAnswer(true); }
                        answer.setText(answers.item(i).getTextContent());
                        multipleChoice.getAnswers().add(answer);
                        }
                    result.getElements().add(multipleChoice);
                    }
                else if(e.getTagName().equals("shortAnswer"))
                    {
                    ShortAnswer shortAnswer = new ShortAnswer();
                    shortAnswer.setPoints(e.getAttribute("points").equals("") ? 1 : Integer.parseInt(e.getAttribute("points")));

                    for (Node node = e.getElementsByTagName("text").item(0).getFirstChild(); node != null; node = node.getNextSibling())
                        {
                        if (node.getNodeType() == Node.ELEMENT_NODE)
                            { shortAnswer.getQuestion().add(new ShortAnswer.Blank()); }
                        else
                            {
                            ShortAnswer.Text text = new ShortAnswer.Text();
                            text.setContent(node.getNodeValue());
                            shortAnswer.getQuestion().add(text);
                            }
                        }
                    shortAnswer.setAnswer(((Element)e.getElementsByTagName("a").item(0)).getTextContent());
                    result.getElements().add(shortAnswer);
                    }
                else if(e.getTagName().equals("matching"))
                    {
                    Matching matching = new Matching();
                    matching.setPoints(e.getAttribute("points").equals("") ? 1 : Integer.parseInt(e.getAttribute("points")));

                    NodeList pairs = e.getElementsByTagName("pair");
                    for (int i = 0; i < pairs.getLength(); i++)
                        {
                        matching.getMatches().add(new Pair<>(
                                ((Element)pairs.item(i)).getElementsByTagName("left").item(0).getTextContent(),
                                ((Element)pairs.item(i)).getElementsByTagName("right").item(0).getTextContent()
                        ));
                        }
                    result.getElements().add(matching);
                    }
                }
            }

        return result;
        }
    }
