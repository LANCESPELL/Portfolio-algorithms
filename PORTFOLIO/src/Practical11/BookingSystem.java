package Practical11;

/**
 * BookingSystem using arrays (no ArrayList).
 * Stores venue id, location, backing array and count.
 */
public class BookingSystem {

    // basic information
    private int venueId;
    private String location;

    // backing array (default capacity 7) + current size
    private Concert[] concertBackingField = new Concert[7];
    private int count = 0;

    public BookingSystem(int venueId, String location) {
        this.venueId = venueId;
        this.location = location;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCount() {
        return count;
    }

    /**
     * Schedule a concert:
     * - must be unique
     * - must stay sorted by time (ascending)
     *
     * Time Complexity:
     * - Contains check: O(n)
     * - Find insert index: O(n)
     * - Shift right: O(n)
     * Worst case overall: O(n)
     *
     * Sub-optimal part: shifting on insert makes it O(n).
     */
    public boolean ScheduleConcert(Concert concertToAdd) {
        if (concertToAdd == null) return false;
        if (Contains(concertToAdd)) return false;

        // resize if full (copy is O(n))
        if (count == concertBackingField.length) {
            Concert[] newConcerts = new Concert[concertBackingField.length + 7];
            System.arraycopy(concertBackingField, 0, newConcerts, 0, count);
            concertBackingField = newConcerts;
        }

        // find insert index to keep ascending order (O(n))
        int insertIndex = 0;
        while (insertIndex < count &&
                concertBackingField[insertIndex].compareTo(concertToAdd) <= 0) {
            insertIndex++;
        }

        // shift right (O(n))
        for (int i = count; i > insertIndex; i--) {
            concertBackingField[i] = concertBackingField[i - 1];
        }

        concertBackingField[insertIndex] = concertToAdd;
        count++;
        return true;
    }

    /**
     * Cancel a concert (remove and shift left so there are no gaps).
     *
     * Time Complexity:
     * - Search: O(n)
     * - Shift left: O(n)
     * Worst case overall: O(n)
     *
     * Sub-optimal part: shifting after removal makes it O(n).
     */
    public boolean CancelConcert(Concert concertToCancel) {
        if (concertToCancel == null) return false;

        // find index (O(n))
        int index = -1;
        for (int i = 0; i < count; i++) {
            if (concertBackingField[i].equals(concertToCancel)) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;

        // shift left to remove gap (O(n))
        for (int i = index; i < count - 1; i++) {
            concertBackingField[i] = concertBackingField[i + 1];
        }

        concertBackingField[count - 1] = null;
        count--;
        return true;
    }

    /**
     * Indexed access (0..count-1).
     * Time Complexity: O(1)
     */
    public Concert GetConcert(int index) {
        if (index < 0 || index >= count) return null;
        return concertBackingField[index];
    }

    /**
     * Check if concert already exists (unique by concertId via equals).
     * Time Complexity: O(n)
     */
    private boolean Contains(Concert containsConcert) {
        for (int i = 0; i < count; i++) {
            if (concertBackingField[i].equals(containsConcert)) return true;
        }
        return false;
    }

    /**
     * toString ignores empty (null) array elements.
     * Time Complexity: O(n) over scheduled concerts (count).
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Venue: ").append(venueId)
                .append(" Location: ").append(location)
                .append("\nConcerts:\n");

        for (int i = 0; i < count; i++) {
            str.append(concertBackingField[i]).append("\n");
        }
        return str.toString();
    }
}
