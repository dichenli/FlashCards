/**
 * 
 */
package flashCards;

import static org.junit.Assert.*;

import javax.lang.model.type.NullType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sun.tools.javac.util.List;

/**
 * @author David Matuszek
 */
public class StudyListTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	StudyList studylist;
	Item item1, item2, item3;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    		studylist = new StudyList();
    		item1 = new Item(" 1 + 1", "2");
    		item1.setTimesCorrect(3);
    		item2 = new Item(" 2 + 3", "5"); 
    		item3 = new Item(" 1 + 1", "3");
    		studylist.add(item1);
    }

    /**
     * Test method for {@link flashCards.StudyList#StudyList()}.
     */
    @Test
    public final void testStudyList() {
    		studylist = new StudyList();
    }

    /**
     * Test method for {@link flashCards.StudyList#add(flashCards.Item)}.
     */
    @Test
    public final void testAdd() {
    		studylist.add(item2);
        assertTrue(studylist.getList().contains(item2));
        exception.expect(IllegalArgumentException.class);
        studylist.add(null);
    }

	//The second way to test expected exception
	@Test(expected=IllegalArgumentException.class)
	public void testAddException() {
		studylist.add(item3);
	}
	
    /**
     * Test method for {@link flashCards.StudyList#find(java.lang.String)}.
     */
    @Test
    public final void testFind() {
        assertEquals(item1, studylist.find("1 + 1"));
        assertEquals(null, studylist.find("1 + 2"));
        assertEquals(item1, studylist.find("2  \n"));
    }

    /**
     * Test method for {@link flashCards.StudyList#delete(flashCards.Item)}.
     */
    @Test
    public final void testDelete() {
    		studylist.delete(new Item("1 + 1 ", "2"));
    		assertFalse(studylist.getList().contains(item1));
    		
    		studylist.add(item1);
    		studylist.delete(new Item("1 + 1 ", "3"));
    		assertTrue(studylist.getList().contains(item1));
    		studylist.delete(item1);
    		assertEquals(null, studylist.find("1 + 1 "));
    		
    		
    		studylist.add(item2);
    		studylist.delete(null); // delete null?
    		assertEquals(item2, studylist.find("2 + 3"));
    		
    		studylist.delete(new Item("2 + 3", "5")); // delete twice here
    		studylist.delete(new Item("2 + 3", "5")); //delete when items is empty
    		assertEquals(null, studylist.find("2 + 3"));
    }

    /**
     * Test method for {@link flashCards.StudyList#modify(flashCards.Item, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testModify() {
        studylist.modify(new Item("1 + 1", "2"), "2 + 2 ", " 4");
        assertEquals("2 + 2", item1.getStimulus()); //item1 is one element in studylist, so it will be modified
        assertEquals("4", item1.getResponse());
        
        //illegal arguments, item1 is not modified
        studylist.modify(item1, " ", " ");
        assertEquals("2 + 2", item1.getStimulus());
        assertEquals("4", item1.getResponse());
        
        studylist.modify(item1, null, null);
        assertEquals("2 + 2", item1.getStimulus());
        assertEquals("4", item1.getResponse());
        
        studylist.modify(null, "1 + 2", "3");
        assertEquals("2 + 2", item1.getStimulus());
        assertEquals("4", item1.getResponse());
    }

}
