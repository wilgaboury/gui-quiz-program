package quiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represents all the data in a quiz
 *
 * @author Wil Gaboury
 */
public class Quiz implements Complexity
    {
    private String name;
    private List<QuizElement> elements;
    private String complexity;

    public Quiz()
        { elements = new ArrayList<>(); }

    /**
     * @return a list of the quiz elements in order that they should appear in the quiz
     */
    public List<QuizElement> getElements()
        { return elements; }

    /**
     * @return the quizzes name
     */
    public String getName()
        { return name; }

    /**
     * @param name value to set the quizzes name to
     */
    public void setName(String name)
        { this.name = name; }

    /**
     * {@inheritDoc}
     */
    public String getComplexity()
        { return complexity; }

    /**
     * {@inheritDoc}
     */
    public void setComplexity(String complexity)
        { this.complexity = complexity; }

    }
