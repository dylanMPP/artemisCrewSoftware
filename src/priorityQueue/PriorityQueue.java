package priorityQueue;

import graph.GraphNode;

public class PriorityQueue<E, K extends Comparable<K>> implements IPriorityQueue<E, K> {

    private PriorityQueueNode<E, K>[] priorityQueue;

    public PriorityQueue(int size){
        this.priorityQueue = new PriorityQueueNode[size];
    }

    public PriorityQueueNode<E, K>[] getPriorityQueue() {
        return priorityQueue;
    }

    public void setPriorityQueue(PriorityQueueNode<E, K>[] priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    @Override
    public void delete(int index){

        if(isEmpty()){ // 1
            return; // 1
        } else {
            priorityQueue[index] = null; // 1

            for (int i = index; i < priorityQueue.length-1; i++) { // n-1

                priorityQueue[i] = priorityQueue[i+1]; // n-2
            }
        } // TOTAL TEMPORAL = 1 + (n-1) + (n-2) = 1 + n - 1 + n - 2 = 2n - 2 = | 2 (n-1) |
    } // delete

    public void delete(E element){

        if(isEmpty()){ // 1
            return; // 1
        } else {
            int index = -1;

            for (int i = 0; i < priorityQueue.length-1; i++) { // n-1
                if(priorityQueue[i].equals(element)){
                    priorityQueue[i] = null;// n-2
                    index = i;
                    break;
                }
            }

            for (int i = index; i < priorityQueue.length-1; i++) { // n-1

                priorityQueue[i] = priorityQueue[i+1]; // n-2
            }
        } // TOTAL TEMPORAL = 1 + (n-1) + (n-2) = 1 + n - 1 + n - 2 = 2n - 2 = | 2 (n-1) |
    } // delete

    @Override
    public boolean insert(PriorityQueueNode<E, K> node) {

        for (int i = 0; i < priorityQueue.length; i++) { // n

            if(priorityQueue[i]==null){ // n - 1
                priorityQueue[i] = node; // n - 1
                return true; // n - 1
            }
        }
        return false; // 1
    } // TOTAL TEMPORAL = n + 3(n-1) + 1 = n + 3n - 3 + 1 = 4n - 2 = | 2(n - 1) |

    @Override
    public PriorityQueueNode<E, K> maximum() {
        if(priorityQueue.length>0){
            quickSortMajorMinor(0 );
            return priorityQueue[0];
        } else {
            return null;
        }

    }

    @Override
    public PriorityQueueNode<E, K> minimum() {
        if(priorityQueue.length>0){
            quickSortMinorMajor(0);
            return priorityQueue[0];
        } else {
            return null;
        }
    }

    @Override
    public E extractMax() {
        quickSortMajorMinor(0);

        E element;

        if(isEmpty()){
            return null;
        } else {
            int occupied = occupedSize();
            element = priorityQueue[0].getElement();
            priorityQueue[0] = null;

            for (int i = 0; i <= occupied-1; i++) {
                if(i==occupied-1){
                    priorityQueue[i] = null;
                } else {
                    priorityQueue[i] = priorityQueue[i+1];
                }
            }
        }
        return element;
    }

    @Override
    public E extractMin() {
        quickSortMinorMajor(0);

        E element;

        if(isEmpty()){
            return null;
        } else {
            int occupied = occupedSize();
            element = priorityQueue[0].getElement();
            priorityQueue[0] = null;

            for (int i = 0; i < occupied-1; i++) {
                if(i==occupied-1){
                    priorityQueue[i] = null;
                } else {
                    priorityQueue[i] = priorityQueue[i+1];
                }
            }
        }
        return element;
    }

    public void quickSortMajorMinor(int inicio){
        int fin = 0;

        for (int i = 0; i < priorityQueue.length; i++) {
            if(priorityQueue[i]==null){
                fin = i-1;
                break;
            } else if(i==priorityQueue.length-1){
                fin = priorityQueue.length-1;
            }
        }

        quickSortMajorMinor(inicio, fin);
    }

    public void quickSortMajorMinor(int inicio, int fin) {

        if (inicio >= fin) return;
        PriorityQueueNode<E,K> pivote = priorityQueue[inicio];
        int elemIzq = inicio + 1;
        int elemDer = fin;

        while (elemIzq <= elemDer) {
            while (elemIzq <= fin && (priorityQueue[elemIzq].compareTo(pivote.getKey())) >= 0)  {
                elemIzq++;
            }
            while (elemDer > inicio && (priorityQueue[elemDer].compareTo(pivote.getKey())) < 0) {
                elemDer--;
            }
            if (elemIzq < elemDer) {
                PriorityQueueNode<E, K> temp = priorityQueue[elemIzq];
                priorityQueue[elemIzq] = priorityQueue[elemDer];
                priorityQueue[elemDer] = temp;
            }
        }

        if (elemDer > inicio) {
            PriorityQueueNode<E, K> temp = priorityQueue[inicio];
            priorityQueue[inicio] = priorityQueue[elemDer];
            priorityQueue[elemDer] = temp;
        }
        quickSortMajorMinor(inicio, elemDer - 1);
        quickSortMajorMinor(elemDer + 1, fin);
    } // quicksort for priority queue (major to minor)


    public void quickSortMinorMajor(int inicio){
        int fin = 0;

        for (int i = 0; i < priorityQueue.length; i++) {
            if(priorityQueue[i]==null){
                fin = i-1;
                break;
            } else if(i==priorityQueue.length-1){
                fin = priorityQueue.length-1;
            }
        }

        quickSortMinorMajor(inicio, fin);
    }

    public void quickSortMinorMajor(int inicio, int fin) {

        System.out.println(inicio);
        if (inicio >= fin) return;

        PriorityQueueNode<E,K> pivote = priorityQueue[inicio];
        int elemIzq = inicio + 1;
        int elemDer = fin;


        while (elemIzq <= elemDer) {
            while (elemIzq <= fin && (priorityQueue[elemIzq].compareTo(pivote.getKey())<0) ) {
                elemIzq++;
            }
            while (elemDer > inicio && (priorityQueue[elemDer].compareTo(pivote.getKey()) >= 0) ) {
                elemDer--;
            }
            if (elemIzq < elemDer) {
                PriorityQueueNode<E, K> temp = priorityQueue[elemIzq];
                priorityQueue[elemIzq] = priorityQueue[elemDer];
                priorityQueue[elemDer] = temp;
            }
        }

        if (elemDer > inicio) {
            PriorityQueueNode<E, K> temp = priorityQueue[inicio];
            priorityQueue[inicio] = priorityQueue[elemDer];
            priorityQueue[elemDer] = temp;
        }
        quickSortMinorMajor(inicio, elemDer - 1);
        quickSortMinorMajor(elemDer + 1, fin);
    } // quicksort for priority queue (minor to major)

    @Override
    public boolean increaseKey(E element, K key) {

        for (int i = 0; i < priorityQueue.length; i++) {

            if(priorityQueue[i].getElement().equals(element)){

                if(priorityQueue[i].getKey().compareTo(key) >= 0){
                    return false;
                } else {
                    priorityQueue[i].setKey(key);
                    return true;
                }
            }
        }

        return false;
    } // increase key

    @Override
    public boolean decreaseKey(E element, K key) {

        for (int i = 0; i < priorityQueue.length; i++) {

            if(priorityQueue[i].getElement().equals(element)){

                if(priorityQueue[i].getKey().compareTo(key) <= 0){
                    return false;
                } else {
                    priorityQueue[i].setKey(key);
                    return true;
                }
            }
        }

        return false;
    } // decrease key

    public boolean isEmpty() {

        if(priorityQueue.length==0){
            return true;
        }

        if(priorityQueue[0]==null){
            return true;
        } else {
            return false;
        }
    } // Is empty

    public int occupedSize(){

        int size = 0;

        for (int i = 0; i < priorityQueue.length; i++) {

            if(priorityQueue[i]!=null){
                size++;
            }

            if(priorityQueue[i]==null){
                break;
            }
        }

        return size;
    } // occuped size
}

