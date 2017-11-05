package quiz.model;

/**
 * An interface that sets and gets the a descriptor for a quizzes complexity
 */
public interface Complexity
    {
    /**
     * sets the complexity of the quiz
     * @param complexity string describing the complexity
     */
    void setComplexity (String complexity);

    /**
     * gets the complexity descriptor
     * @return a descriptor of the complexity of a quiz
     */
    String getComplexity();
    }
