public class Animate implements Runnable{
    
    private Screen s;
    public Animate(Screen s){
        this.s = s;
    }

    public void run(){
        while (true){
            s.repaint();
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}