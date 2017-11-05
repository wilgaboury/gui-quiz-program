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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import quiz.XML;
import quiz.statedata.QuizData;

import java.io.File;

/**
 * the gui compontent for the quiz picking screen
 *
 * @author Wil Gaboury
 */
class QuizListScreen extends GridPane
    {
    private QuizListView quizListView;
    private Button openQuiz;
    private Button addQuiz;
    private Button removeQuiz;
    private QuizApplication root;

    /**
     * creates a new quiz screen
     * @param root the gui node of the application
     */
    public QuizListScreen(QuizApplication root)
        {
        this.root = root;
        this.setStyle("-fx-background-color: whitesmoke");

        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(50, 50, 50, 50));

        quizListView = new QuizListView(root);

        openQuiz = new Button("Open Quiz");
        addQuiz = new Button("Add Quiz");
        removeQuiz = new Button("Remove Quiz");

        openQuiz.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addQuiz.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        removeQuiz.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        openQuiz.setOnMouseClicked(event ->
            {
            if (quizListView.getSelectedQuiz() != -1)
                {
                QuizData selectedQuiz = quizListView.getQuizzes().get(quizListView.getSelectedQuiz());
                if (selectedQuiz.isValid() && XML.isValid(selectedQuiz.getLocation()))
                    {
                    selectedQuiz.reload();
                    root.openQuiz(XML.parseFile(selectedQuiz.getLocation()));
                    }
                }
            });

        addQuiz.setOnMouseClicked(event ->
            {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("quiz xml file (*.xml)", "*.xml"));
            fileChooser.setTitle("Load Quiz");
            File file = fileChooser.showOpenDialog(root.getStage());
            if (file != null && XML.isValid(file))
                { quizListView.addQuiz(file); }
            });
        removeQuiz.setOnMouseClicked(event -> quizListView.removeQuiz(quizListView.getSelectedQuiz()));

        this.setHgap(20);
        this.setVgap(20);
        this.add(quizListView, 0, 0, 1, 3);
        this.add(openQuiz, 1, 0);
        this.add(addQuiz, 1, 1);
        this.add(removeQuiz, 1, 2);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(65);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(35);
        this.getColumnConstraints().addAll(column1, column2);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(33);
        this.getRowConstraints().addAll(row, row, row);
        }

    }
