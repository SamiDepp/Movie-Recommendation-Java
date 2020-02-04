import java.util.*;

/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerAverage {
    
    public void printAverageRatings(){
        //SecondRatings sr = new SecondRatings("ratedmovies_short.csv", "ratings_short.csv");
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        System.out.println("Number of movies in the file is " + sr.getMovieSize() + 
                            " & Number of raters is " + sr.getRaterSize());
        int minimalRaters = 12;
	    ArrayList<Rating> ratings = sr.getAverageRatings(minimalRaters);
	    Collections.sort(ratings);
		System.out.println(ratings.size());
		for(Rating r: ratings) {
	    	System.out.println(r.getValue() + " " + sr.getTitle(r.getItem()));
	    }                            
    }

    public void getAverageRatingOneMovie() {
		SecondRatings sr = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        //SecondRatings sr = new SecondRatings("ratedmovies_short.csv", "ratings_short.csv");
		String movieTitle = "Vacation";
		String movieID = sr.getID(movieTitle);
		int minimalRaters = 0;
		ArrayList<Rating> ratings = sr.getAverageRatings(minimalRaters);
		for(Rating r: ratings) {
			if(r.getItem().equals(movieID))
				System.out.println("The average rating for the movie \"" + movieTitle + "\" is " + r.getValue());
		}
	}
	public static void main(String[] args){
		new MovieRunnerAverage().printAverageRatings();

	}
}
