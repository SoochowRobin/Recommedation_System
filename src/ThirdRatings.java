import java.util.ArrayList;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;

    // default constructor
    public ThirdRatings() {
        this("ratings.csv");
    }

    public ThirdRatings(String ratingsfile) {
        FirstRatings firstrating = new FirstRatings();
        myRaters = firstrating.loadRaters(ratingsfile);
    }

    public int getRaterSize(){
        // return the number of raters.
        return myRaters.size();
    }

    /* This method returns a double representing the average movie rating for this ID
         * if there are at least minimalRaters ratings.
           If there are not minimalRaters ratings, then it returns 0.0.*/
    private double getAverageByID(String movieID, int minimalRaters){
        int numRatings = 0;
        double totalScore = 0;
        for (Rater currRater: myRaters){
            ArrayList<String> currMovies = currRater.getItemsRated();
            for (String s: currMovies){
                if (s.equals(movieID)){
                    numRatings += 1;
                    totalScore += currRater.getRating(movieID);
                }
            }
        }
        if (numRatings < minimalRaters){
            return 0.0;
        } else {
            return totalScore/numRatings;
        }
    }

    /**
     * This method should find the average rating for every movie that has been rated by
     * at least minimalRaters raters. Store each such rating in a Rating object where the
     * movie ID and the average rating are used in creating the Rating object.
     * @param minimalRaters
     * @return
     */
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        // Get the ArrayList of Movies from MovieDatabase.
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> allAverageRatings = new ArrayList<>();
        for (String currMovieID: movies){
            double averageRating = getAverageByID(currMovieID, minimalRaters);
            allAverageRatings.add(new Rating(currMovieID, averageRating));
        }
        return allAverageRatings;
    }

    /**
     * This method should create and return an ArrayList of type Rating of all the movies
     * that have at least minimalRaters ratings and satisfies the filter criteria.
     * This method will need to create the ArrayList of type String of movie IDs
     * from the MovieDatabase using the filterBy method before calculating those averages.
     */
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){

        ArrayList<String> movieIDs = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> averageRatings = new ArrayList<>();
        for (String s: movieIDs){
            double ratingValue = getAverageByID(s, minimalRaters);
            averageRatings.add(new Rating(s, ratingValue));
        }
        return averageRatings;
    }

}
