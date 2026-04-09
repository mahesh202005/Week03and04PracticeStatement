import java.util.*;

class RiskBand {
    String label;
    int threshold;

    RiskBand(String label, int threshold) {
        this.label = label;
        this.threshold = threshold;
    }

    public String toString() {
        return label + ":" + threshold;
    }
}

public class RiskThresholdLookup {

    static int linearSearch(RiskBand[] arr, int target) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].threshold == target) {
                System.out.println("Linear: threshold=" + target + " -> found at index " + i + " (" + comparisons + " comparisons)");
                return i;
            }
        }
        System.out.println("Linear: threshold=" + target + " -> not found (" + comparisons + " comparisons)");
        return -1;
    }

    static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1, comparisons = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] == target) {
                System.out.println("Binary exact: " + target + " -> index " + mid + " (" + comparisons + " comparisons)");
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Binary exact: " + target + " -> not found (" + comparisons + " comparisons)");
        return -1;
    }

    static int insertionPoint(int[] arr, int target) {
        int low = 0, high = arr.length, comparisons = 0;
        while (low < high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        System.out.println("Insertion point for " + target + ": index " + low + " (" + comparisons + " comparisons)");
        return low;
    }

    static int floorValue(int[] arr, int target) {
        int low = 0, high = arr.length - 1, comparisons = 0, result = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] <= target) {
                result = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Floor(" + target + "): " + result + " (" + comparisons + " comparisons)");
        return result;
    }

    static int ceilingValue(int[] arr, int target) {
        int low = 0, high = arr.length - 1, comparisons = 0, result = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] >= target) {
                result = arr[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.println("Ceiling(" + target + "): " + result + " (" + comparisons + " comparisons)");
        return result;
    }

    static int lowerBound(int[] arr, int target) {
        int low = 0, high = arr.length, comparisons = 0;
        while (low < high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        System.out.println("LowerBound(" + target + "): index " + low + " (" + comparisons + " comparisons)");
        return low;
    }

    static int upperBound(int[] arr, int target) {
        int low = 0, high = arr.length, comparisons = 0;
        while (low < high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        System.out.println("UpperBound(" + target + "): index " + low + " (" + comparisons + " comparisons)");
        return low;
    }

    static void assignRiskBand(int[] bands, String[] labels, int clientScore) {
        int idx = upperBound(bands, clientScore) - 1;
        String band = (idx >= 0) ? labels[idx] : "Below minimum";
        System.out.println("Client score " + clientScore + " -> Risk band: " + band);
    }

    public static void main(String[] args) {

        RiskBand[] unsorted = {
            new RiskBand("Medium", 50),
            new RiskBand("Low",    10),
            new RiskBand("High",   100),
            new RiskBand("Medium-Low", 25)
        };

        int[] sorted = {10, 25, 50, 75, 100};
        String[] labels = {"Very Low", "Low", "Medium", "High", "Very High"};

        System.out.println("=== Linear Search (unsorted) ===");
        System.out.println("Bands: " + Arrays.toString(unsorted));
        linearSearch(unsorted, 30);
        linearSearch(unsorted, 50);

        System.out.println("\n=== Binary Search (sorted) ===");
        System.out.println("Bands: " + Arrays.toString(sorted));
        binarySearch(sorted, 50);
        binarySearch(sorted, 30);

        System.out.println("\n=== Insertion Point ===");
        insertionPoint(sorted, 30);
        insertionPoint(sorted, 10);
        insertionPoint(sorted, 110);

        System.out.println("\n=== Floor & Ceiling ===");
        floorValue(sorted, 30);
        ceilingValue(sorted, 30);
        floorValue(sorted, 50);
        ceilingValue(sorted, 50);
        floorValue(sorted, 5);
        ceilingValue(sorted, 105);

        System.out.println("\n=== Lower Bound & Upper Bound ===");
        int[] withDups = {10, 25, 25, 50, 50, 50, 100};
        System.out.println("Array with duplicates: " + Arrays.toString(withDups));
        lowerBound(withDups, 25);
        upperBound(withDups, 25);
        lowerBound(withDups, 50);
        upperBound(withDups, 50);

        System.out.println("\n=== Risk Band Assignment ===");
        assignRiskBand(sorted, labels, 8);
        assignRiskBand(sorted, labels, 30);
        assignRiskBand(sorted, labels, 75);
        assignRiskBand(sorted, labels, 99);
        assignRiskBand(sorted, labels, 100);

        System.out.println("\n=== Complexity Report ===");
        int n = sorted.length;
        System.out.printf("Linear Search  : O(n)     - worst case %d comparisons%n", n);
        System.out.printf("Binary Search  : O(log n) - worst case %d comparisons%n", (int)(Math.log(n) / Math.log(2)) + 1);
        System.out.printf("Floor/Ceiling  : O(log n) - worst case %d comparisons%n", (int)(Math.log(n) / Math.log(2)) + 1);
        System.out.printf("Lower/Upper Bound: O(log n) - worst case %d comparisons%n", (int)(Math.log(n) / Math.log(2)) + 1);
    }
}
