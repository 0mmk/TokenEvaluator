import java.util.HashMap;

public class Evaluator {

    public static Object parse(TokenList objectList, HashMap<String, Double> variables) throws Exception {
        if (objectList.getTokenLeft().getClass() == TokenList.class) {
            objectList.setTokenLeft(parse((TokenList) objectList.getTokenLeft(), variables));
        }
        if (objectList.getTokenRight().getClass() == TokenList.class) {
            objectList.setTokenRight(parse((TokenList) objectList.getTokenRight(), variables));
        }

        return makeOperation(objectList, variables);
    }

    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Double setOperation(TokenList objectList, HashMap<String, Double> variables, boolean isLeft) {
        Object token = isLeft ? objectList.getTokenLeft() : objectList.getTokenRight();
        return setOperationTypeWithToken(token, objectList.getOperation(), variables);
    }

    private static Double setOperationTypeWithToken(Object token, String operator, HashMap<String, Double> variables) {
        Double varPosition;
        if (Double.class.equals(token.getClass())) {
            varPosition = (Double) token;
        } else if (String.class.equals(token.getClass())) {
            String varPositionStr = (String) token;
            if (!isDouble(varPositionStr)) {
                varPosition = variables.get(varPositionStr);
                if (!operator.equals("=") && varPosition == null) {
                    throw new IllegalStateException(String.format("Variable `%s` doesn't exist", varPositionStr));
                }
            } else {
                varPosition = Double.parseDouble(varPositionStr);
            }
        } else if (Integer.class.equals(token.getClass())) {
            return setOperationTypeWithToken(((Integer) token).doubleValue(), operator, variables);
        } else {
            throw new IllegalStateException("Unexpected token class: " + token.getClass());
        }

        return varPosition;
    }

    private static Object makeOperation(TokenList objectList, HashMap<String, Double> variables) throws Exception {
        String operator = objectList.getOperation();
        Double varLeft = setOperation(objectList, variables, true);
        Double varRight = setOperation(objectList, variables, false);
        if (operator.charAt(operator.length() - 1) != '=') {
            switch (operator) {
                case "+" -> varLeft += varRight;
                case "-" -> varLeft -= varRight;
                case "*" -> varLeft *= varRight;
                case "/" -> varLeft /= varRight;
                case "^" -> varLeft = Math.pow(varLeft, varRight);
                default -> throw new IllegalStateException("Unexpected operation " + operator);
            }
        } else {
            String varLeftStr = (String) objectList.getTokenLeft();
            switch (operator) {
                case "=" -> varLeft = variables.put(varLeftStr, varRight);
                case "+=" -> varLeft = variables.put(varLeftStr, varLeft + varRight);
                case "-=" -> varLeft = variables.put(varLeftStr, varLeft - varRight);
                case "*=" -> varLeft = variables.put(varLeftStr, varLeft * varRight);
                case "/=" -> varLeft = variables.put(varLeftStr, varLeft / varRight);
                case "^=" -> varLeft = variables.put(varLeftStr, Math.pow(varLeft, varRight));
                default -> throw new IllegalStateException("Unexpected operation " + operator);
            }
        }
        return varLeft;
    }
}
