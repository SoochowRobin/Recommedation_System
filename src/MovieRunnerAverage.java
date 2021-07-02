/**
 * author: Robin Chen
 * contact: sudacgb@gmail.com
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 * MovieRunnerAverage class is used to create a SecondRatings
 * object to test method implemented in SecondRatings class
 */
public class MovieRunnerAverage {
    private String movieFileName;
    private String ratingFileName;
    private SecondRatings sr;

    public MovieRunnerAverage(){
        movieFileName = "ratedmoviesfull.csv";
        ratingFileName = "ratings.csv";
        sr = new SecondRatings(movieFileName, ratingFileName);
    }

    public void printAverageRatings(){
        System.out.println("There are " + sr.getMovieSize() + " movies in the file.");
        System.out.println("There are " + sr.getRaterSize() + " raters in the file.");

        // when we want to get how many movies have more than numRaings, we need to
        // do some modification here
        int numRating = 12;
        ArrayList<Rating> ratings = sr.getAverageRatings(numRating);
        // prepare to print them in a sorted way
        Collections.sort(ratings);

        System.out.println("Rating values of Movies with at least " + numRating + " ratings:");
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                String currMovieID = currRating.getItem();
                System.out.println(currValue + "  " + sr.getTitle(currMovieID));
            }
        }
    }

    // this method get average value of each movie so set numRating to 0
    public void getAverageRatingOneMovie(){
        int numRating = 0;
        ArrayList<Rating> ratings = sr.getAverageRatings(numRating);

        String movieTitle = "Vacation";
        String movieID = sr.getID(movieTitle);
        double value = -1;
        for (Rating currRating: ratings){
            if (currRating.getItem().equals(movieID)){
                value = currRating.getValue();
            }
        }
        System.out.println("The average rating for " + movieTitle + " is " + value + ".");
    }

//     test start place
//    public static void main(String[] args) {
//        MovieRunnerAverage test = new MovieRunnerAverage();
//        test.printAverageRatings();
//
//    }
}
