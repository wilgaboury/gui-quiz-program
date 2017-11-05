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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import quiz.model.ShortAnswer;

import java.util.ArrayList;
import java.util.List;

public class ShortAnswerGUI extends VBox implements QuestionGUI
    {
    private ShortAnswer model;

    private TextField answerArea;
    private TextFlow fullQuestion;
    private List<Text> blanks;

    public ShortAnswerGUI(ShortAnswer model, int qNum, Region parent)
        {
        this.model = model;
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setStyle(  "-fx-background-color: lightgrey");
        this.prefWidthProperty().bind(parent.widthProperty());
        blanks = new ArrayList<>();
        fullQuestion = new TextFlow();

        answerArea = new TextField();

        Label number = new Label(qNum + ". (" + model.getPoints() + " point" + (model.getPoints() > 1 ? "s" : "") + ") ");
        number.setWrapText(true);
        fullQuestion.getChildren().add(number);

        for (ShortAnswer.BlankOrText bot : model.getQuestion())
            {
            if (bot.getType() == ShortAnswer.Type.BLANK)
                {
                Text blank = new Text("<blank>");
                blank.setStyle("-fx-underline: true");
                blanks.add(blank);
                fullQuestion.getChildren().add(blank);
                }
            else
                {
                Text text = new Text(((ShortAnswer.Text)bot).getContent());
                fullQuestion.getChildren().add(text);
                }
            }

        answerArea.textProperty().addListener(
                event -> updateBlanks(answerArea.getText()));

        this.getChildren().addAll(fullQuestion, answerArea);
        }

    @Override
    public boolean isAnswered()
        { return !(answerArea.getText().equals("")); }

    @Override
    public int getScore()
        { return answerArea.getText().toString().equalsIgnoreCase(model.getAnswer()) ? model.getPoints() : 0; }

    @Override
    public int getPoints()
        { return model.getPoints(); }

    private void updateBlanks(String str)
        {
        for (Text text : blanks)
            {
            if (str.equals(""))
                { text.setText("<blank>"); }
            else
                { text.setText(str); }
            }
        }
    }
