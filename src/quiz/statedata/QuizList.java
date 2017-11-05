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

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * A list that serializes it's data and stores the metadata about each file on added to the program between sessions
 */
public class QuizList
    {
    private static final QuizList instance;
    static
        {
        instance = new QuizList();
        }
    public static QuizList getInstance()
        { return instance; }

    private List<QuizData> quizzes;
    private File saveLocation;

    public QuizList()
        {
        quizzes = new ArrayList<QuizData>();
        saveLocation = new File("quizlist.dat");
        if (saveLocation.exists())
            {
            try
                {
                FileInputStream fin = new FileInputStream(saveLocation);
                ObjectInputStream oin = new ObjectInputStream(fin);
                quizzes = (List<QuizData>) oin.readObject();
                oin.close();
                fin.close();
                }
            catch (ClassNotFoundException | EOFException e)
                {
                try
                    {
                    Files.delete(saveLocation.toPath());
                    Files.createFile(saveLocation.toPath());
                    }
                catch (IOException e1)
                    { e1.printStackTrace(); }
                }
            catch (IOException e)
                { e.printStackTrace(); }

            removeBadFiles();
            rescanFiles();
            save();
            }
        else
            {
            try
                { Files.createFile(saveLocation.toPath()); }
            catch (IOException e)
                { e.printStackTrace(); }
            }
        }

    /**
     * removes all the invalid files
     */
    public void removeBadFiles()
        {
        for (int i = 0; i < quizzes.size(); i++)
            {
            if (!quizzes.get(i).getLocation().exists())
                {
                quizzes.remove(i);
                i--;
                }
            }
        }

    /**
     * recollects all the files metadata
     */
    public void rescanFiles()
        {
        for (int i = 0; i < quizzes.size(); i++)
            { quizzes.get(i).reload(); }
        }

    /**
     * saves the metadata to a .dat file in the current directory
     */
    public void save()
        {
        try
            {
            FileOutputStream fout = new FileOutputStream(saveLocation);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(quizzes);
            oout.close();
            fout.close();
            }
        catch (IOException e)
            { e.printStackTrace(); }
        }

    /**
     * @return the list of quiz metadata stored by this object
     */
    public List<QuizData> getQuizes()
        { return new ArrayList<>(quizzes); }

    /**
     * @param list the list of quiz metadata to be stored by this object
     */
    public void setQuizes(List<QuizData> list)
        {
        quizzes = new ArrayList<>(list);
        removeBadFiles();
        save();
        }
    }
