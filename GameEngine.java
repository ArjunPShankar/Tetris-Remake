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

    public void hardDrop(){
        if (current.getFirst().getY() == 0 || current.getSecond().getY() == 0 || current.getThird().getY() == 0 || current.getFourth().getY() == 0){
            placePiece();
            clearLines();
            resetFallingThread();
            newPiece = true;
        }
        else if (board.get(current.getFirst().getY()-1)[current.getFirst().getX()] == ' ' && board.get(current.getSecond().getY()-1)[current.getSecond().getX()] == ' ' && board.get(current.getThird().getY()-1)[current.getThird().getX()] == ' ' && board.get(current.getFourth().getY()-1)[current.getFourth().getX()] == ' '){
            current.drop();
            hardDrop();
        }
        else {
            placePiece();
            clearLines();
            resetFallingThread();
            newPiece = true;
        }
    }

    public Location[] getOutline(int iteration){
        if (current.getFirst().getY()-iteration == 0 || current.getSecond().getY()-iteration == 0 || current.getThird().getY()-iteration == 0 || current.getFourth().getY()-iteration == 0){
            Location[] thing = {new Location(current.getFirst().getX(), current.getFirst().getY()-iteration), new Location(current.getSecond().getX(), current.getSecond().getY()-iteration), new Location(current.getThird().getX(), current.getThird().getY()-iteration), new Location(current.getFourth().getX(), current.getFourth().getY()-iteration)};
            return thing;
        }
        else if (board.get(current.getFirst().getY()-iteration-1)[current.getFirst().getX()] == ' ' && board.get(current.getSecond().getY()-iteration-1)[current.getSecond().getX()] == ' ' && board.get(current.getThird().getY()-iteration-1)[current.getThird().getX()] == ' ' && board.get(current.getFourth().getY()-iteration-1)[current.getFourth().getX()] == ' '){
            return getOutline(iteration+1);
        }
        else {
            Location[] thing = {new Location(current.getFirst().getX(), current.getFirst().getY()-iteration), new Location(current.getSecond().getX(), current.getSecond().getY()-iteration), new Location(current.getThird().getX(), current.getThird().getY()-iteration), new Location(current.getFourth().getX(), current.getFourth().getY()-iteration)};
            return thing;
        }
    }

    public ArrayList<char[]> getBoard(){
        return board;
    }

    public Piece getCurrent(){
        return current;
    }

    public void moveRight(){
        if (current.getFirst().getX() < 9 && current.getSecond().getX() < 9 && current.getThird().getX() < 9 && current.getFourth().getX() < 9){
            if (board.get(current.getFirst().getY())[current.getFirst().getX()+1] == ' ' && board.get(current.getSecond().getY())[current.getSecond().getX()+1] == ' ' && board.get(current.getThird().getY())[current.getThird().getX()+1] == ' ' && board.get(current.getFourth().getY())[current.getFourth().getX()+1] == ' '){
                current.moveRight();
            }
        }
    }

    public void moveLeft(){
        if (current.getFirst().getX() > 0 && current.getSecond().getX() > 0 && current.getThird().getX() > 0 && current.getFourth().getX() > 0){
            if (board.get(current.getFirst().getY())[current.getFirst().getX()-1] == ' ' && board.get(current.getSecond().getY())[current.getSecond().getX()-1] == ' ' && board.get(current.getThird().getY())[current.getThird().getX()-1] == ' ' && board.get(current.getFourth().getY())[current.getFourth().getX()-1] == ' '){
                current.moveLeft();
            }
        }
    }

    public void clockWise(){
        current.rotateClockWise();

        if (current.getFirst().getX() >= 0 && current.getSecond().getX() >= 0 && current.getThird().getX() >= 0 && current.getFourth().getX() >= 0){
            if (current.getFirst().getX() < 10 && current.getSecond().getX() < 10 && current.getThird().getX() < 10 && current.getFourth().getX() < 10){
                if (board.get(current.getFirst().getY())[current.getFirst().getX()] == ' ' && board.get(current.getSecond().getY())[current.getSecond().getX()] == ' ' && board.get(current.getThird().getY())[current.getThird().getX()] == ' ' && board.get(current.getFourth().getY())[current.getFourth().getX()] == ' '){
                    return;
                }
            }
        }

        current.rotateCounterClockWise();
    }

    public void counterClockWise(){
        current.rotateCounterClockWise();

        if (current.getFirst().getX() >= 0 && current.getSecond().getX() >= 0 && current.getThird().getX() >= 0 && current.getFourth().getX() >= 0){
            if (current.getFirst().getX() < 10 && current.getSecond().getX() < 10 && current.getThird().getX() < 10 && current.getFourth().getX() < 10){
                if (board.get(current.getFirst().getY())[current.getFirst().getX()] == ' ' && board.get(current.getSecond().getY())[current.getSecond().getX()] == ' ' && board.get(current.getThird().getY())[current.getThird().getX()] == ' ' && board.get(current.getFourth().getY())[current.getFourth().getX()] == ' '){
                    return;
                }
            }
        }

        current.rotateClockWise();
    }

    public void clearLines(){
        TreeSet<Integer> clearList = new TreeSet<>();

        clearList.add(current.getFirst().getY());
        clearList.add(current.getSecond().getY());
        clearList.add(current.getThird().getY());
        clearList.add(current.getFourth().getY());
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
        board.get(current.getFirst().getY())[current.getFirst().getX()] = current.getType();
        board.get(current.getSecond().getY())[current.getSecond().getX()] = current.getType();
        board.get(current.getThird().getY())[current.getThird().getX()] = current.getType();
        board.get(current.getFourth().getY())[current.getFourth().getX()] = current.getType();

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
                    if (current.getFirst().getY() == 0 || current.getSecond().getY() == 0 || current.getThird().getY() == 0 || current.getFourth().getY() == 0){
                        placePiece();
                        clearLines();
                        newPiece = true;
                    }
                    else if (board.get(current.getFirst().getY()-1)[current.getFirst().getX()] == ' ' && board.get(current.getSecond().getY()-1)[current.getSecond().getX()] == ' ' && board.get(current.getThird().getY()-1)[current.getThird().getX()] == ' ' && board.get(current.getFourth().getY()-1)[current.getFourth().getX()] == ' '){
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