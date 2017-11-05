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

package quiz.statedata;

import javafx.scene.control.Alert;
import quiz.XML;
import quiz.model.Matching;
import quiz.model.Question;
import quiz.model.Quiz;
import quiz.model.QuizElementType;

import java.io.File;
import java.io.Serializable;

/**
 * A class comprising of metadata about a given quiz file
 *
 * @author Wil Gaboury
 */
public class QuizData implements Serializable
    {
    private String name;
    private int numberOfQuestions;
    private int points;
    private File location;
    private String complexity;

    /**
     * parses and store metadata about a given file
     * @param file an xml quiz file
     */
    public QuizData(File file)
        {
        location = file;
        reload();
        }

    /**
     * @return the name of the quiz
     */
    public String getName()
        { return name; }

    /**
     * @return the number of questions in the quiz
     */
    public int getNumberOfQuestions()
        { return numberOfQuestions; }

    /**
     * @return the location of the xml file
     */
    public File getLocation()
        { return location; }

    /**
     * @return the number of points in the quiz
     */
    public int getPoints()
        { return points; }

    /**
     * @return the complexity of the quiz
     */
    public String getComplexity()
        { return complexity; }

    /**
     * determine if the current file is still valid and creates an error dialoge if it isn't
     * @return true if the file is valid, false otherwise
     */
    public boolean isValid()
        {
        if (!location.exists())
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Not Found");
            alert.setHeaderText("File Not Found");
            alert.setContentText("Could not find the xml file specified, it may have been moved or deleted.");

            alert.showAndWait();
            return false;
            }

        return true;
        }

    /**
     * retreives the metadata anouther time from the xml file
     */
    public void reload()
        {
        Quiz quiz = XML.parseFile(location);
        numberOfQuestions = 0;
        points = 0;
        for (int i = 0; i < quiz.getElements().size(); i++)
            {
            if (    quiz.getElements().get(i).getType() == QuizElementType.MULTIPLECHOICE ||
                    quiz.getElements().get(i).getType() == QuizElementType.SHORTANSWER)
                {
                numberOfQuestions++;
                points += ((Question)quiz.getElements().get(i)).getPoints();
                }
            else if ( quiz.getElements().get(i).getType() == QuizElementType.MATCHING)
                {
                numberOfQuestions++;
                points += ((Question)quiz.getElements().get(i)).getPoints() * ((Matching)quiz.getElements().get(i)).getMatches().size();
                }
            }
        name = quiz.getName();
        complexity = quiz.getComplexity();
        }
    }
