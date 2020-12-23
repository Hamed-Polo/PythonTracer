public class Complexity {
    /**
     * These two variables will keep track of what power each of the base types
     * is present in the complexity object.
     */
    int nPower;
    int logPower;

    /**
     * default constructor method for Complexity
     */
    public Complexity() {

    }

    /**
     * We're setting nPower to the given argument.
     * @param nPower - argument we'll use
     */
    public void setNPower(int nPower) {
        this.nPower = nPower;
    }

    /**
     * @return nPower
     */
    public int getNPower() {
        return nPower;
    }

    /**
     * We're setting logPower to the given argument
     * @param logPower - argument we'll use
     */
    public void setLogPower(int logPower) {
        this.logPower = logPower;
    }

    /**
     * @return logPower
     */
    public int getLogPower() {
        return logPower;
    }

    /**
     * Depending on different conditions, it'll return a readable Big-Oh
     * notation of the object.
     * @return a human-readable Big-Oh notation. (e.g O(n^4 * log(n)^2).
     */
    public String toString() {
        if (nPower == 0 && logPower == 0) {
            return "O(1)";
        }
        else if (nPower == 1 && logPower == 1) {
            return "O(n * log(n))";
        }
        else if (nPower == 1) {
            return "O(n)";
        }
        else if (logPower == 1) {
            return "O(log(n))";
        }
        else if (nPower != 0 & logPower == 0) {
            return "O(n^" + nPower + ")";
        }
        else {
            return "O(n^" + nPower + " * log(n)^" + logPower + ")";
        }
    }
}