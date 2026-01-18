package Practical11;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class used to represent concerts in the booking system
 * Stores concert id, artists and time (ISO 8601 via LocalDateTime).
 * Allows total ordering by time via Comparable.
 */
public class Concert implements Comparable<Concert> {

    private String concertId;
    private List<String> artists;
    private LocalDateTime time;

    public Concert(String concertId, List<String> artists, LocalDateTime time) {
        this.concertId = concertId;
        this.artists = artists;
        this.time = time;
    }

    public String getConcertId() {
        return concertId;
    }

    public List<String> getArtists() {
        return artists;
    }

    public String getArtists(int index) {
        if (index >= 0 && index < artists.size()) return artists.get(index);
        return null;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public void setConcertId(String concertId) {
        this.concertId = concertId;
    }

    /**
     * Total ordering by concert time.
     * Time Complexity: O(1)
     */
    @Override
    public int compareTo(Concert other) {
        return this.time.compareTo(other.time);
    }

    @Override
    public String toString() {
        return "Concert Id: " + concertId + ", Artists: " + artists + ", Time: " + time;
    }

    /**
     * Equality used for "unique concerts" in BookingSystem.
     * We'll treat concerts as unique by concertId.
     * Time Complexity: O(1)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Concert)) return false;
        Concert other = (Concert) obj;
        return this.concertId.equals(other.concertId);
    }
}
