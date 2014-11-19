/**
 * 
 */
package flashCards;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author David Matuszek
 */
public class ItemTest {

    /**
     * @throws java.lang.Exception
     */
	Item item1;
	Item item2;
	Item item3;
    @Before
    public void setUp() throws Exception {
    	item1 = new Item("1 + 1", "2");
    	item2 = new Item("2 + 3", "5");
    	
    }

    /**
     * Test method for {@link flashCards.Item#Item(java.lang.String, java.lang.String)}.
     */
    @Test (expected = IllegalArgumentException.class) 
    public final void testItem1() {
    	item3 = new Item("", "6");
    }
    
    @Test (expected = IllegalArgumentException.class) 
    public final void testItem2() {
    	item3 = new Item("1 + 1", "");
    }

    /**
     * Test method for {@link flashCards.Item#setStimulus(java.lang.String)} and
     * {@link flashCards.Item#getStimulus()} (combined).
     */
    @Test
    public final void testSetAndGetStimulus() {
        item1.setStimulus("1 + 3");
        String expected1 = "1 + 3";
        assertEquals(expected1, item1.getStimulus());
        item2.setStimulus("3 + 5");
        String expected2 = "3 + 5";
        assertEquals(expected2, item2.getStimulus());
    }
    
    @Test (expected = IllegalArgumentException.class) 
    public final void testSetStimulus1() {
    	item1.setStimulus("");
    }

    /**
     * Test method for {@link flashCards.Item#setResponse(java.lang.String)} and
     * {@link flashCards.Item#getResponse()} (combined).
     */
    @Test
    public final void testSetAndGetResponse() {
    	 item1.setResponse("4");
         String expected1 = "4";
         assertEquals(expected1, item1.getResponse());
         item2.setResponse("8");
         String expected2 = "8";
         assertEquals(expected2, item2.getResponse());
    }
    
    @Test (expected = IllegalArgumentException.class) 
    public final void testSetResponse1() {
    	item1.setResponse("");
    }


    /**
     * Test method for {@link flashCards.Item#setTimesCorrect(int)} and
     * {@link flashCards.Item#getTimesCorrect()} (combined).
     */
    @Test
    public final void testSetAndGetTimesCorrect() {
    	item1.setTimesCorrect(3);;
        int expected1 = 3;
        assertEquals(expected1, item1.getTimesCorrect());
        item2.setTimesCorrect(1);;
        int expected2 = 1;
        assertEquals(expected2, item2.getTimesCorrect());
    }
    
    @Test (expected = IllegalArgumentException.class) 
    public final void testSetTimesCorrect1() {
    	item1.setTimesCorrect(-1);;
    }


}
