package MealPlanningService.PositiveTests.Pojo.Responses.t3_1_getMealPlanWeek;

import java.util.ArrayList;

public class Saturday{
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
}
