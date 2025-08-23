package uglytrivia;

import uglytrivia.enums.Categories;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Questions {
    public static final int NUMBER_OF_QUESTIONS = 50;
    private Map<Categories, LinkedList<String>> questions = new HashMap<>();

    public Questions() {
        questions.put(Categories.POP, new LinkedList<>());
        questions.put(Categories.SCIENCE, new LinkedList<>());
        questions.put(Categories.SPORTS, new LinkedList<>());
        questions.put(Categories.ROCK, new LinkedList<>());
        initQuestions();
    }

    private void initQuestions() {
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            addQuestion(Categories.POP, "Pop Question " + i);
            addQuestion(Categories.SCIENCE, "Science Question " + i);
            addQuestion(Categories.SPORTS, "Sports Question " + i);
            addQuestion(Categories.ROCK, "Rock Question " + i);
        }
    }


    public void addQuestion(Categories categorie, String question) {
        questions.get(categorie).addLast(question);
    }

    public String nextQuestion(Categories categorie) {
        return questions.get(categorie).removeFirst();
    }

}
