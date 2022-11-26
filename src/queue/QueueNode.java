package queue;

public class QueueNode<V>{

    private V value;

    public QueueNode(V value){
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

