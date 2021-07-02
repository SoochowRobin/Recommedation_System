/**
 * author:  Robin Chen
 * contact: sudacgb@gmail.com
 */
import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    // default constructor
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    // constructor
    public SecondRatings(String moivefile, String ratingsfile) {
        FirstRatings firstrating = new FirstRatings();
        // use loadMoives method to load movies in the arraylist
        myMovies = firstrating.loadMovies(moivefile);
        // use loadRaters method to load raters in the arraylist
        myRaters = firstrating.loadRaters(ratingsfile);
    }

    // return number of movies in the arraylist
    public int getMovieSize(){
        return myMovies.size();
    }

    // return number of raters in the arraylist
    public int getRaterSize(){
        return myRaters.size();
    }

    // a helper method in SecondRatings class
    // it will return 0.0 if there is less than minimalRaters rate this movie
    public double getAverageByID(String id, int minimalRaters){
        int numRatings = 0;
        double totalScore = 0;
        for (Rater currRater: myRaters){
            ArrayList<String> currMovies = currRater.getItemsRated();
            for (String s: currMovies){
                if (s.equals(id)){
                    numRatings ++;
                    totalScore += currRater.getRating(id);
                }
            }
        }

        if (numRatings < minimalRaters){
            return 0.0;
        }else{
            return totalScore/numRatings;
        }
    }

    // getAverageRatings method iterate all movies in myMovies list,
    // and then calculate average ratings for each movie
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> allAverageRatings = new ArrayList<>();
        for (Movie currMovie: myMovies){
            String currMovieID = currMovie.getID();
            double averageRating = getAverageByID(currMovieID, minimalRaters);
            allAverageRatings.add(new Rating(currMovieID,averageRating));
        }
        return allAverageRatings;
    }

    // getTitle return title of provided id number
    public String getTitle(String id){
        for (Movie currMovie: myMovies){
            if (currMovie.getID().equals(id)){
                return currMovie.getTitle();
            }
        }
        return "There is no movie found";
    }

    // getID return ID of provided movie string
    public String getID(String movie){
        for (Movie currMovie: myMovies){
            if (currMovie.getTitle().equals(movie)){
                return currMovie.getID();
            }
        }
        return "There is no movie ID found";
    }
}
