package quiz.gui;

/**
 * Is a base interface for all gui compontents that function as questions in the quiz
 *
 * @author Wil Gaboury
 */
public interface QuestionGUI
    {
    /**
     * @return true if the question has been answered, false otherwiwse
     */
    boolean isAnswered();

    /**
     * @return gets the current score of the question using the users current answer/s
     */
    int getScore();

    /**
     * @return gets the highest score possible for this question
     */
    int getPoints();
    }
