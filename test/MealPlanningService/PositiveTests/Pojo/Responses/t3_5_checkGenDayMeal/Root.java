package MealPlanningService.PositiveTests.Pojo.Responses.t3_5_checkGenDayMeal;

import java.util.ArrayList;

public class Root {

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

    public Root(ArrayList<Meal> meals, Nutrients nutrients) {
        this.meals = meals;
        this.nutrients = nutrients;
    }

    public Root() {
    }

    @Override
    public String toString() {
        return "Root{" +
                "meals=" + meals +
                ", nutrients=" + nutrients +
                '}';
    }



}
