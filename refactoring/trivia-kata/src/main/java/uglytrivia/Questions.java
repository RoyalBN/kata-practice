package uglytrivia;

import uglytrivia.enums.Categories;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Questions {
    public static final int NUMBER_OF_QUESTIONS = 50;
    private Map<Categories, Queue<String>> questionsByCategory = new HashMap<>();

    public Questions() {
        for (Categories category : Categories.values()) {
            questionsByCategory.put(category, new LinkedList<>());
            for (int i=0; i<50; i++) {
                questionsByCategory.get(category).add(category.getCategory() + " Question" + i);
            }
        }
    }

    public String nextQuestion(Categories category) {
        Queue<String> questions = questionsByCategory.get(category);
        if (questions.isEmpty()) {
            refillQuestions(category);
        }
        return questions.poll();
    }

    private void refillQuestions(Categories category) {
        int startIndex = NUMBER_OF_QUESTIONS + questionsByCategory.get(category).size();
        for (int i = 0; i < 50; i++) { // tu peux ajuster
            questionsByCategory.get(category).add(generateNewQuestion(category, startIndex + i));
        }
    }

    private String generateNewQuestion(Categories categorie, int index) {
        return categorie + " Question " + index;
    }

    public int remainingQuestions(Categories category) {
        return questionsByCategory.get(category).size();
    }
}
