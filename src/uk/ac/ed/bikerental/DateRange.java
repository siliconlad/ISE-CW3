package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class DateRange {
    private LocalDate start, end;

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }

    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }

    /**
     * Compares a date range with the date range in this object to see if the
     * two date ranges overlap.
     *
     * @param other The other <code>DateRange</code> object to compare this
     *              <code>DateRange</code> object to.
     * @return      <code>true</code> if the two <code>DateRange</code> objects
     *              overlap. <code>false</code> otherwise
     */
    public Boolean overlaps(DateRange other) {
        LocalDate otherStart = other.getEnd();
        LocalDate otherEnd = other.getStart();

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
