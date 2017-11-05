package quiz.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import quiz.model.Matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class creates and runs the interface for a matching question in the quiz taking gui
 *
 * @author Wil Gaboury
 */
public class MatchingGUI extends VBox implements QuestionGUI
    {
    private Matching model;

    private Label header;
    private List<Row> rows;
    private Label[] labelCol1;
    private Label[] labelCol2;
    private Pane lineDisplay;
    private GridPane answers;

    /**
     * Creates a new gui element by providing it some basic data
     *
     * @param model the data model for the matching question it should map to
     * @param qNum the question number that it is in the quiz
     * @param parent the parent gui component, used for resizing purposes
     */
    public MatchingGUI(Matching model, int qNum, Region parent)
        {
        this.model = model;
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setStyle("-fx-background-color: lightgrey");
        this.prefWidthProperty().bind(parent.widthProperty());
        header = new Label(qNum + ". (" + model.getPoints() + " point"  + (model.getPoints() > 1 ? "s" : "") + " each)");

        answers = new GridPane();
        answers.setHgap(5);
        answers.setVgap(5);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(50);
        col1.setMinWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(75);
        col3.setMinWidth(75);
        ColumnConstraints col4 = new ColumnConstraints();
        answers.getColumnConstraints().addAll(col1, col2, col3, col4);

        labelCol1 = new Label[model.getMatches().size()];
        labelCol2 = new Label[model.getMatches().size()];
        rows = new ArrayList<>();

        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < model.getMatches().size(); i++)
            { positions.add(i); }

        lineDisplay = new Pane();
        lineDisplay.setStyle("-fx-background-color: rgba(0,0,0,0)");
        lineDisplay.maxWidth(Double.MAX_VALUE);
        answers.add(lineDisplay, 2, 0, 1, model.getMatches().size());
        int i = 0;
        for (Pair<String, String> pair : model.getMatches())
            {
            TextField field = new TextField();
            Label label1 = new Label(pair.getKey());
            label1.setWrapText(true);

            Random rand = new Random();
            int randPos = rand.nextInt(positions.size());
            int pos2 = positions.get(randPos);
            positions.remove(randPos);
            Label label2 = new Label((pos2 + 1) + ") " + pair.getValue());
            label2.setWrapText(true);

            labelCol1[i] = label1;
            labelCol2[pos2] = label2;

            answers.add(field, 0, i);
            answers.add(label1, 1, i);
            answers.add(label2, 3, pos2);

            rows.add(new Row(i, pos2, field));

            i++;
            }

        this.getChildren().addAll(header, answers);
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAnswered()
        {
        for (Row r : rows)
            {
            if (r.getAnswer() < 0 || !r.isValid())
                { return false; }
            }
        return true;
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore()
        {
        int total = 0;
        for (Row r : rows)
            { total += (r.isCorrect() ? model.getPoints() : 0); }
        return total;
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPoints()
        { return model.getPoints() * rows.size(); }


    /**
     * Inner class for representing a row in the table of matching values
     *
     * @author Wil Gaboury
     */
    private class Row
        {
        private TextField answer;
        private int pos;
        private int answerLoc;
        private Line line;
        private String prevText;
        private boolean initialstate;
        private boolean valid;
        private int guessLoc;

        /**
         * Creates a new answer loc given some data
         * @param pos it's position in the gui grid
         * @param answerLoc the location of the answer in the grid
         * @param answer the text field that should be monitored for answer input
         */
        public Row(int pos, int answerLoc, TextField answer)
            {
            this.pos = pos;
            this.answerLoc = answerLoc;
            this.answer = answer;
            line = new Line();
            lineDisplay.getChildren().add(line);
            line.setStartX(0);
            line.endXProperty().bind(lineDisplay.widthProperty());
            line.setStyle("-fx-stroke-width: 0px");
            initialstate = true;
            valid = true;
            guessLoc = -1;

            answer.textProperty().addListener(event ->
                {
                initialstate = false;
                validateAll();

                if (getAnswer() >= 0)
                    {
                    line.setStyle("-fx-stroke-width: 1px");
                    guessLoc = getAnswer();
                    recalcLineYValues();
                    }
                else
                    {
                    line.setStyle("-fx-stroke-width: 0px");
                    }
                });

            answers.heightProperty().addListener(new ChangeListener<Number>()
                    {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
                        { recalcLineYValues(); }
                    });
            }

        /**
         * @return true if the answer is currently correct false otherwise
         */
        public boolean isCorrect()
            { return getAnswer() == this.answerLoc; }

        /**
         * @return -1 if the answer is invalid (out of range or not a number), -2 if no answer has yet been provided or
         * otherwise the position of the current answer that the user has inputted.
         */
        public int getAnswer()
            {
            if (initialstate)
                { return -2; }
            String answer = this.answer.getText();
            int answerLoc = 0;
            try
                { answerLoc = Integer.parseInt(answer); }
            catch (NumberFormatException e)
                { return -1; }
            if (answerLoc > rows.size() || answerLoc < 1)
                { return -1; }

            return answerLoc - 1;
            }

        /**
         * distiguishes graphically that the current row is invalid (makes the feild red)
         */
        public void invalid()
            {
            answer.setStyle("-fx-control-inner-background: #ff0f0f");
            valid = false;
            }

        /**
         * distinguishes graphically that the current row in not invalid
         */
        public void valid()
            {
            answer.setStyle("-fx-control-inner-background: white");
            valid = true;
            }

        public boolean isValid()
            { return valid; }

        /**
         * Used to update the position of the line in case of redraw, resize, etc.
         */
        public void recalcLineYValues()
            {
            if (guessLoc == -1)
                { return; }

            line.setStartY(labelCol1[pos].getLayoutY() + 10);
            line.setEndY(labelCol2[guessLoc].getLayoutY() + 10);
            }
        }

    /**
     * it graphically goes through and distinguishes all invalid selections as invalid (makes them red)
     */
    private void validateAll()
        {
        for (Row r1 : rows)
            {
            if (r1.getAnswer() >= 0)
                {
                r1.valid();

                for (Row r2 : rows)
                    {
                    if (r1.pos != r2.pos && r1.getAnswer() == r2.getAnswer())
                        {
                        r1.invalid();
                        r2.invalid();
                        }
                    }
                }
            else if (r1.getAnswer() == -2)
                {
                r1.valid();
                }
            else
                {
                r1.invalid();
                }
            }
        }
    }
