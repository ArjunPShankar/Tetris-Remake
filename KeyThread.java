public class KeyThread implements Runnable{
    
    private Screen s;
    public KeyThread(Screen s){
        this.s = s;
    }

    public void run(){
        while (true){
            while (!s.getMoveRight() && !s.getMoveLeft() && !s.getDropPiece()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (s.getMoveRight()){
                s.moveRight();
            }
            if (s.getMoveLeft()){
                s.moveLeft();
            }
            if (s.getDropPiece()){
                s.dropPiece();
            }

            try {Thread.sleep(75);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    
}