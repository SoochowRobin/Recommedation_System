// PlainRater class is used to store rater and his/her movies and rating

import java.util.*;

public class PlainRater extends Rater {
    private final String myID;
    private final ArrayList<Rating> myRatings;

    public PlainRater(String id) {
        super();
        myID = id;
        myRatings = new ArrayList<Rating>();
    }

    public void addRating(String item, double rating) {
        myRatings.add(new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        for (Rating myRating : myRatings) {
            if (myRating.getItem().equals(item)) {
                return true;
            }
        }

        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        for (Rating myRating : myRatings) {
            if (myRating.getItem().equals(item)) {
                return myRating.getValue();
            }
        }

        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for (Rating myRating : myRatings) {
            list.add(myRating.getItem());
        }

        return list;
    }
}