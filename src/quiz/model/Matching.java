/*
gui-quiz-program lets one take quizzes via a gui program
Copyright (C) 2017  Wil Gaboury

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
