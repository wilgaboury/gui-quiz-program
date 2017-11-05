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
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import quiz.model.MultipleChoice;
import quiz.model.MultipleChoiceAnswer;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceGUI extends VBox implements QuestionGUI
    {
    private MultipleChoice model;
    private MyToggleGroup group;

    public MultipleChoiceGUI(MultipleChoice model, int qNum, Region parent)
        {
        this.model = model;
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setStyle(  "-fx-background-color: lightgrey");
        this.prefWidthProperty().bind(parent.widthProperty());

        Label question = new Label(qNum + ". (" + model.getPoints() + " point" + (model.getPoints() > 1 ? "s" : "") + ") " + model.getQuestion());
        question.setWrapText(true);
        this.getChildren().add(question);
        group = new MyToggleGroup();
        for (MultipleChoiceAnswer answer : model.getAnswers())
            {
            RadioButton bt = new RadioButton(answer.getText());
            this.getChildren().add(bt);
            group.add(bt);
            }
        }

    public boolean isAnswered()
        { return group.getSelected() != null; }

    public int getScore()
        {
        if (group.getSelected() != null && model.getAnswers().get(group.getToggles().indexOf(group.getSelected())).isAnswer())
            { return model.getPoints(); }
        else
            { return 0; }
        }

    public int getPoints()
        { return model.getPoints(); }

    private static class MyToggleGroup
        {
        private List<Toggle> toggles;
        private Toggle selected;

        public MyToggleGroup()
            {
            toggles = new ArrayList<>();
            }

        public void add(Toggle toggle)
            {
            toggles.add(toggle);
            toggle.selectedProperty().addListener(change ->
                {
                if (toggle.isSelected())
                    {
                    for (Toggle t : toggles)
                        {
                        if (t.isSelected() && t != toggle)
                            { t.setSelected(false); }
                        }
                    toggle.setSelected(true);
                    selected = toggle;
                    }
                else
                    { selected = null; }
                });
            }

        public List<Toggle> getToggles()
            { return toggles; }

        public Toggle getSelected()
            { return selected; }
        }
    }
