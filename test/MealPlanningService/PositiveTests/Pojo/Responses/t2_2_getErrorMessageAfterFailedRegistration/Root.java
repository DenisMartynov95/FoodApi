package MealPlanningService.PositiveTests.Pojo.Responses.t2_2_getErrorMessageAfterFailedRegistration;

public class Root {
    private String status;
    private String username;
    private String spoonacularPassword;
    private String hash;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSpoonacularPassword() {
        return spoonacularPassword;
    }

    public void setSpoonacularPassword(String spoonacularPassword) {
        this.spoonacularPassword = spoonacularPassword;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Root(String status, String username, String spoonacularPassword, String hash) {
        this.status = status;
        this.username = username;
        this.spoonacularPassword = spoonacularPassword;
        this.hash = hash;
    }

    public Root() {
    }

    @Override
    public String toString() {
        return "Root{" +
                "status='" + status + '\'' +
                ", username='" + username + '\'' +
                ", spoonacularPassword='" + spoonacularPassword + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
