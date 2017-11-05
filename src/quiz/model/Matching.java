package quiz.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * An object that reprenents the data for a matching question
 *
 * @author Wil Gaboury
 */
public class Matching extends Question
    {
    private List<Pair<String, String> > matches;

    public Matching()
        { matches = new ArrayList<>(); }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizElementType getType()
        { return QuizElementType.MATCHING; }

    /**
     * @return a list of the pairs in the matching question
     */
    public List<Pair<String, String>> getMatches()
        { return matches; }
    }
