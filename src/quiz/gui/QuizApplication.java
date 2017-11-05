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
