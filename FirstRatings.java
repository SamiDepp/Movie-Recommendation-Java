import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.csv.*;

import edu.duke.*;

/**
 * FirstRatings process the movie and ratings data and to answer questions about them. 
 * It uses CSVParser and CSVRecord.
 * 
 * @author ShreyamDuttaGupta
 * @version 1.0.1
 */
public class FirstRatings {
    
    public ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> list = new ArrayList<Movie>();
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord rec: parser) {
            Movie m = new Movie(rec.get("id"), rec.get("title"), rec.get("year"), rec.get("genre"), rec.get("director"),rec.get("country"), rec.get("poster"), Integer.parseInt(rec.get("minutes")));
            list.add(m);
        }
        return list;
    }
    
    public void testLoadMovies(){
        //String filename = "data/ratedmovies_short.csv";
        String filename = "data/ratedmoviesfull.csv";
		ArrayList<Movie> movieList = loadMovies(filename);
        System.out.println("Total number of movies = " + movieList.size());
        /*for(Movie m : movieList){
            System.out.println(m.toString());
        }*/
       HashMap<String, Integer> map = new HashMap<String, Integer>();
       int genreCount = 0;
       int minCount = 0;
       int maxDirector = 0;
       String directorName = " ";
       for(Movie m : movieList){
           if(m.getGenres ().contains("Comedy")) 
           genreCount++;   
           if(m.getMinutes() > 150)
           minCount++;
           
           if(map.containsKey(m.getDirector()))
				map.put(m.getDirector(), map.get(m.getDirector()) + 1);
			else
				map.put(m.getDirector(), 1);
           
       }
       System.out.println("Total number of movies with Genre comedy = " + genreCount);
       System.out.println("Total number of movies with length greater than 150mins = " + minCount);
       for(String s : map.keySet()){
           if(map.get(s) > maxDirector){
                maxDirector = map.get(s);
                directorName = s;
            }
        }
       System.out.println("Maximum number of movies by any director = " + maxDirector + " by " + directorName);    
    }
    
    
    public ArrayList<EfficientRater> loadRaters(String filename) {
		ArrayList<EfficientRater> list = new ArrayList<EfficientRater>();
		FileResource fr = new FileResource(filename);
		CSVParser parser = fr.getCSVParser();
		int index = 0;
		for(CSVRecord rec: parser) {
			if(index == 0) {
				EfficientRater r = new EfficientRater(rec.get("rater_id"));
				r.addRating(rec.get("movie_id"), Integer.parseInt(rec.get("rating")));
				list.add(index, r);
				index++;
			}
			else if(list.get(index-1).getID().equals(rec.get("rater_id"))) {
				list.get(index-1).addRating(rec.get("movie_id"), Integer.parseInt(rec.get("rating")));
			}
			else {
				EfficientRater r = new EfficientRater(rec.get("rater_id"));
				r.addRating(rec.get("movie_id"), Integer.parseInt(rec.get("rating")));
				list.add(index, r);
				index++;
			}
		}
		return list;
	}

	
	public void testLoadRaters() {
		//String filename = "data/ratings_short.csv";
		String filename = "data/ratings.csv";
		ArrayList<EfficientRater> raterList = loadRaters(filename);
		System.out.println("--------------------------------------------------------------");
		System.out.println(filename.substring(5) + " has " + raterList.size() + " raters");
		
		// To find the list of raters and respective rating
		/*
		for(Rater r: raterList) {
		    System.out.println("Rater ID: " + r.getID() + " whose number of rating is" + r.numRatings());
		    ArrayList<String> movieId = r.getItemsRated();
		    for(String s : movieId ){		    
		    System.out.println("Movie ID is: " + s + " Rating is: " + r.getRating(s));
		  }
		  }
		  */
		 
		// To find the number of ratings for a particular rater
		/*
		String rater_id= "193";
		for(Rater r: raterList) {
		  if(r.getID().equals(rater_id)){
		      System.out.println("number of rating is: " + r.numRatings());
		  }
		  
		  }
		 */

		// To find the maximum number of ratings by any rater
		int max = 0;
		for(Rater r: raterList) {
		      if(r.numRatings() > max)
		          max = r.numRatings();  
		  }
		
		for(Rater r: raterList) {
		    if(r.numRatings() == max)
		      System.out.println("Rater " + r.getID() + " has the maximum number of ratings which is " + max);
		} 

	    // To find number of user rated a particular movie
	    
	    HashMap<String, Integer> map = new HashMap<String, Integer>();
	    map.clear();
	    for(EfficientRater r: raterList) {
		    //System.out.println("Rater ID: " + r.getID() + " whose number of rating is" + r.numRatings());
		    ArrayList<String> movieId = r.getItemsRated();
		    for(String s : movieId ){	
		        if(!map.containsKey(s)){
		            map.put(s,1);
		          } else
		            map.put(s, map.get(s)+1);
		    }
		  }
		int maxValue = Collections.max(map.values());
		for(String s : map.keySet()){
		  if(s.contains("1798709"))
		      System.out.println("Number of ratings by 1798709 : " + map.get(s));
		      
           }
		
		
		String str = " ";
		for (String s : map.keySet()){
		    if(maxValue == map.get(s))
		             System.out.println("Movie ID: " + s + " has been rated: " + maxValue + " times");
		      }
		System.out.println(map.size() + " number of movies have been rated");
		
		int count = 0;
		for (String s : map.keySet()){
		    if(map.get(s) >= 50)
		           count++;
		      }
		System.out.println(count + " movies have 50 or more ratings " );
	}

	public static void main(String[] args){
    	new FirstRatings().testLoadRaters();

	}
}
