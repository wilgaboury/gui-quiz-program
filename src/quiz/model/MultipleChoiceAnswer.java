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
 * A class that represents the data for a multiple choice questions answer's data
 *
 * @author Wil Gaboury
 */
public class MultipleChoiceAnswer
    {
    private String text;
    private boolean answer;

    public MultipleChoiceAnswer()
        {}

    /**
     * @return the answers text
     */
    public String getText()
        { return text; }

    /**
     * @param text some text to set the answers text to
     */
    public void setText(String text)
        { this.text = text; }

    /**
     * @return true if it is a correct answer and false otherwise
     */
    public boolean isAnswer()
        { return answer; }

    /**
     * @param answer true if this is a correct answer and false otherwise
     */
    public void setAnswer(boolean answer)
        { this.answer = answer; }
    }
