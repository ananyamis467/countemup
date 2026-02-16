package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested; //like nested for loop, but for tests
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

//tests
class GroceryCounterTest {
    
    private GroceryCounter counter;
    
    @BeforeEach
    void setUp() {
        counter = new GroceryCounter();
    }
    
    //initialization tests
    @Nested
    class InitializationTests {
        
        @Test
        void testInitialValue() {
            assertEquals("$0.00", counter.total());
        }
        
        @Test
        void testInitialOverflowCount() {
            assertEquals(0, counter.overflows());
        }
    }
    
    //increment tests
    @Nested
    class SingleIncrementTests {
        
        @Test
        void testTensIncrement() {
            counter.tens();
            assertEquals("$10.00", counter.total());
        }
        
        @Test
        void testOnesIncrement() {
            counter.ones();
            assertEquals("$1.00", counter.total());
        }
        
        @Test
        void testTenthsIncrement() {
            counter.tenths();
            assertEquals("$0.10", counter.total());
        }
        
        @Test
        void testHundredthsIncrement() {
            counter.hundredths();
            assertEquals("$0.01", counter.total());
        }
    }
    
    @Nested
    class MultipleIncrementTests {
        
        @Test
        void testMultipleTens() {
            counter.tens();
            counter.tens();
            counter.tens();
            assertEquals("$30.00", counter.total());
        }
        
        @Test
        void testMultipleOnes() {
            for (int i = 0; i < 5; i++) {
                counter.ones();
            }
            assertEquals("$5.00", counter.total());
        }
        
        @Test
        void testMultipleTenths() {
            for (int i = 0; i < 7; i++) {
                counter.tenths();
            }
            assertEquals("$0.70", counter.total());
        }
        
        @Test
        void testMultipleHundredths() {
            for (int i = 0; i < 9; i++) {
                counter.hundredths();
            }
            assertEquals("$0.09", counter.total());
        }
    }
    
    @Nested
    class MixedIncrementTests {
        
        @Test
        void testMixedIncrements() {
            counter.tens();      // $10.00
            counter.ones();      // $11.00
            counter.tenths();    // $11.10
            counter.hundredths(); // $11.11
            assertEquals("$11.11", counter.total());
        }
        
        @Test
        void testBuildSpecificValue() {
            counter.tens();      // $10.00
            counter.ones();      // $11.00
            counter.ones();      // $12.00
            counter.tenths();    // $12.10
            counter.tenths();    // $12.20
            counter.tenths();    // $12.30
            counter.hundredths(); // $12.31
            counter.hundredths(); // $12.32
            counter.hundredths(); // $12.33
            counter.hundredths(); // $12.34
            assertEquals("$12.34", counter.total());
        }
        
        @Test
        void testMaxValue() {
            // Add $90.00
            for (int i = 0; i < 9; i++) {
                counter.tens();
            }
            // Add $9.00
            for (int i = 0; i < 9; i++) {
                counter.ones();
            }
            // Add $0.90
            for (int i = 0; i < 9; i++) {
                counter.tenths();
            }
            // Add $0.09
            for (int i = 0; i < 9; i++) {
                counter.hundredths();
            }
            assertEquals("$99.99", counter.total());
        }
    }
    
    //formatting tests
    @Nested
    class FormattingTests {
        
        @Test
        void testFormattingWithLeadingZero() {
            // Build 509: $5.09
            for (int i = 0; i < 5; i++) {
                counter.ones();
            }
            counter.tenths();
            for (int i = 0; i < 9; i++) {
                counter.hundredths();
            }
            assertEquals("$5.09", counter.total());
        }
        
        @Test
        void testFormattingSingleCent() {
            counter.hundredths();
            assertEquals("$0.01", counter.total());
        }
        
        @Test
        void testFormattingSingleDime() {
            counter.tenths();
            assertEquals("$0.10", counter.total());
        }
        
        @Test
        void testFormattingEvenDollars() {
            counter.tens();
            assertEquals("$10.00", counter.total());
        }
    }
    
    //overflow tests
    @Nested
    class OverflowTests {
        
        @Test
        void testOverflowOnHundredths() {
            // Build $99.99
            for (int i = 0; i < 9999; i++) {
                counter.hundredths();
            }
            assertEquals("$99.99", counter.total());
            assertEquals(0, counter.overflows());
            
            // One more should overflow
            counter.hundredths();
            assertEquals("$0.00", counter.total());
            assertEquals(1, counter.overflows());
        }
        
        @Test
        void testOverflowOnTenths() {
            // Build $99.99
            for (int i = 0; i < 9999; i++) {
                counter.hundredths();
            }
            
            // Add $0.10 should overflow to $0.09
            counter.tenths();
            assertEquals("$0.09", counter.total());
            assertEquals(1, counter.overflows());
        }
        
