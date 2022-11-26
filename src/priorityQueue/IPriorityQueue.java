package priorityQueue;

public interface IPriorityQueue<E , K extends Comparable<K>> {

    public boolean insert(PriorityQueueNode<E, K> node);
    public void delete(int index);
    public PriorityQueueNode<E, K> maximum();
    public PriorityQueueNode<E, K> minimum();
    public E extractMax();
    public E extractMin();
    public boolean increaseKey(E element, K key);
    public boolean decreaseKey(E element, K key);
}
