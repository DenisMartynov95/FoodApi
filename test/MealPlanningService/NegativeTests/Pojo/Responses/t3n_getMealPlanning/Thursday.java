package MealPlanningService.NegativeTests.Pojo.Responses.t3n_getMealPlanning;

import java.util.ArrayList;

public class Thursday{
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

    public Thursday(ArrayList<Meal> meals, Nutrients nutrients) {
        this.meals = meals;
        this.nutrients = nutrients;
    }

    public Thursday() {
    }
}
