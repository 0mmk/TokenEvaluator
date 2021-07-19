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

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Double setOperation(TokenList objectList, HashMap<String, Double> variables, boolean isLeft) throws Exception {
        Double varPosition;
        Object token = isLeft ? objectList.getTokenLeft() : objectList.getTokenRight();
        String operator = objectList.getOperation();
        if (token.getClass() == Double.class) {
            varPosition = (Double) token;
        } else {
            String varLeftStr = (String) token;
            if (!isNumeric(varLeftStr)) {
                varPosition = variables.get(varLeftStr);
                if (!operator.equals("=") && varPosition == null) {
                    throw new Exception(String.format("Variable: `%s` doesn't exist!", varLeftStr));
                }
            } else {
                varPosition = Double.parseDouble(varLeftStr);
            }
        }
        return varPosition;
    }

    private static Object makeOperation(TokenList objectList, HashMap<String, Double> variables) throws Exception {
        String operator = objectList.getOperation();
        Double varLeft = setOperation(objectList, variables, true);
        Double varRight = setOperation(objectList, variables, false);

        if (operator.charAt(operator.length() - 1) != '=') {
            switch (operator) {
                case "+":
                    varLeft += varRight;
                    break;
                case "-":
                    varLeft -= varRight;
                    break;
                case "*":
                    varLeft *= varRight;
                    break;
                case "/":
                    varLeft /= varRight;
                    break;
                case "^":
                    varLeft = Math.pow(varLeft, varRight);
                    break;
            }
        } else {
            String varLeftStr = (String) objectList.getTokenLeft();
            switch (operator) {
                case "=":
                    variables.put(varLeftStr, varRight);
                    varLeft = variables.get(varLeftStr);
                    break;
                case "+=":
                    variables.put(varLeftStr, varLeft + varRight);
                    varLeft = variables.get(varLeftStr);
                    break;
                case "-=":
                    variables.put(varLeftStr, varLeft - varRight);
                    varLeft = variables.get(varLeftStr);
                    break;
                case "*=":
                    variables.put(varLeftStr, varLeft * varRight);
                    varLeft = variables.get(varLeftStr);
                    break;
                case "/=":
                    variables.put(varLeftStr, varLeft / varRight);
                    varLeft = variables.get(varLeftStr);
                    break;
                case "^=":
                    variables.put(varLeftStr, Math.pow(varLeft, varRight));
                    varLeft = variables.get(varLeftStr);
                    break;
            }
        }

        return varLeft;
    }
}
