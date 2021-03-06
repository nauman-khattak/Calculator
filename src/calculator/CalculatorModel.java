package calculator;

public class CalculatorModel {

    String operand1; 
    String operand2;
    String arithmeticOperation; //+ - * /
    String operationalMode; //integer or float
    String floatingPointPrecision; // .0 or .00 or Sci
    boolean errorState;
    String result;
    int divisionCounter = 0;

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
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
                case "-":
                    try {
                        result = String.valueOf(Math.subtractExact(Integer.parseInt(operand1), Integer.parseInt(operand2)));
                    } catch (Exception e) {
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
                case "/":
                    try {
                        if ((divisionCounter%2) == 0) {
                            result = String.valueOf(Integer.parseInt(operand1) / Integer.parseInt(operand2));
                            divisionCounter++;
                        } else{
                            result = String.valueOf(Integer.parseInt(operand2) / Integer.parseInt(operand1));
                            divisionCounter++;
                        }
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
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
                case "-":
                    try {
                        result = String.valueOf(Float.valueOf(operand1) - Float.valueOf(operand2));
                    } catch (Exception e) {
                        result = e.getMessage();
                        errorState = true;
                    }
                    break;
                case "/":
                    try {
                        if ((divisionCounter%2) == 0) {
                            result = String.valueOf(Float.valueOf(operand1) / Float.valueOf(operand2));
                            divisionCounter++;
                        } else{
                            
                            result = String.valueOf(Float.valueOf(operand2) / Float.valueOf(operand1));
                            divisionCounter++;
                        }
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
                        errorState = true;
                    }
                    break;
                case "*":
                    try {
                        result = String.valueOf(Float.valueOf(operand1) * Float.valueOf(operand2));
                    } catch (Exception e) {
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
                    //%.2f means one decimal place
                    result = String.format("%.1f", Double.valueOf(result));
                } catch (Exception e) {
                    result = e.getMessage();
                }
                break;
            case ".00":
                try {
                    //%.2f means two decimal places
                    result = String.format("%.2f", Double.valueOf(result));
                } catch (Exception e) {
                    result = e.getMessage();
                }
                break;
            case "sci":
                try {
                    //%9.6E means there are maximum 9 meaningful digits in the result, 6 decimal place digits, E should be used for ^10
                    result = String.format("%9.6E", Double.valueOf(result));
                } catch (Exception e) {
                    result = e.getMessage();
                }
                break;
        }
    }
}
