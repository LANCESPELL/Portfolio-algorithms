package Practical12;

import java.util.Objects;

public class Module implements Comparable<Module> {

    private final String code, name;
    private final Year year;
    private final int credits;
    private int grade;

    public Module(String code, String name, Year year, int credits, int grade) {
        this.code = code;
        this.name = name;
        this.year = year;
        this.credits = credits;
        this.grade = grade;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public Year getYear() { return year; }
    public int getCredits() { return credits; }
    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %d credits | %d%%",
                code, name, year, credits, grade);
    }

    // Year asc; same year grade desc
    @Override
    public int compareTo(Module o) {
        int yc = this.year.compareTo(o.year);
        if (yc != 0) return yc;
        return Integer.compare(o.grade, this.grade);
    }

    // identity used for duplicate detection: module code + year
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Module)) return false;
        Module other = (Module) obj;
        return year == other.year && code.equalsIgnoreCase(other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code.toLowerCase(), year);
    }
}
