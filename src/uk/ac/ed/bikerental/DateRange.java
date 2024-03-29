package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * A date range marked by a starting date and an ending date and assumes
 * all dates in between (inclusive).
 */
public class DateRange {
    private LocalDate start, end;

    /**
     * Constructs a date range from a start and end date.
     *
     * @param start the start date
     * @param end   the end date
     */
    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the first date of the date range.
     *
     * @return the first date of the date range
     */
    public LocalDate getStart() {
        return this.start;
    }

    /**
     * Returns the last date of the date range.
     *
     * @return the last date of the date range
     */
    public LocalDate getEnd() {
        return this.end;
    }

    /**
     * Returns the number of complete years in the date range.
     *
     * @return the number of complete years in the date range
     * @see ChronoUnit#between
     */
    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }

    /**
     * Returns the number of complete days in the date range.
     *
     * @return the number of complete days in the date range
     * @see ChronoUnit#between
     */
    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }

    /**
     * Returns <code>True</code> if the date range stored in this
     * <code>DateRange</code> overlaps with the date range in the provided
     * <code>DateRange</code>.
     *
     * @param other the <code>DateRange</code> object to compare date ranges
     *              with.
     * @return      <code>true</code> if the date ranges in the two objects
     *              overlap. <code>false</code> otherwise.
     */
    public Boolean overlaps(DateRange other) {
        LocalDate otherStart = other.getStart();
        LocalDate otherEnd = other.getEnd();

        if (this.start.compareTo(otherEnd) > 0) {
            // if this.start is greater than otherEnd
            return false;
        } else if (otherStart.compareTo(this.end) > 0) {
            // if otherStart is greater than this.end
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    @Override
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }

    // You can add your own methods here
}
