import java.util.Arrays;

class Trade {
    String id;
    int volume;

    Trade(String id, int volume) {
        this.id     = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return String.format("%s:%d", id, volume);
    }
}

public class HistoricalTradeVolumeAnalysis {

    static Trade[] mergeSort(Trade[] arr) {
        if (arr.length <= 1) return arr;

        int mid = arr.length / 2;
        Trade[] left  = mergeSort(Arrays.copyOfRange(arr, 0, mid));
        Trade[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length));
        return merge(left, right);
    }

    private static Trade[] merge(Trade[] left, Trade[] right) {
        Trade[] result = new Trade[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].volume <= right[j].volume) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length)  result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];

        return result;
    }

    static void quickSortDesc(Trade[] arr, int low, int high) {
        if (low < high) {
            int pivot = lomutoPartitionDesc(arr, low, high);
            quickSortDesc(arr, low, pivot - 1);
            quickSortDesc(arr, pivot + 1, high);
        }
    }

    private static int lomutoPartitionDesc(Trade[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        if (arr[mid].volume > arr[low].volume)  swap(arr, mid, low);
        if (arr[high].volume > arr[low].volume) swap(arr, high, low);
        if (arr[mid].volume > arr[high].volume) swap(arr, mid, high);
        Trade pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].volume >= pivot.volume) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(Trade[] arr, int a, int b) {
        Trade tmp = arr[a];
        arr[a]    = arr[b];
        arr[b]    = tmp;
    }

    static Trade[] mergeSessions(Trade[] morning, Trade[] afternoon) {
        Trade[] sortedM = mergeSort(morning);
        Trade[] sortedA = mergeSort(afternoon);
        return merge(sortedM, sortedA);
    }

    static long totalVolume(Trade[] arr) {
        long sum = 0;
        for (Trade t : arr) sum += t.volume;
        return sum;
    }

    static void printTrades(Trade[] arr) {
        System.out.print("  [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    static void run(String label, Trade[] trades) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  " + label);
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println("\n  Input:");
        printTrades(trades);

        Trade[] merged = mergeSort(trades);
        System.out.println("\n  Merge Sort (volume asc, stable):");
        printTrades(merged);

        Trade[] quick = Arrays.copyOf(trades, trades.length);
        quickSortDesc(quick, 0, quick.length - 1);
        System.out.println("\n  Quick Sort (volume desc, median pivot):");
        printTrades(quick);

        System.out.printf("%n  Total volume: %,d%n", totalVolume(merged));
    }

    public static void main(String[] args) {

        Trade[] sample = {
            new Trade("trade3", 500),
            new Trade("trade1", 100),
            new Trade("trade2", 300)
        };
        run("Scenario 1 – Sample (3 trades)", sample);

        Trade[] morning = {
            new Trade("AM-1",  1200),
            new Trade("AM-2",  4500),
            new Trade("AM-3",   800),
            new Trade("AM-4",  3100),
            new Trade("AM-5",  2200)
        };
        Trade[] afternoon = {
            new Trade("PM-1",  1750),
            new Trade("PM-2",  5300),
            new Trade("PM-3",   950),
            new Trade("PM-4",  4100)
        };

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  Scenario 2 – Morning + Afternoon Merge");
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println("\n  Morning session:");
        printTrades(morning);
        System.out.println("  Afternoon session:");
        printTrades(afternoon);

        Trade[] combined = mergeSessions(morning, afternoon);
        System.out.println("\n  Merged (volume asc):");
        printTrades(combined);
        System.out.printf("%n  Total combined volume: %,d%n", totalVolume(combined));

        Trade[] batch = {
            new Trade("T01", 9200), new Trade("T02", 1500), new Trade("T03", 7800),
            new Trade("T04", 3300), new Trade("T05", 6100), new Trade("T06",  420),
            new Trade("T07", 8800), new Trade("T08", 2700), new Trade("T09", 5500),
            new Trade("T10", 4900), new Trade("T11",  310), new Trade("T12", 6700)
        };

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  Scenario 3 – 12-trade desk batch");
        System.out.println("╚══════════════════════════════════════════════╝");

        Trade[] mSorted = mergeSort(batch);
        System.out.println("\n  Merge Sort (volume asc):");
        printTrades(mSorted);

        Trade[] qSorted = Arrays.copyOf(batch, batch.length);
        quickSortDesc(qSorted, 0, qSorted.length - 1);
        System.out.println("\n  Quick Sort (volume desc):");
        printTrades(qSorted);

        System.out.printf("%n  Total volume: %,d%n", totalVolume(mSorted));
    }
}
