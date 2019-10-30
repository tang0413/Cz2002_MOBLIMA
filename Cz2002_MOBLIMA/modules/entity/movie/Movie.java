package modules.entity.movie;

import modules.entity.BaseEntity;

import java.text.DecimalFormat;

public class Movie extends BaseEntity {
    private static DecimalFormat df = new DecimalFormat("0.00");
    private String name;
    private String description;
    private Double totalScore;
    private String rating;
    private int numOfPeople;
    public Movie(int id, String name, String description, Double totalScore, int numOfPeople ) {
        super(id);
        this.name = name;
        this.description = description;
        this.totalScore = totalScore;
        this.numOfPeople = numOfPeople;
        this.rating = df.format(totalScore/numOfPeople);
    }

    //TODO: add set, add dependency to cast and directors.....

    public Double getTotalScore() {
        return totalScore;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }


}
