/**
 * Write a description of MinutesFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinutesFilter implements Filter {
    private int Min;
	private int Max;
	
	public MinutesFilter(int min, int max) {
		Min = min;
		Max = max;
	}
	
	@Override
	public boolean satisfies(String id) {
		return MovieDatabase.getMinutes(id) >= Min && MovieDatabase.getMinutes(id) <= Max;
	}
}
