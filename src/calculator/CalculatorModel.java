package calculator;

public class CalculatorModel {

    String operand1;
    String operand2;
    String arithmeticOperation;
    String operationalMode; //integer or float
    String floatingPointPrecision;
    boolean errorState;
    String result;

    CalculatorViewController calculatorViewController;

    public CalculatorModel(CalculatorViewController calculatorViewController) {
        this.calculatorViewController = calculatorViewController;
    }

    public void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    public void setOperand2(String operand2) {
        this.operand2 = operand2;
    }

    public void setArithmeticOperation(String arithmeticOperation) {
        this.arithmeticOperation = arithmeticOperation;
    }

    public void setOperationalMode(String operationalMode) {
        this.operationalMode = operationalMode;
    }

    public void setFloatingPointPrecision(String floatingPointPrecision) {
        this.floatingPointPrecision = floatingPointPrecision;
    }

    public void setErrorState(boolean errorState) {
        this.errorState = errorState;
    }
    
    public boolean getErrorState(){
        return this.errorState;
    }

    public String getResult() {
        calculate();
        if ("F".equals(operationalMode)) {
            parseResult();
        }
        return result;
    }

    private void calculate() {

        if (operationalMode == "I") {
            switch (arithmeticOperation) {
                case "+":
                    try {
                        result = String.valueOf(Math.addExact(Integer.parseInt(operand1), Integer.parseInt(operand2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
                case "-":
                    try {
                        result = String.valueOf(Math.subtractExact(Integer.parseInt(operand1), Integer.parseInt(operand2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
                case "/":
                    try {
                        result = String.valueOf(Integer.parseInt(operand1) / Integer.parseInt(operand2));
                    } catch (Exception e) {
                        if (e.getMessage().equals("/ by zero")) {
                            result = "Cannot divide by zero";
                            errorState = true;
                            return;
                        }
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
                case "*":
                    try {
                        result = String.valueOf(Math.multiplyExact(Integer.parseInt(operand1), Integer.parseInt(operand2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.toString();
                        errorState = true;
                    }
                    break;
            }

        } else if (operationalMode == "F") {
            switch (arithmeticOperation) {
                case "+":
                    try {
                        result = String.valueOf(Float.sum(Float.valueOf(operand1), Float.valueOf(operand2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
                case "-":
                    try {
                        result = String.valueOf(Float.valueOf(operand1) - Float.valueOf(operand2));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
                case "/":
                    try {
                        result = String.valueOf(Float.valueOf(operand1) / Float.valueOf(operand2));
                        if (result.equalsIgnoreCase("Infinity")) {
                            result = "Cannot divide by zero";
                            errorState = true;
                        }
                        if (result.equalsIgnoreCase("NaN")) {
                            result = "Result is undefined";
                            errorState = true;
                        }
                    } catch (Exception e) {
                        if (e.getMessage().equals("/ by zero")) {
                            result = "Cannot divide by zero";
                            errorState = true;
                            return;
                        }
                        result = e.getMessage();
                        e.printStackTrace();
                        errorState = true;
                    }
                    break;
                case "*":
                    try {
                        result = String.valueOf(Float.valueOf(operand1) * Float.valueOf(operand2));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
            }
        }

    } //ends calculate()

    public void parseResult() {
        if (errorState) {
            return;
        }
        switch (floatingPointPrecision) {
            case ".0":
                try {
                    result = String.format("%.1f", Double.valueOf(result));
                } catch (Exception e) {
                    e.printStackTrace();
                    result = e.getMessage();
                }
                break;
            case ".00":
                try {
                    result = String.format("%.2f", Double.valueOf(result));
                } catch (Exception e) {
                    e.printStackTrace();
                    result = e.getMessage();
                }
                break;
            case "sci":
                try {
                    result = String.format("%9.6E", Double.valueOf(result));
                } catch (Exception e) {
                    e.printStackTrace();
                    result = e.getMessage();
                }
                break;
        }
    }
}
