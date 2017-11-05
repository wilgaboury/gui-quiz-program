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
 * A class representing the data for a multiple choice question
 *
 * @author Wil Gaboury
 */
public class MultipleChoice extends Question
    {
    private String question;
    private List<MultipleChoiceAnswer> answers;

    public MultipleChoice()
        { answers = new ArrayList<>(); }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizElementType getType()
        { return QuizElementType.MULTIPLECHOICE; }

    /**
     * @return the question text
     */
    public String getQuestion()
        { return question; }

    /**
     * sets the question text
     * @param question the question text
     */
    public void setQuestion(String question)
        { this.question = question; }

    /**
     * @return a list of objects representing each multiple choice answer
     */
    public List<MultipleChoiceAnswer> getAnswers()
        { return answers; }
    }
