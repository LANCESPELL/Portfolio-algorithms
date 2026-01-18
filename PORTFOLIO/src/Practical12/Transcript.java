package Practical12;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Transcript {

    private final int id;
    private final LinkedList<Module> modules = new LinkedList<>();

    public Transcript(int id) { this.id = id; }

    public List<Module> getModules() { return new LinkedList<>(modules); }

    /**
     * Add module while keeping ordering (year asc, grade desc).
     *
     * Time complexity:
     * - contains: O(n) (LinkedList linear scan)
     * - insertion search: O(n)
     * => O(n) overall
     *
     * Sub-optimal: contains + insertion scan = two passes.
     * Improvement: single pass that checks duplicates and finds position together.
     */
    public boolean addModule(Module m) {
        if (modules.contains(m)) { print("ADD FAILED (duplicate)"); return false; }

        ListIterator<Module> it = modules.listIterator();
        while (it.hasNext()) {
            if (m.compareTo(it.next()) < 0) {
                it.previous();
                it.add(m);
                print("ADDED");
                return true;
            }
        }
        modules.add(m);
        print("ADDED");
        return true;
    }

    /**
     * Remove module.
     * Time complexity: O(n) for LinkedList.remove(Object)
     */
    public boolean removeModule(Module m) {
        boolean ok = modules.remove(m);
        print(ok ? "REMOVED" : "REMOVE FAILED");
        return ok;
    }

    /**
     * Update a module grade and reorder list.
     *
     * Time complexity:
     * - find: O(n)
     * - remove: O(n)
     * - reinsert: O(n)
     * => O(n)
     */
    public boolean updateGrade(String code, Year year, int newGrade) {
        Module found = null;
        for (Module m : modules) {
            if (m.getYear() == year && m.getCode().equalsIgnoreCase(code)) {
                found = m; break;
            }
        }
        if (found == null) { print("UPDATE FAILED"); return false; }

        modules.remove(found);
        found.setGrade(newGrade);
        addModule(found); // prints
        return true;
    }

    // Prints transcript + average per year after each change.
    private void print(String action) {
        System.out.println("\n=== Transcript " + id + " | " + action + " ===");
        for (Module m : modules) System.out.println(m);
        printAverages();
    }

    /**
     * Average per year.
     * Time complexity: O(k*n) where k=4 years => O(n).
     * Sub-optimal if called often; improvement: keep running totals per year.
     */
    private void printAverages() {
        for (Year y : Year.values()) {
            int total = 0, count = 0;
            for (Module m : modules) {
                if (m.getYear() == y) { total += m.getGrade(); count++; }
            }
            if (count > 0) {
                System.out.printf("%s average: %.2f%n", y, (double) total / count);
            }
        }
    }
}
