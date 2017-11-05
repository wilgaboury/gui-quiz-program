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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import quiz.model.Quiz;

/**
 * The main driver for the application
 *
 * @author Wil Gaboury
 */
public class QuizApplication extends Application
    {
    private StackPane frame;
    private QuizListScreen quizListScreen;
    private QuizScreen quizScreen;
    private Stage stage;

    /**
     * starts the whole program and inizializes the gui
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
        {
        this.stage = primaryStage;
        frame = new StackPane();
        Scene scene = new Scene(frame);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wil's Quiz Program");

        quizListScreen = new QuizListScreen(this);
        quizScreen = new QuizScreen(this);

        frame.setMaxHeight(Double.MAX_VALUE);
        frame.setMaxWidth(Double.MAX_VALUE);

        frame.getChildren().add(quizListScreen);
        frame.getChildren().add(quizScreen);
        quizListScreen.toFront();

        primaryStage.show();
        }

    public static void main(String[] args)
        { launch(args); }

    /**
     * opens a quiz to be taken by the user
     * @param quiz the quiz that is to be displayed/taken
     */
    void openQuiz(Quiz quiz)
        {
        quizScreen.displayQuiz(quiz);
        quizScreen.toFront();
        }

    /**
     * exits from the quiz back to the quizlist screen in the application
     */
    void exitQuiz()
        { quizListScreen.toFront(); }

    /**
     * @return the program's gui stage component
     */
    Stage getStage()
        { return stage; }
    }
