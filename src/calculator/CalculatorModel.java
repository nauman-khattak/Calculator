package calculator;

public class CalculatorModel {

    String operand1;
    String operand2;
    String arithmeticOperation;
    String operationalMode; //integer or float
    String floatingPointPrecision;
    boolean errorState;
    boolean exception;

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

    public String getResult() {
        calculate();
        parseResult();
        return result;
    }
    String result;

    private void calculate() {

        if (operationalMode == "I") {
            switch (arithmeticOperation) {
                case "+":
                    try {
                        result = String.valueOf(Math.addExact(Integer.parseInt(operand1), Integer.parseInt(operand2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.getMessage();
                        exception = true;
                    }
                    break;
                case "-":
                    try {
                        result = String.valueOf(Math.subtractExact(Integer.parseInt(operand1), Integer.parseInt(operand2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.getMessage();
                    }
                    break;
                case "/":
                    try {
                        result = String.valueOf(Integer.parseInt(operand1) / Integer.parseInt(operand2));
                    } catch (Exception e) {
                        if (e.getMessage().equals("/ by zero")) {
                            result = "Cannot divide by zero";
                            return;
                        }
                        result = e.getMessage();
                    }
                    break;
                case "*":
                    try {
                        result = String.valueOf(Math.multiplyExact(Integer.parseInt(operand1), Integer.parseInt(operand2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.toString();
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
                    }
                    break;
                case "-":
                    try {
                        result = String.valueOf(Float.valueOf(operand1)-Float.valueOf(operand2));
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.getMessage();
                    }
                    break;
                case "/":
                    try {
                        result = String.valueOf(Float.valueOf(operand1)/Float.valueOf(operand2));
                    } catch (Exception e) {
                        if (e.getMessage().equals("/ by zero")) {
                            result = "Cannot divide by zero";
                            return;
                        }
                        result = e.getMessage();
                    }
                    break;
                case "*":
                    try {
                        result = String.valueOf(Float.valueOf(operand1)*Float.valueOf(operand2));
                    } catch (Exception e) {
                        e.printStackTrace();
//                        result = e.getMessage();
//                        result = e.toString();
                    }
                    break;
            }
        }

    } //ends calculate()
    
    public void parseResult(){
        if (exception) {
            return;
        }
        
    }
}
