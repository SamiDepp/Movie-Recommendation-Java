import java.util.*;

/**
 * Write a description of ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ThirdRatings {
    private ArrayList<EfficientRater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
     public ThirdRatings(String ratingsfile) {
    	FirstRatings fr = new FirstRatings();
    	myRaters = fr.loadRaters(ratingsfile);
    }
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    private double getAverageByID(String id, int minimalRaters) {
    	double average = 0;
    	double sum = 0;
    	int countRaters = 0;
    	for(EfficientRater r: myRaters) {
    		if(r.hasRating(id)) {
    			countRaters++;  
    			sum += r.getRating(id);
    		}
    	}
    	if(countRaters >= minimalRaters)
    		average = sum / countRaters;
    	return average;
    }
    
     public ArrayList<Rating> getAverageRatings(int minimalRaters) {
    	ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
    	ArrayList<Rating> ratings = new ArrayList<Rating>();
    	for(String id: movies) {
    		double averageRating = getAverageByID(id,minimalRaters);
    		if(averageRating!=0){
    			Rating r = new Rating(id, averageRating);
    			ratings.add(r);
    		}
    	}
    	return ratings;

    }
    
     public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
    	ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
    	ArrayList<Rating> ratings = new ArrayList<Rating>();
       	for(String id: movies) {
    		double averageRating = getAverageByID(id,minimalRaters);
    		if(averageRating!=0){
    			Rating r = new Rating(id, averageRating);
    			ratings.add(r);
    		}
    	}
    	return ratings;
    }
    
}
