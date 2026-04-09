import java.util.Arrays;

class Client {
    String name;
    int riskScore;
    double accountBalance;

    Client(String name, int riskScore, double accountBalance) {
        this.name           = name;
        this.riskScore      = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return String.format("%s(risk=%d, bal=$%.0f)", name, riskScore, accountBalance);
    }
}

class SortStats {
    Client[] sorted;
    int passes;
    int swaps;

    SortStats(Client[] sorted, int passes, int swaps) {
        this.sorted = sorted;
        this.passes = passes;
        this.swaps  = swaps;
    }
}

public class ClientRiskRanking {

    static SortStats bubbleSortAsc(Client[] input) {
        Client[] arr = Arrays.copyOf(input, input.length);
        int n      = arr.length;
        int passes = 0;
        int swaps  = 0;

        for (int i = 0; i < n - 1; i++) {
            passes++;
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    Client tmp  = arr[j];
                    arr[j]      = arr[j + 1];
                    arr[j + 1]  = tmp;
                    swaps++;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }

        return new SortStats(arr, passes, swaps);
    }

    static SortStats insertionSortDesc(Client[] input) {
        Client[] arr = Arrays.copyOf(input, input.length);
        int n      = arr.length;
        int passes = 0;
        int shifts = 0;

        for (int i = 1; i < n; i++) {
            passes++;
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 && compareDescBalAsc(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
                shifts++;
            }
            arr[j + 1] = key;
        }

        return new SortStats(arr, passes, shifts);
    }

    private static int compareDescBalAsc(Client a, Client b) {
        int cmp = Integer.compare(b.riskScore, a.riskScore);
        if (cmp != 0) return cmp;
        return Double.compare(b.accountBalance, a.accountBalance);
    }

    static void printTopN(Client[] sorted, int n) {
        System.out.printf("  Top %d highest-risk clients:%n", n);
        for (int i = 0; i < Math.min(n, sorted.length); i++) {
            System.out.printf("    %2d. %s%n", i + 1, sorted[i]);
        }
    }

    static void run(String label, Client[] clients, int topN) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  " + label);
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println("\n── Bubble Sort (risk asc) ──────────────────────");
        SortStats bubble = bubbleSortAsc(clients);
        System.out.printf("  Passes: %d  |  Swaps: %d%n", bubble.passes, bubble.swaps);
        System.out.print("  Result: ");
        for (Client c : bubble.sorted) System.out.print(c.name + ":" + c.riskScore + "  ");
        System.out.println();

        System.out.println("\n── Insertion Sort (risk desc + balance desc) ───");
        SortStats insertion = insertionSortDesc(clients);
        System.out.printf("  Passes: %d  |  Shifts: %d%n", insertion.passes, insertion.swaps);
        System.out.print("  Result: ");
        for (Client c : insertion.sorted) System.out.print(c.name + ":" + c.riskScore + "  ");
        System.out.println();

        System.out.println();
        printTopN(insertion.sorted, topN);
    }

    public static void main(String[] args) {

        Client[] sample = {
            new Client("clientC", 80, 12000),
            new Client("clientA", 20,  5000),
            new Client("clientB", 50,  8500)
        };
        run("Scenario 1 – Sample (3 clients)", sample, 3);

        Client[] batch = {
            new Client("Alice",   72, 45000),
            new Client("Bob",     91, 12000),
            new Client("Carol",   55, 78000),
            new Client("David",   91, 30000),
            new Client("Eva",     38,  9500),
            new Client("Frank",   67, 22000),
            new Client("Grace",   85, 61000),
            new Client("Hank",    20,  3200),
            new Client("Irene",   77, 41000),
            new Client("Jack",    95,  8000),
            new Client("Karen",   44, 15500),
            new Client("Leo",     88, 52000),
            new Client("Mia",     63, 27000),
            new Client("Nate",    79, 19000),
            new Client("Olivia",  33, 11000)
        };
        run("Scenario 2 – 15-client batch (top 10 review)", batch, 10);
    }
}
