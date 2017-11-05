package quiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the data for a multiple choice question
 *
 * @author Wil Gaboury
 */
public class MultipleChoice extends Question
    {
    private String question;
    private List<MultipleChoiceAnswer> answers;

    public MultipleChoice()
        { answers = new ArrayList<>(); }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizElementType getType()
        { return QuizElementType.MULTIPLECHOICE; }

    /**
     * @return the question text
     */
    public String getQuestion()
        { return question; }

    /**
     * sets the question text
     * @param question the question text
     */
    public void setQuestion(String question)
        { this.question = question; }

    /**
     * @return a list of objects representing each multiple choice answer
     */
    public List<MultipleChoiceAnswer> getAnswers()
        { return answers; }
    }
