public class RotateThread implements Runnable{
    
    private Screen s;
    public RotateThread(Screen s){
        this.s = s;
    }

    public void run(){
        while (true){
            while (!s.getClockWise()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (s.getClockWise()){
                s.clockWise();
                // System.out.println("ss");
            }


            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}