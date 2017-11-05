package quiz.model;

/**
 * An abstract class that has data containing common instance data about the number of points a given question is worth
 * because all question types must have this data and funcitonality anyway
 *
 * @author Wil Gaboury
 */
public abstract class Question implements QuizElement
    {
    private int points;

    /**
     * @return the point worth of the question
     */
    public int getPoints()
        { return points; }

    /**
     * @param points a value to set the point worth of the question to
     */
    public void setPoints(int points)
        { this.points = points; }
    }
