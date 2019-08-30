package javatp.domain;
public class QuestionAnswerResponse{
    boolean correct;
    Hint hint;
    public QuestionAnswerResponse(boolean correct, Hint hint){
        this.correct = correct;
        this.hint = hint;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Hint getHint() {
        return hint;
    }

    public void setHint(Hint hint) {
        this.hint = hint;
    }
}