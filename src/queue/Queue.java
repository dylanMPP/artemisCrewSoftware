package queue;

import java.util.ArrayList;

public class Queue<V> implements IQueue<V>{

    private ArrayList<QueueNode<V>> queue;
    private QueueNode<V> front;
    private QueueNode<V> back;

    public Queue(){
        queue = new ArrayList<>();
        front = null;
        back = null;
    } // Constructor

    public void delete(int index){

        if(isEmpty()){ // 1
            return; // 1
        } else {
            queue.remove(index);

            for (int i = 0; i < queue.size()-1; i++) { // n - 1

                queue.set(i, queue.get(i+1)); // n - 2
            }
        }
    } // delete -> TOTAL TEMPORAL = 1 + 1 +

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    } // Is empty

    @Override
    public V peek() {
        if(isEmpty()){
            return null;
        } else {
            front = queue.get(0);
            return queue.get(0).getValue();
        }
    } // Peek

    @Override
    public V poll() {
        V value = queue.get(0).getValue();
        queue.remove(0);
        return value;
    } // Poll

    @Override
    public boolean offer(QueueNode<V> item) {
        queue.add(item);
        return true;
    } // Offer -> TOTAL TEMPORAL = n + 4(n-1) + 1 = n + 4n - 4 + 1 = | 5n - 3 |

    public int occupedSize(){

        int size = 0;

        for (int i = 0; i < queue.size(); i++) {

            if(queue.get(i)!=null){
                size++;
            }

            if(queue.get(i)==null){
                break;
            }
        }

        return size;
    } // occuped size

    @Override
    public int size() {
        return queue.size();
    }

    public ArrayList<QueueNode<V>> getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<QueueNode<V>> queue) {
        this.queue = queue;
    }

    public QueueNode<V> getFront() {
        return front;
    }

    public void setFront(QueueNode<V> front) {
        this.front = front;
    }

    public QueueNode<V> getBack() {
        return back;
    }

    public void setBack(QueueNode<V> back) {
        this.back = back;
    }

} // Queue

