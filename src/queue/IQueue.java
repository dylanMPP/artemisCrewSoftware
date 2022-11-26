package queue;

public interface IQueue <V>{

    public boolean isEmpty();
    public V peek();
    public V poll();
    public boolean offer(QueueNode<V> item);
    public int occupedSize();
    public int size();
}
