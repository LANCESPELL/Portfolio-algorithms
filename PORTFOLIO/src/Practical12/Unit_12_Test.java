package Practical12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Unit_12_Test {

    private Transcript t;
    private Module y1_low, y1_high, y2_mid;

    @BeforeEach
    void setup() {
        t = new Transcript(1);
        y1_low  = new Module("CS101", "Programming", Year.FIRST_YEAR, 20, 40);
        y1_high = new Module("CS102", "Algorithms",  Year.FIRST_YEAR, 20, 80);
        y2_mid  = new Module("CS201", "Databases",   Year.SECOND_YEAR, 20, 60);
    }

    // -------------------------
    // Module tests
    // -------------------------

    @Test
    void module_toString_isReadable() {
        String s = y1_low.toString();
        assertTrue(s.contains("CS101"));
        assertTrue(s.contains("Programming"));
        assertTrue(s.contains("FIRST_YEAR"));
    }

    @Test
    void module_compareTo_ordersByYearAscending() {
        assertTrue(y1_low.compareTo(y2_mid) < 0);  // Year1 before Year2
        assertTrue(y2_mid.compareTo(y1_low) > 0);
    }

    @Test
    void module_compareTo_ordersByGradeDescendingWithinSameYear() {
        assertTrue(y1_high.compareTo(y1_low) < 0); // higher grade should come first
        assertTrue(y1_low.compareTo(y1_high) > 0);
    }

    @Test
    void module_equals_isCodePlusYearIdentity() {
        Module dup = new Module("CS101", "Different Name", Year.FIRST_YEAR, 10, 99);
        assertEquals(y1_low, dup);
    }

    // -------------------------
    // Transcript add tests
    // -------------------------

    @Test
    void transcript_addModule_addsSuccessfully() {
        assertTrue(t.addModule(y1_low));
        assertEquals(1, t.getModules().size());
        assertEquals("CS101", t.getModules().get(0).getCode());
    }

    @Test
    void transcript_addModule_rejectsDuplicate() {
        assertTrue(t.addModule(y1_low));
        assertFalse(t.addModule(new Module("CS101", "Programming", Year.FIRST_YEAR, 20, 55)));
        assertEquals(1, t.getModules().size());
    }

    @Test
    void transcript_addModule_maintainsOrderYearAscThenGradeDesc() {
        t.addModule(y1_low);
        t.addModule(y2_mid);
        t.addModule(y1_high);

        List<Module> list = t.getModules();
        assertEquals("CS102", list.get(0).getCode()); // same year, higher grade first
        assertEquals("CS101", list.get(1).getCode());
        assertEquals("CS201", list.get(2).getCode()); // year2 after year1
    }

    // -------------------------
    // Transcript remove tests
    // -------------------------

    @Test
    void transcript_removeModule_removesExisting() {
        t.addModule(y1_low);
        assertTrue(t.removeModule(y1_low));
        assertEquals(0, t.getModules().size());
    }

    @Test
    void transcript_removeModule_returnsFalseWhenNotFound() {
        assertFalse(t.removeModule(y1_low));
    }

    // -------------------------
    // Transcript update tests
    // -------------------------

    @Test
    void transcript_updateGrade_updatesGradeValue() {
        t.addModule(y1_low);
        assertTrue(t.updateGrade("CS101", Year.FIRST_YEAR, 90));
        assertEquals(90, t.getModules().get(0).getGrade());
    }

    @Test
    void transcript_updateGrade_reordersAfterChange() {
        t.addModule(y1_low);
        t.addModule(y1_high);

        assertTrue(t.updateGrade("CS101", Year.FIRST_YEAR, 99));
        assertEquals("CS101", t.getModules().get(0).getCode());
        assertEquals("CS102", t.getModules().get(1).getCode());
    }

    @Test
    void transcript_updateGrade_returnsFalseIfMissing() {
        assertFalse(t.updateGrade("MISSING", Year.FIRST_YEAR, 50));
    }
}
