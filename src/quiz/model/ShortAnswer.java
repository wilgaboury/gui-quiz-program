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
 * represents the data needen for a short anstwer question
 *
 * @author Wil Gaboury
 */
public class ShortAnswer extends Question
    {
    /**
     * represents the type of an element in the short answers question text
     */
    public enum Type
        {
        BLANK,
        TEXT
        }

    /**
     * interface that is used to determine whether an object is a blank or some text
     */
    public interface BlankOrText
        {
        /**
         * @return the type of the current object via an enum value
         */
        Type getType();
        }

    /**
     * class comprising the data for a peice of text
     */
    public static class Text implements BlankOrText
        {
        private String content;

        public Text()
            {}

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getType()
            { return Type.TEXT; }

        /**
         * @return the text content
         */
        public String getContent()
            { return content; }

        /**
         * sets the text content
         * @param content the text content
         */
        public void setContent(String content)
            { this.content = content; }
        }

    public static class Blank implements BlankOrText
        {
        public Blank()
            {}

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getType()
            { return Type.BLANK; }
        }

    private List<BlankOrText> question;
    private String answer;

    public ShortAnswer()
        { question = new ArrayList<>(); }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuizElementType getType()
        { return QuizElementType.SHORTANSWER; }

    /**
     * @return the list of elements comprising the question text
     */
    public List<BlankOrText> getQuestion()
        { return question; }

    /**
     * @return the answer to the question as a string
     */
    public String getAnswer()
        { return answer; }

    /**
     * sets the answer to the question
     * @param answer the answer to the question
     */
    public void setAnswer(String answer)
        { this.answer = answer; }
    }
