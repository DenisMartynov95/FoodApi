package ReceiptService.PositiveTests.Pojo.Responses.t1_3_checkMinLimitsSearch;

import java.util.ArrayList;

public class Root{
    private ArrayList<Result> results;
    private int offset;
    private int number;
    private int totalResults;

    public Root() {
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "Root{" +
                "results=" + results +
                ", offset=" + offset +
                ", number=" + number +
                ", totalResults=" + totalResults +
                '}';
    }
}