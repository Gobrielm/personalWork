package core;

public class recipe {
    private good[] input;
    private good[] output;
    private int expenses;
    private int income;
    private int[] inputStorage;
    private int[] outputStorage;

    public recipe(good[] input, good[] output) {
        this.input = input;
        inputStorage = new int[input.length];
        this.output = output;
        outputStorage = new int[output.length];
        income = 0;
        expenses = 0;
    }
    public recipe(good[] input, good[] output, int expenses, int income) {
        this.input = input;
        this.output = output;
        this.income = income;
        this.expenses = expenses;
    }
    public recipe(good input, good output, int expenses, int income) {
        this.input = new good[]{input};
        this.output = new good[]{output};
        this.income = income;
        this.expenses = expenses;
    }

    public good[] getOutput() {
        return output;
    }

    public good[] getInput() {
        return input;
    }

    public int getExpenses() {
        return expenses;
    }
    public int getIncome() {
        return income;
    }

    public void changeInput(String name, int amount) {
        int i = 0;
        for (good good: input) {
            if (good.getName().equals(name)) {
                inputStorage[i] += amount;
                return;
            }
            i++;
        }
    }
    public void changeOutput(String name, int amount) {
        int i = 0;
        for (good good: output) {
            if (good.getName().equals(name)) {
                outputStorage[i] += amount;
                return;
            }
            i++;
        }
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

}
