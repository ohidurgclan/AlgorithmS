public class QuickSort {
    public static void main(String[] args) {
        int[] array = {15, 3, 9, 17, 8, 20, 5, 7, 2};

        quickSort(array, 0, array.length - 1);
        System.out.println("\nSorted array(Quick):");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println("\n\n");
    }
    // Coded By Md Ohidur Rahman - ID: 221002406
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    // Partition function
    public static int partition(int[] array, int low, int high) {
        int pivot = array[high]; 
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                // Swap array[i] and array[j]
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        // Swap array
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }
}