package threading.manager;


import threading.types.ThreadPool;
import threading.types.ThreadPriority;
import threading.types.ThreadedJob;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashMap;

public class ThreadManager {
    public static int maxThreadPools = 30;
    private static ThreadManager instance;
    private final int[] priorityList;
    /**
     * TODO HashMap<ThreadedJob, ThreadPool> stores where threadedjobs are being
     * processed. should probably be seperated more by starting letters of the
     * job or something idk or you just assign every threadedjob an id, an index
     * of in what hashmap the threadedjob is !
     */
    private final HashMap<ThreadedJob, ThreadPool>[] assignments;
    private Object[] locks;
    /**
     * int[] contains all pool indexes for a certain object so you can identify,
     * for example, all event threadpools, and get where they are in the
     * assignments array Example for Object would be "{static Object EventPools
     * = new Object()}"
     */
    private HashMap<Object, int[]> poolAssignments;

    @SuppressWarnings("unchecked")
    public ThreadManager() throws InstanceAlreadyExistsException {
        if (instance == null) {
            ThreadManager.instance = this;
        } else {
            throw new InstanceAlreadyExistsException();
        }

        ThreadPriority[] values = ThreadPriority.values();
        priorityList = new int[values.length];

        for (int i = 0; i < priorityList.length; i++) {
            priorityList[i] = values[i].getPriority();
        }
        quickSort(priorityList, 0, priorityList.length);

        assignments = new HashMap[maxThreadPools];

    }

    public void handleEvent(ThreadedJob e) {

    }

    /**
     * just for testing
     *
     * @param index
     * @return
     */
    public HashMap<ThreadedJob, ThreadPool> getAssignment(int index) {
        return assignments[index];
    }

    /**
     * Quicksort algorithm.
     *
     * @param arr
     * @param begin
     * @param end
     */
    private void quickSort(int[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    /**
     * Quicksort algorithm.
     *
     * @param arr
     * @param begin
     * @param end
     * @return
     */
    private int partition(int[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        int swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

}
