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
