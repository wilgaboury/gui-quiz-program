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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import quiz.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The gui tha presents and allows users to take a quiz
 *
 * @author Wil Gaboury
 */
class QuizScreen extends VBox
    {
    private QuizApplication root;
    private Label quizName;
    private VBox quizView;
    private Button exit;
    private Button submit;
    private DoubleProperty zoomProperty;
    private ScrollPane quizRegion;
    private Pane zoomRegion;

    private Quiz quiz;
    private List<QuestionGUI> questions;

    private DoubleProperty widthProp;

    private double scrollBarWidth;

    private int selectedQuestion;

    private final int SCROLLBARWIDTH = 14;

    /**
     * @param root the root component of the application
     */
    public QuizScreen(QuizApplication root)
        {
        super(10);
        this.root = root;
        this.setStyle("-fx-background-color: whitesmoke");
        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(50, 50, 50, 50));
        this.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        selectedQuestion = -1;

        questions = new ArrayList<>();
        quizName = new Label();
        quizView = new VBox();
        exit = new Button("Exit");
        submit = new Button("Submit");
        exit.setMinWidth(submit.getPrefWidth());

        exit.setOnMouseClicked(event ->
            {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText("Exit quiz without a grade");
            alert.setContentText("You are about to exit this quiz without receiving a grade. All answers will be lost.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                { root.exitQuiz(); }
            });

        submit.setOnMouseClicked(event ->
            {
            boolean notAnswered = false;
            for (QuestionGUI question : questions)
                {
                if (!question.isAnswered())
                    { notAnswered = true; }
                }

            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Submission Confirmation");
            alert1.setHeaderText("Confirm Quiz Submission");
            alert1.setContentText((notAnswered ? "There are still unanswered questions. " : "") +
                "Your grade will be final once you hit okay.");

            Optional<ButtonType> result = alert1.showAndWait();
            if (result.get() == ButtonType.OK)
                {
                int score = 0;
                int total = 0;
                for (QuestionGUI question : questions)
                    {
                    score += question.getScore();
                    total += question.getPoints();
                    }

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Your Score");
                alert2.setHeaderText("You Scored " + score + "/" + total);

                alert2.showAndWait();
                root.exitQuiz();
                }

            });

        submit.setAlignment(Pos.CENTER_LEFT);
        exit.setAlignment(Pos.CENTER_LEFT);

        quizRegion = new ScrollPane();
        quizRegion.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        quizRegion.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        quizRegion.setMaxHeight(Double.MAX_VALUE);
        quizRegion.setMaxWidth(Double.MAX_VALUE);
        quizRegion.setFitToWidth(true);

        zoomProperty = new SimpleDoubleProperty(1);
        zoomRegion = new Pane();
        zoomRegion.getChildren().add(quizView);

        widthProp = new SimpleDoubleProperty((quizRegion.widthProperty().get() - SCROLLBARWIDTH) / zoomProperty.get());
        quizRegion.widthProperty().addListener(event ->
        {
        widthProp.set((quizRegion.widthProperty().get() - SCROLLBARWIDTH) / zoomProperty.get());
        });
        zoomRegion.prefWidthProperty().bind(widthProp);
        quizView.prefWidthProperty().bind(zoomRegion.widthProperty());

        quizView.getTransforms().add(new Scale(1, 1));
        zoomProperty.addListener(event ->
            {
            quizView.getTransforms().clear();
            quizView.getTransforms().add(new Scale(zoomProperty.get(), zoomProperty.get()));
            widthProp.set((quizRegion.widthProperty().get() - SCROLLBARWIDTH) / zoomProperty.get());
            });
        quizRegion.addEventFilter(ScrollEvent.ANY, event ->
            {
            if (event.isControlDown())
                {
                event.consume();

                if (event.getDeltaY() > 0)
                    { zoomProperty.set(zoomProperty.get() * 1.1); }
                else if (event.getDeltaY() < 0)
                    { zoomProperty.set(zoomProperty.get() / 1.1); }
                }
            });
        Group contentGroup = new Group(zoomRegion);
        contentGroup.setAutoSizeChildren(true);
        quizRegion.setContent(contentGroup);

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(exit, submit);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(quizName, quizRegion, buttons);
        }

    /**
     * loads and generates the gui for a quiz given its xml model, and then displays it to the screen so it can be taken
     * @param inputQuiz the quiz to process
     */
    public void displayQuiz(Quiz inputQuiz)
        {
        quiz = inputQuiz;
        quizView.getChildren().clear();
        questions.clear();
        selectedQuestion = -1;

        quizName.setText(inputQuiz.getName());
        quizName.setStyle("-fx-font-size: 20pt");
        zoomProperty.set(1);

        int qNum = 1;
        for (QuizElement elem : quiz.getElements())
            {
            EventHandler<MouseEvent> selectQuestion = event ->
                {
                if (selectedQuestion > -1)
                    {
                    Node prev = (Node) questions.get(selectedQuestion);
                    prev.setStyle("-fx-backgound-color: lightgrey");
                    if (!((QuestionGUI)prev).isAnswered())
                        { prev.setStyle("-fx-background-color: #ffa0a0"); }
                    else
                        { prev.setStyle("-fx-background-color: lightgrey"); }
                    }

                selectedQuestion = questions.indexOf(event.getSource());
                Node cur = (Node)questions.get(selectedQuestion);
                cur.setStyle(   "-fx-background-color: aliceblue");
                };

            switch (elem.getType())
                {
                case MULTIPLECHOICE:
                    {
                    MultipleChoiceGUI question = new MultipleChoiceGUI((MultipleChoice) elem, qNum, quizView);
                    questions.add(question);
                    quizView.getChildren().add(question);
                    question.addEventFilter(MouseEvent.MOUSE_PRESSED, selectQuestion);
                    qNum++;
                    break;
                    }
                case MATCHING:
                    {
                    MatchingGUI question = new MatchingGUI((Matching) elem, qNum, quizView);
                    questions.add(question);
                    quizView.getChildren().add(question);
                    question.addEventFilter(MouseEvent.MOUSE_PRESSED, selectQuestion);
                    qNum++;
                    break;
                    }
                case SHORTANSWER:
                    {
                    ShortAnswerGUI question = new ShortAnswerGUI((ShortAnswer) elem, qNum, quizView);
                    questions.add(question);
                    quizView.getChildren().add(question);
                    question.addEventFilter(MouseEvent.MOUSE_PRESSED, selectQuestion);
                    qNum++;
                    break;
                    }
                case TEXT:
                    {
                    Label text = new Label(((Text) elem).getText());
                    text.setStyle("-fx-background-color: lightgrey");
                    text.prefWidthProperty().bind(quizView.widthProperty());
                    text.setWrapText(true);
                    quizView.getChildren().add(text);
                    break;
                    }
                }
            }
        }
    }
