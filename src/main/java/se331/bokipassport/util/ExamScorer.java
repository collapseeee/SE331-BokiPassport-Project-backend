package se331.bokipassport.util;

import se331.bokipassport.entity.Question;

import java.util.List;
import java.util.Map;

public final class ExamScorer {
    private ExamScorer() {
    }

    public static int countCorrect(List<Question> questions, Map<Long, Long> answers) {
        if (questions == null || answers == null) {
            return 0;
        }
        int correct = 0;
        for (Question question : questions) {
            Long chosenAnswerId = answers.get(question.getId());
            if (chosenAnswerId == null) {
                continue;
            }
            boolean isCorrect = question.getAnswerList().stream()
                    .anyMatch(answer -> chosenAnswerId.equals(answer.getId())
                            && Boolean.TRUE.equals(answer.getIsCorrect()));
            if (isCorrect) {
                correct++;
            }
        }
        return correct;
    }

    public static boolean isPassed(int score, int questionCount, Double passCriteria) {
        if (passCriteria == null || questionCount <= 0) {
            return false;
        }
        return score >= Math.round(passCriteria * questionCount);
    }
}
