import java.util.*;

class TransactionLog {
    String accountId;
    String transactionId;
    double amount;
    String timestamp;

    TransactionLog(String accountId, String transactionId, double amount, String timestamp) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return accountId + "[" + transactionId + ", $" + amount + ", " + timestamp + "]";
    }
}

public class AccountIdLookup {

    // ── Linear Search — first occurrence ─────────────────────────────────────
    static int linearSearchFirst(TransactionLog[] arr, String target) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].accountId.equals(target)) {
                System.out.println("Linear first '" + target + "': index " + i
                        + " (" + comparisons + " comparisons)");
                return i;
            }
        }
        System.out.println("Linear first '" + target + "': not found"
                + " (" + comparisons + " comparisons)");
        return -1;
    }

    // ── Linear Search — last occurrence ──────────────────────────────────────
    static int linearSearchLast(TransactionLog[] arr, String target) {
        int comparisons = 0, lastIdx = -1;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].accountId.equals(target)) lastIdx = i;
        }
        System.out.println("Linear last  '" + target + "': index " + lastIdx
                + " (" + comparisons + " comparisons)");
        return lastIdx;
    }

    // ── Linear Search — count all occurrences ────────────────────────────────
    static int linearSearchCount(TransactionLog[] arr, String target) {
        int comparisons = 0, count = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].accountId.equals(target)) count++;
        }
        System.out.println("Linear count '" + target + "': " + count
                + " occurrences (" + comparisons + " comparisons)");
        return count;
    }

    // ── Binary Search — exact match (any occurrence) ──────────────────────────
    static int binarySearch(TransactionLog[] arr, String target) {
        int low = 0, high = arr.length - 1, comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            int cmp = arr[mid].accountId.compareTo(target);

            if (cmp == 0) {
                System.out.println("Binary search '" + target + "': index " + mid
                        + " (" + comparisons + " comparisons)");
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Binary search '" + target + "': not found"
                + " (" + comparisons + " comparisons)");
        return -1;
    }

    // ── Binary Search — first occurrence ─────────────────────────────────────
    static int binarySearchFirst(TransactionLog[] arr, String target) {
        int low = 0, high = arr.length - 1, comparisons = 0, result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            int cmp = arr[mid].accountId.compareTo(target);

            if (cmp == 0) {
                result = mid;
                high = mid - 1; // keep searching left
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Binary first '" + target + "': index " + result
                + " (" + comparisons + " comparisons)");
        return result;
    }

    // ── Binary Search — last occurrence ──────────────────────────────────────
    static int binarySearchLast(TransactionLog[] arr, String target) {
        int low = 0, high = arr.length - 1, comparisons = 0, result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            int cmp = arr[mid].accountId.compareTo(target);

            if (cmp == 0) {
                result = mid;
                low = mid + 1; // keep searching right
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Binary last  '" + target + "': index " + result
                + " (" + comparisons + " comparisons)");
        return result;
    }

    // ── Binary Search — count occurrences ────────────────────────────────────
    static int binarySearchCount(TransactionLog[] arr, String target) {
        int first = binarySearchFirst(arr, target);
        if (first == -1) { System.out.println("Count '" + target + "': 0"); return 0; }
        int last  = binarySearchLast(arr, target);
        int count = last - first + 1;
        System.out.println("Binary count '" + target + "': " + count + " occurrences"
                + " (first=" + first + ", last=" + last + ")");
        return count;
    }

    // ── Sort by accountId (required before binary search) ────────────────────
    static void sortByAccountId(TransactionLog[] arr) {
        Arrays.sort(arr, Comparator.comparing(t -> t.accountId));
    }

    static void printArray(TransactionLog[] arr) {
        for (int i = 0; i < arr.length; i++)
            System.out.println("  [" + i + "] " + arr[i]);
    }

    // ── Complexity Report ─────────────────────────────────────────────────────
    static void complexityReport(int n) {
        System.out.println("\n=== Time Complexity Report (n=" + n + ") ===");
        System.out.printf("  Linear Search  : O(n)      — worst case %d comparisons%n", n);
        System.out.printf("  Binary Search  : O(log n)  — worst case %d comparisons%n",
                (int)(Math.log(n) / Math.log(2)) + 1);
        System.out.printf("  Sort (pre-req) : O(n log n)— %d ops approx%n",
                (int)(n * Math.log(n) / Math.log(2)));
    }

    // ── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        TransactionLog[] logs = {
            new TransactionLog("accB", "T001", 500.0,  "10:00"),
            new TransactionLog("accA", "T002", 200.0,  "09:30"),
            new TransactionLog("accB", "T003", 750.0,  "10:15"),
            new TransactionLog("accC", "T004", 1200.0, "11:00"),
            new TransactionLog("accA", "T005", 300.0,  "08:45"),
            new TransactionLog("accB", "T006", 450.0,  "12:30"),
            new TransactionLog("accD", "T007", 900.0,  "13:00"),
            new TransactionLog("accA", "T008", 150.0,  "14:00")
        };

        // ── Linear Search (unsorted) ──
        System.out.println("=== Linear Search (unsorted) ===");
        printArray(logs);
        System.out.println();
        linearSearchFirst(logs, "accB");
        linearSearchLast(logs,  "accB");
        linearSearchCount(logs, "accB");
        linearSearchFirst(logs, "accZ");  // not found

        // ── Sort before binary search ──
        System.out.println("\n=== Sorted by accountId ===");
        sortByAccountId(logs);
        printArray(logs);

        // ── Binary Search (sorted) ──
        System.out.println("\n=== Binary Search (sorted) ===");
        binarySearch(logs,      "accB");
        binarySearchFirst(logs, "accB");
        binarySearchLast(logs,  "accB");
        binarySearchCount(logs, "accB");
        binarySearch(logs,      "accZ");  // not found

        // ── Complexity report ──
        complexityReport(logs.length);
    }
}
