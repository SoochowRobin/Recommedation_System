import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;


public class FirstRatings {
    // this method is to load movies information in an ArrayList
    // parameter is a filename: String
    public ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> movies = new ArrayList<>();
        // FileResource is built-in function in edu.duke package
        FileResource fr = new FileResource(filename);
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
//                Rater currRater = new PlainRater(raterId);
                // use EfficientRater here
                Rater currRater = new EfficientRater(raterId);
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

    // test loadMovies function
    public void testLoadMovies(String filename){
        // store movies in local variable movies
        ArrayList<Movie> movies = loadMovies(filename);
        System.out.println("There are "+ movies.size() + " records");

        // record number of Comedy
        int numComedy = 0;
        for (Movie currMovie: movies){
            if (currMovie.getGenres().contains("Comedy")){
                numComedy ++;
            }
        }
        System.out.println("There are " + numComedy + " comedy in the file.");

        // record number of film which time exceeds 150 min
        int numLength150 = 0;
        for (Movie currMovie: movies){
            if (currMovie.getMinutes() > 150){
                numLength150 ++;
            }
        }
        System.out.println("There are "+ numLength150 + " movies which their length are more than 150 min");

        // remember movies that have more than one director
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (Movie currMovie: movies){
            String director = currMovie.getDirector().trim();
            if (!director.contains(",")){
                if (!map.containsKey(director)){
                    map.put(director, new ArrayList<String>());
                }
                String title = currMovie.getTitle();
                map.get(director).add(title);
            }else{
                // there are more than one director
                while (director.contains(",")){
                    int idxComma = director.indexOf(",");
                    String currDirector = director.substring(0, idxComma);

                    if (!map.containsKey(currDirector)){
                        map.put(currDirector, new ArrayList<String>());
                    }

                    String title = currMovie.getTitle();
                    map.get(currDirector).add(title);

                    director = director.substring(idxComma+1).trim();
                }
            }
        }
        // test max number of director
        int maxNumOfMoviesByDirector = 0;
        for (String s: map.keySet()){
            if (map.get(s).size() > maxNumOfMoviesByDirector){
                maxNumOfMoviesByDirector = map.get(s).size();
            }
        }
        System.out.println("The maximum number of films directed by one director is " + maxNumOfMoviesByDirector);

        String directorWithMaxMovies = "";
        for (String s: map.keySet()){
            if (map.get(s).size() == maxNumOfMoviesByDirector){
                directorWithMaxMovies += s + ", ";
            }
        }
        System.out.println("Names of the directors who directed the maximum number of movies " +
                directorWithMaxMovies.substring(0, directorWithMaxMovies.length()-2));

    }

    // test loadRaters function
    public void testLoadRaters(String filename){
        ArrayList<Rater> raters = loadRaters(filename);
        System.out.println("There are " + raters.size() + " raters.");

        int mxnNum = 0;
        int num179 = 0;
        Set<String> unique = new HashSet<>();
        for (Rater currRater: raters){
            if (currRater.numRatings() > mxnNum){
                mxnNum = currRater.numRatings();
            }
            System.out.println("Rater ID " + currRater.getID() +" :" +currRater.numRatings() + "Ratings");
            ArrayList<String> items = currRater.getItemsRated();
            for (String item: items){
                unique.add(item);
                double rating = currRater.getRating(item);
                if (item.equals("1798709")){
                    num179 ++;
                }
                System.out.print(item + " " + rating + "; ");
            }
            System.out.println("\n");
        }
        System.out.println("mxnNum " +mxnNum);
        System.out.println("1798709 " + num179);
        System.out.println("unique number is " + unique.size());
    }

    // comment out this part, this part used to test and get result of step_one
//    public static void main(String[] args) {
//        FirstRatings test = new FirstRatings();
//        test.testLoadMovies("ratedmoviesfull.csv");
//        test.testLoadRaters("ratings.csv");
//    }
}
