package quiz.model;

/**
 * An interface for each quiz element that allows for each one's type to be distinguished at runtime
 *
 * @author Wil Gaboury
 */
public interface QuizElement
    {
    /**
     * @return the type of the current element via an enum value
     */
    QuizElementType getType();
    }
