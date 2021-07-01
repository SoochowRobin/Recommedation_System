import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;


public class FirstRatings {
    // this method is to load movies information in an ArrayList
    // parameter is a filename: String
    public ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> movies = new ArrayList<>();
        // FileResource is built-in function in edu.duke package
        FileResource fr = new FileResource("data/" +filename);
        // get all movies in the file
        CSVParser movieParser = fr.getCSVParser();
        // create movie object for each movie and store them in the arraylist
        for (CSVRecord currentRow: movieParser) {
            String currId = currentRow.get("id");
            String currTitle = currentRow.get("title");
            String currYear = currentRow.get("year");
            String currGenres = currentRow.get("genre");
            String currDirector = currentRow.get("director");
            String currCountry = currentRow.get("country");
            String currPoster = currentRow.get("poster");
            int currMinutes = Integer.parseInt(currentRow.get("minutes"));
            // create movie object for each movie and add them to arraylist
            movies.add(new Movie(currId, currTitle, currYear, currGenres, currDirector, currCountry,
                    currPoster, currMinutes));
        }
        return movies;
    }

    // this method is to load Raters in an ArrayList
    // parameter is a filename: String
    public ArrayList<Rater> loadRaters(String filename){
        ArrayList<Rater> raters = new ArrayList<>();
        FileResource fr = new FileResource("data/"+filename);
        CSVParser raterParser = fr.getCSVParser();
        // Initial the String to check if the rater is existed or not.
        String existed = "";
        for (CSVRecord currentRow: raterParser){
            String raterId = currentRow.get("rater_id");
            String movieId = currentRow.get("movie_id");
            double rating = Double.parseDouble(currentRow.get("rating"));

            // Ckeck if the current rater is existed in the ArrayList "raters" or not.
            boolean exist = false;
            for (Rater r: raters){
                if (r.getID().equals(raterId)) {
                    exist = true;
                    break;
                }
            }

            if (existed.contains(raterId)){
                exist = true;
            }

            if (!exist){
                Rater currRater = new PlainRater(raterId);
//                Rater currRater = new EfficientRater(raterId);
                currRater.addRating(movieId, rating);
                raters.add(currRater);
                // Add the rater's ID to the "existed" String.
                existed += raterId + " ";
            } else {
                for (Rater r: raters){
                    if (r.getID().equals(raterId)){
                        r.addRating(movieId, rating);
                    }
                }
            }
        }
        return raters;

    }

}
