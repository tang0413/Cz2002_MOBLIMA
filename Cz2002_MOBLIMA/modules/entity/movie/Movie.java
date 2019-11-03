package modules.entity.movie;

import modules.data.DataBase;
import modules.entity.BaseEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Represents a movie
 */
public class Movie extends BaseEntity implements Comparable{
    /**
     * This is the format of showing user rating that rounds the digits to 1 decimal
     */
    private static DecimalFormat df = new DecimalFormat("0.0");
    /**
     * This is the name of the movie
     */
    private String name;
    /**
     * This is the description of the movie
     */
    private String description;
    /**
     * This is the user rating score of the movie e.g. 4.5
     */
    private String rating;
    /**
     * This is a list of cast names
     */
    private ArrayList<String> castList;
    /**
     * This is a list of director names; Usually only contains onw element
     */
    private ArrayList<String> directorList;
    /**
     * This is a list of all user reviews (comments) on this movie
     */
    private ArrayList<String> review = new ArrayList<String>();
    /**
     * This is the status of the movie, which is one of the four values: Coming Soon, Preview, Now Showing, or End Of Showing
     */
    private String status;
    /**
     * This is the type of the movie, e.g. 3D
     */
    private String type;
    /**
     * This is the movie rating of the movie, e.g. PG13
     * To avoid confuse with user rating, it's named to cat (category)
     */
    private String cat;

//    public Movie(int id, String name, String description, Double totalScore, int numOfPeople ) {
//        super(id);
//        this.name = name;
//        this.description = description;
//        this.totalScore = totalScore;
//        this.numOfPeople = numOfPeople;
//        this.rating = df.format(totalScore/numOfPeople);
//        this.castList = findCast();
//    }

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * Note it will call findRatingAndSetReview and findPeople to get the review, director, and cast attributes
     * @param paramList ArrayList of String with contains the id, name, description, status, type, and category in sequence
     */
    public Movie(ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.name = paramList.get(1);
        this.description = paramList.get(2);
        this.status = paramList.get(3);
        this.type = paramList.get(4);
        this.cat = paramList.get(5);
        this.rating = findRatingAndSetReview();
        this.castList = findPeople(Actor.class);
        this.directorList =  findPeople(Director.class);
    }

    /**
     * @return the id of the movie
     */
    public int getId(){
        return id;
    }

    /**
     * @return the name of the movie
     */
    public String getName(){
        return name;
    }

    /**
     * @return the user rating score of the movie
     */
    public String getRating() {
        return rating;
    }

    /**
     * @return the short description of the movie
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the cast of the movie
     */
    public String getCast(){
        String res = String.join(",", this.castList);
        return res;
    }

    /**
     * @return the direcotr(s) of the movie
     */
    public String getDirector(){
        String res = String.join(",", this.directorList);
        return res;
    }

    /**
     * @return the reviews of the movie
     */
    public ArrayList<String> getReview(){
        return this.review;
    }

    /**
     * @return the status of the movie
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the type of the movie
     */
    public String getType() {
        return type;
    }

    /**
     * @return the category of the movie
     */
    public String getCat() {
        return cat;
    }

    /**
     * This is used to check if two movies' rating and name are identical
     * Modified from Lab4 manual
     * @param o the other Movie object to compare with
     * @return true if the two movies' rating and name are identical. False otherwise.
     */
    public boolean equals(Object o) {
        if(o.getClass() == this.getClass()) {
            Movie s = (Movie)o;
            return (this.rating.equals(s.getRating()) && this.name.equals(s.getName()));
        }
        return false;
    }

    /**
     * This is used to compare two movies' rating and name
     * Modified from Lab4 manual
     * @param o the other Movie object to compare with
     * @return  -1 if the current object is smaller. 1 if the currents object is bigger. 2 means the two objects are not of the same class
     */
    public int compareTo(Object o) {
        if(o.getClass() == this.getClass()) {
            Movie s = (Movie)o;
            if(this.rating.equals(s.getRating()))
                return this.name.compareTo(s.getName());
            else {
                if (this.rating.equals("N.A.")){
                    return -1;
                }
                return this.rating.compareTo(s.getRating());
            }
        }
        return 2;
    }

    /**
     * This is used to find MoviePeople who participated in this particular movie
     * @param classObj The exact class of the people e.g. Actor.class or Director.class
     * @return the name list of all Directors or Cast
     */
    private ArrayList<String> findPeople(Class<? extends MoviePeople> classObj){
        ArrayList<String> peopleList = new ArrayList<>();
        try{
            ArrayList<MoviePeople> wholeMoviePeopleList = DataBase.readList(classObj);
            for (MoviePeople a: wholeMoviePeopleList){
                if (a.getInMovie().contains(Integer.toString(this.id))){
                    peopleList.add(a.getName());
                }
            }
        } catch(Exception e){
        }
        return peopleList;
    }

    /**
     * This is will read the rating and reviews from the Review database to calculate the general user rating.
     * During the process, the stringlized reviews will be appended to this.review as well.
     * @return the calculated overall user rating scores, e.g. 4.5;
     * Note that if there is less than one review, 'N.A.' will be returned instead
     */
    private String findRatingAndSetReview(){
        Double totalScore = 0.0;
        int numberOfPeople = 0;
        try{
            ArrayList<Review> wholeReviewList = DataBase.readList(Review.class);
            for (Review a: wholeReviewList){
                if (a.getMovieId() == this.id){
                    this.review.add(a.getReview());
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

    /**
     * This is to set the name of a movie
     * @param newName new name to be set
     */
    public void setName(String newName){
        this.name = newName;
    }

    /**
     * This is to set the type of the movie
     * @param newType new type to be set
     */
    public void setType(String newType){
        this.type = newType;
    }

    /**
     * This is to set the category (Movie rating) of a movie
     * @param newCat new category to be set
     */
    public void setCat(String newCat){
        this.cat = newCat;
    }

    /**
     * This is to set the description of a movie
     * @param newDescription new description to be set
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    /**
     * This is to set the description of a movie
     * @param newStatus new status to be set
     */
    public void setStatus(String newStatus){
        this.status = newStatus;
    }


    /**
     * This is used in DataBase when setData/deleteData is called
     * @return the Stringlized properties to store back to txt file.
     * e.g. id=2|name=Annabelle|description=this is a horror movie|status=Preview|type=Horror|cat=PG-13
     */
    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|name=" + this.name + "|description=" + this.description + "|status=" + this.status + "|type=" + this.type + "|cat=" + this.cat;
        //TODO make it a standard method
    }
}
