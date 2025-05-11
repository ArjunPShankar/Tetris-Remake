public class MyArrayList<E>{
    private Object[] myArrayList;
    private int size;
    public static int DEFAULT_MEMORY = 1000;

    public MyArrayList(){
        myArrayList = new Object[DEFAULT_MEMORY];
    }

    public boolean add(E item){
        myArrayList[size] = item;
        size++;
        return true;
    }

    public void add(int index, E item){
        for (int i = size-1; i > index-1; i--){
            myArrayList[i+1] = myArrayList[i];
        }
        myArrayList[index] = item;
        size++;
    }

    public E get(int index){
        return (E) myArrayList[index];
    }

    public E remove(int index){
        E removedThing = (E)myArrayList[index];
        myArrayList[index] = null;

        for (int i = index; i < size; i++){
            myArrayList[i] = myArrayList[i+1];
        }
        size--;
        return removedThing;
    }

    public E remove(E removedThing){
        boolean removed = false;
        for (int i = 0; i < size; i++){
            if (myArrayList[i].equals(removedThing)){
                myArrayList[i] = null;
                removed = true;
            }
            if (removed){
                myArrayList[i] = myArrayList[i+1];
            }
        }
        size--;
        
        return removedThing;
    }

    public void set(int index, E setThing){
        myArrayList[index] = setThing;
    }

    public String toString(){
        String output = "";

        for (int i = 0; i < size;i++){
            output += myArrayList[i] + "\n";
        }

        return output;
    }

    public int size(){
        return size;
    }
}