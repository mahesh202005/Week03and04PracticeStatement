import java.util.ArrayList;
import java.util.List;

class Transaction {
    String id;
    double fee;
    String timestamp;

    Transaction(String id, double fee, String timestamp) {
        this.id        = id;
        this.fee       = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s:$%.1f@%s", id, fee, timestamp);
    }
}

public class FeeSortingSystem {

    private static final double HIGH_FEE_THRESHOLD = 50.0;

    static SortResult bubbleSort(List<Transaction> txns) {
        List<Transaction> list = new ArrayList<>(txns);
        int n      = list.size();
        int passes = 0;
        int swaps  = 0;

        for (int i = 0; i < n - 1; i++) {
            passes++;
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Transaction tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                    swaps++;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }

        return new SortResult(list, passes, swaps);
    }

    static SortResult insertionSort(List<Transaction> txns) {
        List<Transaction> list = new ArrayList<>(txns);
        int n      = list.size();
        int passes = 0;
        int shifts = 0;

        for (int i = 1; i < n; i++) {
            passes++;
            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 && compareFeeTimestamp(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
                shifts++;
            }
            list.set(j + 1, key);
        }

        return new SortResult(list, passes, shifts);
    }

    private static int compareFeeTimestamp(Transaction a, Transaction b) {
        int feeCmp = Double.compare(a.fee, b.fee);
        if (feeCmp != 0) return feeCmp;
        return a.timestamp.compareTo(b.timestamp);
    }

    static List<Transaction> flagOutliers(List<Transaction> sorted) {
        List<Transaction> outliers = new ArrayList<>();
        for (Transaction t : sorted) {
            if (t.fee > HIGH_FEE_THRESHOLD) outliers.add(t);
        }
        return outliers;
    }

    static void processTransactions(List<Transaction> txns) {
        int n = txns.size();
        System.out.println("══════════════════════════════════════════");
        System.out.printf( "  Batch size: %d transaction(s)%n", n);
        System.out.println("══════════════════════════════════════════");

        SortResult result;

        if (n <= 100) {
            System.out.println("  Algorithm  : Bubble Sort (fee asc)");
            result = bubbleSort(txns);
            System.out.printf("  Passes     : %d  |  Swaps  : %d%n",
                              result.operationCount1, result.operationCount2);
        } else if (n <= 1000) {
            System.out.println("  Algorithm  : Insertion Sort (fee + timestamp)");
            result = insertionSort(txns);
            System.out.printf("  Passes     : %d  |  Shifts : %d%n",
                              result.operationCount1, result.operationCount2);
        } else {
            System.out.println("  Batch exceeds 1,000 – use a production-grade O(n log n) sort.");
            return;
        }

        System.out.println("\n  Sorted result:");
        for (Transaction t : result.sorted) {
            System.out.println("    " + t);
        }

        List<Transaction> outliers = flagOutliers(result.sorted);
        System.out.println("\n  High-fee outliers (> $" + HIGH_FEE_THRESHOLD + "):");
        if (outliers.isEmpty()) {
            System.out.println("    none");
        } else {
            for (Transaction t : outliers) {
                System.out.printf("    ⚑  %s  <- fee $%.2f exceeds threshold%n", t.id, t.fee);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {

        List<Transaction> sample = new ArrayList<>();
        sample.add(new Transaction("id1", 10.5, "10:00"));
        sample.add(new Transaction("id2", 25.0, "09:30"));
        sample.add(new Transaction("id3",  5.0, "10:15"));

        System.out.println("\n=== Scenario 1 – Problem-statement sample ===");
        processTransactions(sample);

        List<Transaction> withDuplicates = new ArrayList<>();
        withDuplicates.add(new Transaction("txA", 15.0, "08:00"));
        withDuplicates.add(new Transaction("txB", 75.0, "07:30"));
        withDuplicates.add(new Transaction("txC", 15.0, "08:30"));
        withDuplicates.add(new Transaction("txD",  3.0, "09:00"));
        withDuplicates.add(new Transaction("txE", 60.0, "06:00"));

        System.out.println("=== Scenario 2 – Duplicates + outliers (Bubble Sort) ===");
        processTransactions(withDuplicates);

        System.out.println("=== Scenario 3 – Medium batch (Insertion Sort demo) ===");
        List<Transaction> medium = new ArrayList<>();
        medium.add(new Transaction("m001", 12.0, "09:15"));
        medium.add(new Transaction("m002", 55.5, "08:00"));
        medium.add(new Transaction("m003",  7.5, "10:00"));
        medium.add(new Transaction("m004", 12.0, "09:00"));
        medium.add(new Transaction("m005", 33.0, "11:00"));
        for (int i = 6; i <= 101; i++) {
            medium.add(new Transaction("m" + String.format("%03d", i),
                                       Math.round(Math.random() * 4000) / 100.0,
                                       "12:00"));
        }
        processTransactions(medium);
    }
}

class SortResult {
    List<Transaction> sorted;
    int operationCount1;
    int operationCount2;

    SortResult(List<Transaction> sorted, int op1, int op2) {
        this.sorted          = sorted;
        this.operationCount1 = op1;
        this.operationCount2 = op2;
    }
}
