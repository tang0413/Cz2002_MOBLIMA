package modules.entity.movie;

import modules.data.DataBase;
import modules.entity.BaseEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Movie extends BaseEntity {
    private static final String CASTFILENAME = "ActorList.txt";
    private static final String REVIEWFILENAME = "ReviewList.txt";
    private static DecimalFormat df = new DecimalFormat("0.0");
    private String name;
    private String description;
    private Double totalScore;
    private String rating;
    private int numOfPeople;
    private ArrayList<String> castList;
//    public Movie(int id, String name, String description, Double totalScore, int numOfPeople ) {
//        super(id);
//        this.name = name;
//        this.description = description;
//        this.totalScore = totalScore;
//        this.numOfPeople = numOfPeople;
//        this.rating = df.format(totalScore/numOfPeople);
//        this.castList = findCast();
//    }

    public Movie(ArrayList<String> paramList) {
        //TODO get the ratings from review class
        super(Integer.parseInt(paramList.get(0)));
        this.name = paramList.get(1);
        this.description = paramList.get(2);
        this.rating = findRating();
        this.castList = findCast();
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

    public String getCast(){
        String res = String.join(",", this.castList);
        return res;
    }
    private ArrayList<String> findCast(){
        ArrayList<String> castList = new ArrayList<String>();
        try{
            ArrayList<Actor> wholeActorList = DataBase.readList(CASTFILENAME, Actor.class);
            for (Actor a: wholeActorList){
                if (a.getInMovie().contains(Integer.toString(this.id))){
                    castList.add(a.getName());
                }
            }
        } catch(Exception e){
        }
        return castList;
    }

    private String findRating(){
        Double totalScore = 0.0;
        int numberOfPeople = 0;
        try{
            ArrayList<Review> wholeReviewList = DataBase.readList(REVIEWFILENAME, Review.class);
            for (Review a: wholeReviewList){
                if (a.getMovieId() == this.id){
                    totalScore += Double.valueOf(a.getRating());
                    numberOfPeople ++;
                }
            }
        } catch(Exception e){
        }
        if (numberOfPeople > 1){
            return df.format(totalScore/numberOfPeople);
        } else {
            return "N.A.";
        }
    }

}