        @Test
        void testOverflowOnOnes() {
            // Build $99.99
            for (int i = 0; i < 9999; i++) {
                counter.hundredths();
            }
            
            // Add $1.00
            counter.ones();
            assertEquals("$0.99", counter.total());
            assertEquals(1, counter.overflows());
        }
        
        @Test
        void testOverflowOnTens() {
            // Build $99.99
            for (int i = 0; i < 9999; i++) {
                counter.hundredths();
            }
            
            // Add $10.00
            counter.tens();
            assertEquals("$9.99", counter.total());
            assertEquals(1, counter.overflows());
        }
        
        @Test
        void testMultipleOverflows() {
            // Overflow 3 times
            for (int i = 0; i < 30000; i++) {
                counter.hundredths();
            }
            assertEquals("$0.00", counter.total());
            assertEquals(3, counter.overflows());
        }
        
        @Test
        void testLargeOverflow() {
            // Add 25,000 cents = $250.00 (should wrap to $50.00 with 2 overflows)
            for (int i = 0; i < 2500; i++) {
                counter.tens();
            }
            assertEquals("$50.00", counter.total());
            assertEquals(2, counter.overflows());
        }
    }
    
    //clear tests
    @Nested
    class ClearTests {
        
        @Test
        void testClearResetsTotal() {
            counter.tens();
            counter.ones();
            counter.tenths();
            counter.hundredths();
            
            counter.clear();
            assertEquals("$0.00", counter.total());
        }
        
        @Test
        void testClearResetsOverflow() {
            // Cause multiple overflows
            for (int i = 0; i < 20000; i++) {
                counter.hundredths();
            }
            assertEquals(2, counter.overflows());
            
            counter.clear();
            assertEquals(0, counter.overflows());
        }
        
        @Test
        void testClearAllowsNormalOperation() {
            counter.tens();
            counter.clear();
            
            counter.ones();
            counter.tenths();
            assertEquals("$1.10", counter.total());
            assertEquals(0, counter.overflows());
        }
        
        @Test
        void testMultipleClearCalls() {
            counter.tens();
            counter.clear();
            counter.clear();
            counter.clear();
            
            assertEquals("$0.00", counter.total());
            assertEquals(0, counter.overflows());
        }
    }
    
    //edge case tests
    @Nested
    class EdgeCaseTests {
        
        @Test
        void testBoundaryAt9999() {
            // Build exactly 9999
            for (int i = 0; i < 9999; i++) {
                counter.hundredths();
            }
            assertEquals("$99.99", counter.total());
            assertEquals(0, counter.overflows());
        }
        
        @Test
        void testBoundaryAt10000() {
            // Build exactly 10000 (should be $0.00 with 1 overflow)
            for (int i = 0; i < 10000; i++) {
                counter.hundredths();
            }
            assertEquals("$0.00", counter.total());
            assertEquals(1, counter.overflows());
        }
        
        @Test
        void testOverflowNearMax() {
            // Build $99.95
            for (int i = 0; i < 9995; i++) {
                counter.hundredths();
            }
            
            // Add $0.10 should overflow
            counter.tenths();
            assertEquals("$0.05", counter.total());
            assertEquals(1, counter.overflows());
        }
    }
    
    //integration tests
    @Nested
    class IntegrationTests {
        
        @Test
        void testRealisticCheckout() {
            // Item 1: $12.99
            counter.tens();
            counter.ones();
            counter.ones();
            for (int i = 0; i < 9; i++) {
                counter.tenths();
            }
            for (int i = 0; i < 9; i++) {
                counter.hundredths();
            }
            assertEquals("$12.99", counter.total());
            
            counter.clear();
            
            // Item 2: $5.49
            for (int i = 0; i < 5; i++) {
                counter.ones();
            }
            for (int i = 0; i < 4; i++) {
                counter.tenths();
            }
            for (int i = 0; i < 9; i++) {
                counter.hundredths();
            }
            assertEquals("$5.49", counter.total());
        }
        
        @Test
        void testComplexSequence() {
            // Build to near overflow
            for (int i = 0; i < 9990; i++) {
                counter.hundredths();
            }
            assertEquals("$99.90", counter.total());
            
            // Cause overflow
            counter.tens();
            assertEquals("$9.90", counter.total());
            assertEquals(1, counter.overflows());
            
            // Clear and verify fresh state
            counter.clear();
            assertEquals("$0.00", counter.total());
            assertEquals(0, counter.overflows());
            
            // Continue using
            counter.ones();
            assertEquals("$1.00", counter.total());
        }
    }
}
