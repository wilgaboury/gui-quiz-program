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

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import quiz.XML;
import quiz.statedata.QuizData;
import quiz.statedata.QuizList;

import java.io.File;
import java.util.List;

/**
 * A gui view of the QuizList data structure
 *
 * @author Wil Gaboury
 */
class QuizListView extends ScrollPane
    {
    private QuizList list;
    private VBox listView;
    private QuizApplication root;

    private int selectedQuiz;

    /**
     * creates the list view
     * @param root the parent gui compontent
     */
    public QuizListView(QuizApplication root)
        {
        this.root = root;
        this.selectedQuiz = -1;

        list = QuizList.getInstance();

        listView = new VBox();
        updateView();

        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setContent(listView);
        }

    /**
     * a gui component that displays a single cell in this list view
     *
     * @author Wil Gaboury
     */
    private class QuizDataCell extends VBox
        {
        private QuizData quizData;

        private Label name;
        private Label questions;
        private Label points;
        private Label location;
        private Label complexity;

        /**
         * creates a new cell
         * @param quizData simple pieces of metadata about a given quiz
         * @param parent the parent gui component
         */
        public QuizDataCell(QuizData quizData, ScrollPane parent)
            {
            this.quizData = quizData;
            this.setAlignment(Pos.CENTER_LEFT);
            this.setMaxWidth(Double.MAX_VALUE);
            this.setMaxHeight(Double.MAX_VALUE);
            this.prefWidthProperty().bind(parent.widthProperty());

            name = new Label();
            questions = new Label();
            points = new Label();
            complexity = new Label();
            location = new Label();
            setLabels();
            this.getChildren().addAll(name, complexity, questions, points, location);

            this.setOnMouseClicked(event ->
                {
                if (event.getButton().equals(MouseButton.PRIMARY))
                    {
                    ObservableList<Node> quizzes = listView.getChildren();
                    colorCells();
                    this.setStyle("-fx-background-color: cornflowerblue");
                    selectedQuiz = list.getQuizes().indexOf(quizData);

                    if (event.getClickCount() > 1)
                        {
                        if (quizData.isValid() && XML.isValid(quizData.getLocation()))
                            {
                            quizData.reload();
                            root.openQuiz(XML.parseFile(quizData.getLocation()));
                            }
                        }
                    }
                });
            }

        /**
         * resets the labels to the most current metadata stored by the quizData object
         */
        private void setLabels()
            {
            name.setText(quizData.getName());
            questions.setText(quizData.getNumberOfQuestions() + " questions");
            points.setText(quizData.getPoints() + "point" + (quizData.getPoints() > 1 ? "s" : ""));
            complexity.setText(quizData.getComplexity().toUpperCase());
            String color;
            if (quizData.getComplexity().equals("easy"))
                { color = "green"; }
            else if(quizData.getComplexity().equals("medium"))
                { color = "yellow"; }
            else
                { color = "red"; }
            complexity.setStyle("-fx-font-weight: bolder; -fx-text-fill:" + color);
            location.setText(quizData.getLocation().toString());
            }

        /**
         * reloads the file that quizData points to and also this gui compontents labels
         */
        public void reload()
            {
            quizData.reload();
            setLabels();
            }
        }

    /**
     * removes a quiz from the list at a given location
     * @param loc the location of the quiz to be removed
     */
    public void removeQuiz(int loc)
        {
        List<QuizData> quizzes = list.getQuizes();
        quizzes.remove(loc);
        list.setQuizes(quizzes);
        updateView();
        }

    /**
     * adds a quiz to the end of the list of quizez
     * @param loc the file location of the quiz xml
     */
    public void addQuiz(File loc)
        {
        List<QuizData> quizzes = list.getQuizes();
        quizzes.add(new QuizData(loc));
        list.setQuizes(quizzes);
        updateView();
        }

    /**
     * gets the full list of quiz metadata
     * @return the full list of quiz metadata
     */
    public List<QuizData> getQuizzes()
        { return list.getQuizes(); }

    /**
     * @return the index of the currently selected quiz or -1 if none are selected
     */
    public int getSelectedQuiz()
        { return selectedQuiz; }

    /**
     * updates all the cells labels in the list
     */
    private void updateView()
        {
        listView.getChildren().clear();
        for (QuizData data : list.getQuizes())
            { listView.getChildren().add(new QuizDataCell(data, this)); }
        colorCells();
        }

    /**
     * recolors all the cells so that there is better contrast
     */
    private void colorCells()
        {
        boolean white = true;
        for (Node cell : listView.getChildren())
            {
            cell.setStyle(white ? "-fx-background-color: white" : "-fx-background-color: lightgrey");
            white = !white;
            }
        }
    }
