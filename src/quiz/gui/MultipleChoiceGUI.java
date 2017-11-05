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
