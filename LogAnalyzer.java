/**
 * Read web server data and analyse hourly access patterns.
 * 
 * This class can count the accesses per hour, determine the busiest or quietest hour, and determine the busiest
 * two-hour periods. It can also print access counts and log data.
 * 
 * This class uses the LogfileReader class to access the underlying log data.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @author Christian Gorosica
 * @version    2025.11.03
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader("demo.log");
        
    }
    
    /**
     * Create an object to analyze hourly web accesses from a specific log file.
     * 
     * @param filename the name of the log file to analyze
     */
    public LogAnalyzer(String filename) {
        hourCounts = new int[24];
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * Count the total number of accesses recorded in the log file.
     * 
     * This method sums all of the hourly access counts stored in the hourCounts array.
     * 
     * @return the total number of access across all hours
     */
    public int numberOfAccesses() {
        int total = 0;
        
        for (int i = 0; i < hourCounts.length; i++) {
            total += hourCounts[i];
        }
        
        return total;
    }
    
    /**
     * Determine the hour with the highest number of accesses in the log file.
     * 
     * This method examines the hourly access counts stored in the hourCounts array and returns the index of the hour with
     * the largest count.
     * 
     * @return the hour (0-23) with the most accesses
     */
    public int busiestHour() {
        int busiest = 0;
        int maxCount = 0;
        
        for (int i = 0; i < hourCounts.length; i++) {
            
            if (hourCounts[i] > maxCount) {
                maxCount = hourCounts[i];
                busiest = i;
            }
        }
        
        return busiest;
    }
    
    /**
     * Determine the hour with the lowest number of accesses in the log file.
     * 
     * This method examines the hourly counts stored in the hourCounts array and returns the index of the hour with
     * the lowest count.
     * 
     * @return the hour (0-23) with the most accesses
     */
    public int quietestHour() {
        int quietest = 0;
        int minCount = hourCounts[0];
        
        for (int i = 1; i < hourCounts.length; i++) {
            
            if (hourCounts[i] < minCount) {
                minCount = hourCounts[i];
                quietest = i;
            }
        }
        
        return quietest;
    }
    
    /**
     * Determine the two-hour period with the highest number of accesses in the log file.
     * 
     * This method examines the sum of the counts of every two hours in the hourCounts array and returns the index of the first hour
     * of the pair with the highest sum.
     * 
     * @return the first hour (0-22) of a consecutive pair whose sum has the most accesses
     */
    public int busiestTwoHour() {
        int busiestStartHour = 0;
        int maxSum = 0;
        
        for (int i = 0; i < 23; i++) {
            int sum = hourCounts[i] + hourCounts[i + 1];
            
            if (sum > maxSum) {
                maxSum = sum;
                busiestStartHour = i;
            }
        }
        
        return busiestStartHour;
    }
    
}
