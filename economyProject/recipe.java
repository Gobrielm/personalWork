package core;

public class recipe {
    private good[] input;
    private good[] output;
    private double expenses;
    private int income;
    private int[] inputStorage;
    private int[] outputStorage;

    public recipe(good[] input, good[] output, double expenses, int income) {
        this.input = input;
        this.output = output;
        inputStorage = new int[input.length];
        outputStorage = new int[output.length];
        this.income = income;
        this.expenses = expenses;
    }

    public recipe(String[] input, String[] output, int[] inputAmount, int[] outputAmount, double expenses, int income) {
        this.input = new good[input.length];
        for (int i = 0; i < input.length; i++) {
            this.input[i] = new good(input[i], inputAmount[i]);
        }
        this.output = new good[output.length];
        for (int i = 0; i < output.length; i++) {
            this.output[i] = new good(output[i], outputAmount[i]);
        }
        inputStorage = new int[input.length];
        outputStorage = new int[output.length];
        this.income = income;
        this.expenses = expenses;
    }
    public recipe(String input, String output, int inputAmount, int outputAmount, double expenses, int income) {
        this.input = new good[]{new good(input, inputAmount)};
        this.output = new good[]{new good(output, outputAmount)};
        this.income = income;
        this.expenses = expenses;
    }
    public good[] getInputGoodArray() {
        return input;
    }
    public good[] getOutputGoodArray() {
        return output;
    }

    public String[] getOutputName() {
        String[] toReturn = new String[output.length];
        for (int i = 0; i < output.length; i++) {
            toReturn[i] = output[i].getName();
        }
        return toReturn;
    }
    public int getOutputName(int index) {
        return outputStorage[index];
    }
    public int[] getOutputAmount() {
        int[] toReturn = new int[output.length];
        for (int i = 0; i < output.length; i++) {
            toReturn[i] = output[i].getAmount();
        }
        return toReturn;
    }
    public String[] getInputName() {
        String[] toReturn = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            toReturn[i] = input[i].getName();
        }
        return toReturn;
    }
    public int getInputName(int index) {
        return inputStorage[index];
    }
    public int[] getInputAmount() {
        int[] toReturn = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            toReturn[i] = input[i].getAmount();
        }
        return toReturn;
    }

    public double getExpenses() {
        return expenses;
    }
    public int getIncome() {
        return income;
    }

    public void changeInputAmount(String name, int amount) {
        int i = 0;
        for (good good: input) {
            if (good.getName().equals(name)) {
                inputStorage[i] += amount;
                return;
            }
            i++;
        }
    }
    public void changeInputAmount(int index, int amount) {
        inputStorage[index] += amount;
    }

    public void changeOutputAmount(String name, int amount) {
        int i = 0;
        for (good good: output) {
            if (good.getName().equals(name)) {
                outputStorage[i] += amount;
                return;
            }
            i++;
        }
    }
    public void changeOutputAmount(int index, int amount) {
        outputStorage[index] += amount;
    }
    public void createRecipe() {
        for (int i = 0; i < input.length; i++) {
            good temp = input[i];
            int amount = inputStorage[i];
            if (temp.getAmount() > amount) {
                return;
            }
        }
        //From here the recipe is valid

        for (int i = 0; i < input.length; i++) {
            good temp = input[i];
            inputStorage[i] -= temp.getAmount();
        }
        for (int i = 0; i < output.length; i++) {
            good temp = output[i];
            outputStorage[i] += temp.getAmount();
        }
    }

    @Override
    public String toString() {
        String toReturn = "";
        for (good x: input) {
            toReturn += x.getName() + ": " + x.getAmount();
        }
        for (good x: output) {
            toReturn += x.getName() + ": " + x.getAmount();
        }
        return toReturn;
    }
}
