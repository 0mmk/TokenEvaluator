import java.util.HashMap;

public class TokenList {
    private String operation;
    private Object tokenLeft, tokenRight;

    public TokenList(String operation, Object tokenLeft, Object tokenRight) {
        this.operation = operation;
        this.tokenLeft = tokenLeft;
        this.tokenRight = tokenRight;
    }

    public String getOperation() {
        return operation;
    }

    public Object getTokenLeft() {
        return tokenLeft;
    }

    public Object getTokenRight() {
        return tokenRight;
    }

    public void setTokenLeft(Object tokenLeft) {
        this.tokenLeft = tokenLeft;
    }

    public void setTokenRight(Object tokenRight) {
        this.tokenRight = tokenRight;
    }
}