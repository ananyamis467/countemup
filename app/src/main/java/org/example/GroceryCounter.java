package org.example;

//code
public class GroceryCounter {
    private int count;           // Stores value from 0000 to 9999
    private int overflowCount;   // Tracks number of overflows
    
    /**
     * Initializes the counter to 0000 with no overflows
     */
    public GroceryCounter() {
        this.count = 0;
        this.overflowCount = 0;
    }
    
    /**
     * Increments the tens place (adds 1000 cents / $10.00)
     */
    public void tens() {
        count += 1000;
        checkOverflow();
    }
    
    /**
     * Increments the ones place (adds 100 cents / $1.00)
     */
    public void ones() {
        count += 100;
        checkOverflow();
    }
    
    /**
     * Increments the tenths place (adds 10 cents / $0.10)
     */
    public void tenths() {
        count += 10;
        checkOverflow();
    }
    
    /**
     * Increments the hundredths place (adds 1 cent / $0.01)
     */
    public void hundredths() {
        count += 1;
        checkOverflow();
    }
    
    /**
     * Checks if count exceeds 9999 and handles overflow
     */
    private void checkOverflow() {
        if (count > 9999) {
            count = count % 10000;
            overflowCount++;
        }
    }
    
    /**
     * Returns the current counter value formatted as currency
     * @return formatted string like "$12.34" or "$5.09"
     */
    public String total() {
        int dollars = count / 100;
        int cents = count % 100;
        return String.format("$%d.%02d", dollars, cents);
    }
    
    /**
     * Returns the number of times the counter has overflowed
     * @return overflow count
     */
    public int overflows() {
        return overflowCount;
    }
    
    /**
     * Resets the counter to 0000 and clears overflow count
     */
    public void clear() {
        count = 0;
        overflowCount = 0;
    }
    
    /**
     * Gets the raw counter value (for testing purposes)
     * @return current count value (0-9999)
     */
    protected int getCount() {
        return count;
    }
}
