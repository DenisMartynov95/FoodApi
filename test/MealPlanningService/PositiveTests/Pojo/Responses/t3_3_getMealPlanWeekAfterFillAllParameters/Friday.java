package MealPlanningService.PositiveTests.Pojo.Responses.t3_3_getMealPlanWeekAfterFillAllParameters;

import java.util.ArrayList;

public class Friday{
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

    public Friday(ArrayList<Meal> meals, Nutrients nutrients) {
        this.meals = meals;
        this.nutrients = nutrients;
    }

    public Friday() {
    }
}
