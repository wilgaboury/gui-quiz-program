package quiz.model;

/**
 * A class that represents the data for a multiple choice questions answer's data
 *
 * @author Wil Gaboury
 */
public class MultipleChoiceAnswer
    {
    private String text;
    private boolean answer;

    public MultipleChoiceAnswer()
        {}

    /**
     * @return the answers text
     */
    public String getText()
        { return text; }

    /**
     * @param text some text to set the answers text to
     */
    public void setText(String text)
        { this.text = text; }

    /**
     * @return true if it is a correct answer and false otherwise
     */
    public boolean isAnswer()
        { return answer; }

    /**
     * @param answer true if this is a correct answer and false otherwise
     */
    public void setAnswer(boolean answer)
        { this.answer = answer; }
    }
