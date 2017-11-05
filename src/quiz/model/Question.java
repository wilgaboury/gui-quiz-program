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
