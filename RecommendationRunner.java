import java.util.ArrayList;
import java.util.Random;

public class RecommendationRunner implements Recommender {
    private Random myRandom;
    private int toRateNum;
    private int numSimilarRaters;
    private int minimalRaters;
    private int maxRecNum;

    public RecommendationRunner(){
        myRandom = new Random();
        toRateNum = 10;
        numSimilarRaters = 10;
        minimalRaters = 3;
        maxRecNum = 20;
    }

    @Override
    public ArrayList<String> getItemsToRate() {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        ArrayList<String> toRate = new ArrayList<String>();
        Filter f = new TrueFilter();
        ArrayList<String> allMovies = MovieDatabase.filterBy(f);
        for (int k=0; k<toRateNum; k++){
            int currIdx = myRandom.nextInt(MovieDatabase.size());;
            String currMovieID = allMovies.get(currIdx);
            toRate.add(currMovieID);
        }
        return toRate;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {
//        RaterDatabase.initialize("ratings.csv");
//        System.out.println("read data for " + RaterDatabase.size() + " raters");

        MovieDatabase.initialize("ratedmoviesfull.csv");
        FourthRatings fourth = new FourthRatings();
        ArrayList<Rating> result = fourth.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);
        int num = result.size();
        if (num == 0){
            System.out.println("Recommendation List:");
            System.out.println("Nothing to suggest, do you enjoy watching ?");
        } else {
            if (num > maxRecNum){
                num = maxRecNum;
            }
            String header = ("<table> <tr> <th>Title</th> <th>Rating Value</th> <th>Genre</th></tr>");
            StringBuilder body = new StringBuilder();
            for (int k=0; k<num; k++){
                Rating currRating = result.get(k);
                String currMovieID = currRating.getItem();
                String currMovieTitle = MovieDatabase.getTitle(currMovieID);
                double currRatingValue = currRating.getValue();
                String currGenre = MovieDatabase.getGenres(currMovieID);
                body.append(printOut(currMovieTitle, currRatingValue, currGenre));
            }
            System.out.println(header + body + "</table>");
        }

    }
    private String printOut(String title, double value, String genre){
        return ("<tr> <td>" + title + "</td> <td>" + value + "</td> <td>" + genre + "</td></tr>");
    }
}
