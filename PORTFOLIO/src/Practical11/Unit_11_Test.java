package Practical11;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Unit_11_Test {

    @Test
    void testConcertComparableOrdersByTime() {
        Concert c1 = new Concert("C1", List.of("A"), LocalDateTime.of(2026,1,1,18,0));
        Concert c2 = new Concert("C2", List.of("B"), LocalDateTime.of(2026,1,1,20,0));
        assertTrue(c1.compareTo(c2) < 0);
    }

    @Test
    void testScheduleConcertSortedOrder() {
        BookingSystem bs = new BookingSystem(1, "London");

        Concert late = new Concert("C2", List.of("B"), LocalDateTime.of(2026, 1, 1, 20, 0));
        Concert early = new Concert("C1", List.of("A"), LocalDateTime.of(2026, 1, 1, 18, 0));
        Concert mid = new Concert("C3", List.of("C"), LocalDateTime.of(2026, 1, 1, 19, 0));

        assertTrue(bs.ScheduleConcert(late));
        assertTrue(bs.ScheduleConcert(early));
        assertTrue(bs.ScheduleConcert(mid));

        assertEquals("C1", bs.GetConcert(0).getConcertId());
        assertEquals("C3", bs.GetConcert(1).getConcertId());
        assertEquals("C2", bs.GetConcert(2).getConcertId());
    }

    @Test
    void testScheduleConcertRejectsDuplicate() {
        BookingSystem bs = new BookingSystem(1, "London");

        Concert c1 = new Concert("C1", List.of("A"), LocalDateTime.of(2026, 1, 1, 18, 0));
        Concert dup = new Concert("C1", List.of("B"), LocalDateTime.of(2026, 1, 2, 18, 0));

        assertTrue(bs.ScheduleConcert(c1));
        assertFalse(bs.ScheduleConcert(dup));
        assertEquals(1, bs.getCount());
    }

    @Test
    void testCancelConcertRemovesNoGaps() {
        BookingSystem bs = new BookingSystem(1, "London");

        Concert c1 = new Concert("C1", List.of("A"), LocalDateTime.of(2026, 1, 1, 18, 0));
        Concert c2 = new Concert("C2", List.of("B"), LocalDateTime.of(2026, 1, 1, 19, 0));
        Concert c3 = new Concert("C3", List.of("C"), LocalDateTime.of(2026, 1, 1, 20, 0));

        bs.ScheduleConcert(c1);
        bs.ScheduleConcert(c2);
        bs.ScheduleConcert(c3);

        assertTrue(bs.CancelConcert(c2));
        assertEquals(2, bs.getCount());

        // Should now be C1, C3 (no null gap)
        assertEquals("C1", bs.GetConcert(0).getConcertId());
        assertEquals("C3", bs.GetConcert(1).getConcertId());
        assertNull(bs.GetConcert(2));
    }

    @Test
    void testToStringDoesNotPrintNulls() {
        BookingSystem bs = new BookingSystem(1, "London");
        bs.ScheduleConcert(new Concert("C1", List.of("A"), LocalDateTime.of(2026,1,1,18,0)));

        String text = bs.toString();
        assertTrue(text.contains("C1"));
        assertFalse(text.contains("null"));
    }
}
