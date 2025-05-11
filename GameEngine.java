import java.util.ArrayList;
import java.util.TreeSet;
public class GameEngine implements Runnable{
    private final Object lock = new Object();

    private ArrayList<char[]> board; // y, x
    private ArrayList<Piece> queue;
    private boolean newPiece;
    private char[] pieceTypes;
    private Piece current;
    private boolean fallingUpdate;
    private Location first, second, third, fourth;
    private Thread fallingThread;

    private char holdPiece;
    private boolean canHold;

    public GameEngine(){
        board = new ArrayList();
        for (int i = 0; i < 30; i++) {
            board.add(new char[]{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '});
        }

        newPiece = false;
        pieceTypes = new char[] {'t', 'i', 'o', 'l', 'j', 's', 'z'};
        queue = new ArrayList();

        for (int i = 0; i < 5; i++){
            int index = (int)(Math.random()*7);
            queue.add(new Piece(pieceTypes[index]));
        }

        cycleQueue();

        fallingUpdate = false;

        holdPiece = ' ';


        fallingThread = new Thread( () -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(1000);
                    fallingUpdate = true;
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                    break;
                }catch (Exception e) {
                    e.printStackTrace();
                }  
            }
        }
        );
        fallingThread.start();
        canHold = true;
    }

    public void resetFallingThread(){
        fallingThread.interrupt();
        fallingThread = new Thread( () -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(1000);
                    fallingUpdate = true;
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                    break;
                }catch (Exception e) {
                    e.printStackTrace();
                }  
            }
        }
        );
        fallingThread.start();
    }

    public char getHold(){
        return holdPiece;
    }

    public ArrayList<Piece> getQueue(){
        return queue;
    }

    public void holdPiece(){
        if (holdPiece == ' '){
            holdPiece = current.getType();
            cycleQueue();
            canHold = false;
        }
        else {
            if (canHold){
                char oldHold = holdPiece;
                holdPiece = current.getType();
                current = new Piece(oldHold);
                canHold = false;
            }
        }
    }

    public void cycleQueue(){
        current = queue.remove(0);
        int index = (int)(Math.random()*7);
        queue.add(new Piece(pieceTypes[index]));
    }

    public void dropPiece(){
        fallingUpdate = true;
        resetFallingThread();
    }

    public ArrayList<char[]> getBoard(){
        return board;
    }

    public Piece getCurrent(){
        return current;
    }

    public void moveRight(){
        if (first.getX() < 9 && second.getX() < 9 && third.getX() < 9 && fourth.getX() < 9){
            if (board.get(first.getY())[first.getX()+1] == ' ' && board.get(second.getY())[second.getX()+1] == ' ' && board.get(third.getY())[third.getX()+1] == ' ' && board.get(fourth.getY())[fourth.getX()+1] == ' '){
                current.moveRight();
            }
        }
    }

    public void moveLeft(){
        if (first.getX() > 0 && second.getX() > 0 && third.getX() > 0 && fourth.getX() > 0){
            if (board.get(first.getY())[first.getX()-1] == ' ' && board.get(second.getY())[second.getX()-1] == ' ' && board.get(third.getY())[third.getX()-1] == ' ' && board.get(fourth.getY())[fourth.getX()-1] == ' '){
                current.moveLeft();
            }
        }
    }

    public void setCurrent(){
        first = current.getFirst();
        second = current.getSecond();
        third = current.getThird();
        fourth = current.getFourth();
    }

    public void clockWise(){
        current.rotateClockWise();
        setCurrent();

        if (first.getX() >= 0 && second.getX() >= 0 && third.getX() >= 0 && fourth.getX() >= 0){
            if (first.getX() < 10 && second.getX() < 10 && third.getX() < 10 && fourth.getX() < 10){
                // System.out.println();
                if (board.get(first.getY())[first.getX()] == ' ' && board.get(second.getY())[second.getX()] == ' ' && board.get(third.getY())[third.getX()] == ' ' && board.get(fourth.getY())[fourth.getX()] == ' '){
                    return;
                }
            }
        }

        current.rotateCounterClockWise();
        setCurrent();
    }

    public void counterClockWise(){
        current.rotateCounterClockWise();
        setCurrent();

        if (first.getX() >= 0 && second.getX() >= 0 && third.getX() >= 0 && fourth.getX() >= 0){
            if (first.getX() < 10 && second.getX() < 10 && third.getX() < 10 && fourth.getX() < 10){
                if (board.get(first.getY())[first.getX()] == ' ' && board.get(second.getY())[second.getX()] == ' ' && board.get(third.getY())[third.getX()] == ' ' && board.get(fourth.getY())[fourth.getX()] == ' '){
                    return;
                }
            }
        }

        current.rotateClockWise();
        setCurrent();
    }

    public void clearLines(){
        TreeSet<Integer> clearList = new TreeSet<>();

        clearList.add(first.getY());
        clearList.add(second.getY());
        clearList.add(third.getY());
        clearList.add(fourth.getY());
        int numCleared = 0;

        int number = clearList.size();

        for (int i = 0; i < number; i++){
            boolean clear = true;
            int thing = clearList.first();
            for (int e = 0; e < 10; e++){
                if (board.get(thing-numCleared)[e] == ' '){
                    clear = false;
                }
            }
            if (clear){
                board.remove(clearList.first()-numCleared);
                board.add(new char[]{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '});
                numCleared++;
            }
            clearList.pollFirst();
        }
        
    }

    public void placePiece(){
        board.get(first.getY())[first.getX()] = current.getType();
        board.get(second.getY())[second.getX()] = current.getType();
        board.get(third.getY())[third.getX()] = current.getType();
        board.get(fourth.getY())[fourth.getX()] = current.getType();

        canHold = true;
    }

    public void run(){
        while (true) { 
            synchronized (lock) {
                if (newPiece){
                    newPiece = false;

                    cycleQueue();
                }
                if (fallingUpdate){
                    first = current.getFirst();
                    second = current.getSecond();
                    third = current.getThird();
                    fourth = current.getFourth();


                    if (first.getY() == 0 || second.getY() == 0 || third.getY() == 0 || fourth.getY() == 0){
                        placePiece();
                        clearLines();
                        newPiece = true;
                    }
                    else if (board.get(first.getY()-1)[first.getX()] == ' ' && board.get(second.getY()-1)[second.getX()] == ' ' && board.get(third.getY()-1)[third.getX()] == ' ' && board.get(fourth.getY()-1)[fourth.getX()] == ' '){
                        current.drop();
                    }
                    else {
                        placePiece();
                        clearLines();
                        newPiece = true;
                    }

                    fallingUpdate = false;
                }
            }
        }
    }
}