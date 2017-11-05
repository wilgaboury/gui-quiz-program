package quiz.model;

/**
 * class that comprises of the data needed to represent a peice of text in the quiz
 */
public class Text implements QuizElement
    {
    private String text;

    public Text()
        {}

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizElementType getType()
        { return QuizElementType.TEXT; }

    /**
     * @return a string of text
     */
    public String getText()
        { return text; }

    /**
     * sets the text
     * @param text the text
     */
    public void setText(String text)
        { this.text = text; }
    }
