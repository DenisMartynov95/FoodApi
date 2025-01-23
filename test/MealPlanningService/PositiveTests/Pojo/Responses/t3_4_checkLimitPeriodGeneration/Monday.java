package MealPlanningService.PositiveTests.Pojo.Responses.t3_4_checkLimitPeriodGeneration;

import java.util.ArrayList;

public class Monday{
    private ArrayList<Meal> meals;
    private Nutrients nutrients;

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public Monday(ArrayList<Meal> meals, Nutrients nutrients) {
        this.meals = meals;
        this.nutrients = nutrients;
    }

    public Monday() {
    }

    @Override
    public String toString() {
        return "Monday{" +
                "meals=" + meals +
                ", nutrients=" + nutrients +
                '}';
    }
}
