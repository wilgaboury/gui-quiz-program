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
