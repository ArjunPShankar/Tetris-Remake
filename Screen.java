import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;


public class Screen extends JPanel implements ActionListener,KeyListener{
    private GameEngine engine;
    private final int SIZE = 25;

    private boolean moveRight, moveLeft, dropPiece, counterClockWise, clockWise;

    public Screen(){
        this.setFocusable(true);
        this.setLayout(null);
        addKeyListener(this);


        new Thread(new Animate(this)).start();

        engine = new GameEngine();
        new Thread(engine).start();

        new Thread(new KeyThread(this)).start();
        new Thread(new RotateThread(this)).start();
    }

    public Dimension getPreferredSize(){
		return new Dimension(30* SIZE, 31*SIZE);
	}

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,30*SIZE, 31*SIZE);

        g.setColor(new Color(0,1,2));
        g.fillRect(10*SIZE, 100, 10*SIZE, 21*SIZE);

        g.setColor(new Color(39,20,15));
        for (int i = 0; i < 10; i++){
            for (int e = 0; e < 21; e++){
                g.drawRect((10+i)*SIZE, e * SIZE+100, SIZE, SIZE);
            }
        }

        //orange outline
        g.setColor(new Color(215, 124, 75));
        g.fillRect(10*SIZE-15, 90, 15, 20*SIZE+45);
        g.fillRect(20*SIZE, 80, 15, 20*SIZE+55);
        g.fillRect(10*SIZE-15, 21*SIZE + 100, 10*SIZE + 30, 25);

        //outline hold piece
        g.fillRect(3*SIZE, 80, 7*SIZE, 25);
        g.fillRect(3*SIZE, 90+5*SIZE, 7*SIZE, 15);
        g.fillRect(3*SIZE, 90, 15, 5*SIZE);

        g.setColor(new Color(0,0,0));
        g.setFont(new Font("impact", Font.PLAIN, 22));
        g.drawString("Hold", 3*SIZE+15, 100);

        //outline queue
        g.setColor(new Color(215, 124, 75));
        g.fillRect(20*SIZE, 80, 7*SIZE+15, 25);
        g.fillRect(20*SIZE, 90+21*SIZE+20, 7*SIZE+15, 15);
        g.fillRect(27*SIZE, 90, 15, 21*SIZE+20);

        g.setColor(new Color(0,0,0));
        g.setFont(new Font("impact", Font.PLAIN, 22));
        g.drawString("Next", 20*SIZE+15, 100);



        //display board and current piece
        ArrayList<char[]> board = engine.getBoard();
        Piece current = engine.getCurrent();


        for (int i = 0; i < 20; i++){
            for (int e = 0; e < 10; e++){
                switch (board.get(i)[e]){
                    case 't':
                        g.setColor(new Color(164, 62, 154));
                        g.fillRect(e*SIZE + 10*SIZE, (24-i)*SIZE, SIZE,SIZE);
                        break;
                    case 'o':
                        g.setColor(new Color(178, 152, 49));
                        g.fillRect(e*SIZE + 10*SIZE, (24-i)*SIZE, SIZE,SIZE);
                        break;
                    case 'i':
                        g.setColor(new Color(49, 179, 130));
                        g.fillRect(e*SIZE + 10*SIZE, (24-i)*SIZE, SIZE,SIZE);
                        break;
                    case 's':
                        g.setColor(new Color(160, 228, 47));
                        g.fillRect(e*SIZE + 10*SIZE, (24-i)*SIZE, SIZE,SIZE);
                        break;
                    case 'z':
                        g.setColor(new Color(179, 51, 58));
                        g.fillRect(e*SIZE + 10*SIZE, (24-i)*SIZE, SIZE,SIZE);
                        break;
                    case 'l':
                        g.setColor(new Color(179, 99, 50));
                        g.fillRect(e*SIZE + 10*SIZE, (24-i)*SIZE, SIZE,SIZE);
                        break;
                    case 'j':
                        g.setColor(new Color(78, 61, 164));
                        g.fillRect(e*SIZE + 10*SIZE, (24-i)*SIZE, SIZE,SIZE);
                        break;
                }
            }
        }

        // display greyed out outline of current piece
        Location[] outline = engine.getOutline(0);
        g.setColor(new Color(100,100,100));

        for (int i = 0; i < 4; i++){
            drawSquare(outline[i], g);
        }


        setColor(g, current.getType());

        g.fillRect(current.getFirst().getX()*SIZE + 10*SIZE, (24-current.getFirst().getY())*SIZE, SIZE,SIZE);
        g.fillRect(current.getSecond().getX()*SIZE + 10*SIZE, (24-current.getSecond().getY())*SIZE, SIZE,SIZE);
        g.fillRect(current.getThird().getX()*SIZE + 10*SIZE, (24-current.getThird().getY())*SIZE, SIZE,SIZE);
        g.fillRect(current.getFourth().getX()*SIZE + 10*SIZE, (24-current.getFourth().getY())*SIZE, SIZE,SIZE);

        g.setColor(new Color(0,0,0));
        g.drawRect(current.getFirst().getX()*SIZE + 10*SIZE, (24-current.getFirst().getY())*SIZE, SIZE,SIZE);
        g.drawRect(current.getSecond().getX()*SIZE + 10*SIZE, (24-current.getSecond().getY())*SIZE, SIZE,SIZE);
        g.drawRect(current.getThird().getX()*SIZE + 10*SIZE, (24-current.getThird().getY())*SIZE, SIZE,SIZE);
        g.drawRect(current.getFourth().getX()*SIZE + 10*SIZE, (24-current.getFourth().getY())*SIZE, SIZE,SIZE);



        //display hold piece
        if (engine.getHold() != ' '){
            setColor(g, engine.getHold());
            Piece hold = new Piece(engine.getHold());

            int height = 10;
            int deltaX = SIZE;

            if (engine.getHold() == 'o' || engine.getHold() == 's' || engine.getHold() == 't' || engine.getHold() == 'l'){
                height = height + 25;
            }
            if (engine.getHold() == 'i'){
                height = height + 15;
                deltaX = deltaX + 15;
            }

            g.fillRect(hold.getFirst().getX()*SIZE + deltaX, (25-hold.getFirst().getY())*SIZE+height, SIZE,SIZE);
            g.fillRect(hold.getSecond().getX()*SIZE + deltaX, (25-hold.getSecond().getY())*SIZE+height, SIZE,SIZE);
            g.fillRect(hold.getThird().getX()*SIZE + deltaX, (25-hold.getThird().getY())*SIZE+height, SIZE,SIZE);
            g.fillRect(hold.getFourth().getX()*SIZE + deltaX, (25-hold.getFourth().getY())*SIZE+height, SIZE,SIZE);
        }

        //display queue
        ArrayList<Piece> queue = engine.getQueue();

        int height = 15;
        int deltaX = 18 * SIZE+5;

        for (int i = 0; i < queue.size(); i++){
            Piece item = queue.get(i);

            setColor(g, item.getType());
            Piece next = queue.get(i);

            if (engine.getHold() == 'o' || engine.getHold() == 's' || engine.getHold() == 't' || engine.getHold() == 'l'){
                height = height + 25;
            }
            if (engine.getHold() == 'i'){
                height = height + 15;
                deltaX = deltaX + 15;
            }

            g.fillRect(item.getFirst().getX()*SIZE + deltaX, (25-item.getFirst().getY())*SIZE+height, SIZE,SIZE);
            g.fillRect(item.getSecond().getX()*SIZE + deltaX, (25-item.getSecond().getY())*SIZE+height, SIZE,SIZE);
            g.fillRect(item.getThird().getX()*SIZE + deltaX, (25-item.getThird().getY())*SIZE+height, SIZE,SIZE);
            g.fillRect(item.getFourth().getX()*SIZE + deltaX, (25-item.getFourth().getY())*SIZE+height, SIZE,SIZE);

            height = 115 + 4*SIZE * i;
            deltaX = 18 * SIZE+5;
        }
    }

    public void setColor(Graphics g, char type){
        switch (type){
            case 't':
                g.setColor(new Color(164, 62, 154));
                break;
            case 'o':
                g.setColor(new Color(178, 152, 49));
                break;
            case 'i':
                g.setColor(new Color(49, 179, 130));
                break;
            case 's':
                g.setColor(new Color(160, 228, 47));
                break;
            case 'z':
                g.setColor(new Color(179, 51, 58));
                break;
            case 'l':
                g.setColor(new Color(179, 99, 50));
                break;
            case 'j':
                g.setColor(new Color(78, 61, 164));
                break;
        }
    }

    public void drawSquare(Location l, Graphics g){
        g.fillRect(l.getX()*SIZE + 10*SIZE, (24-l.getY())*SIZE, SIZE,SIZE);
    }

    public void addImage(Graphics g, String adress, int x, int y, int width, int height){
        Image image;
        try {
            image = ImageIO.read(new File(adress));
            g.drawImage(image, x, y, width,height,this);

        } catch (Exception e) {}
    }

    public void playSound(String fileName){
        File file = new File(fileName);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {}
    }

    public void actionPerformed(ActionEvent e){
       
    }

    public void moveRight(){
        engine.moveRight();
    }

    public void moveLeft(){
        engine.moveLeft();
    }

    public void dropPiece(){
        engine.dropPiece();
    }

    public void clockWise(){
        engine.clockWise();
    }

    public void counterClockWise(){
        engine.counterClockWise();
    }

    public boolean getMoveRight(){
        return moveRight;
    }

    public boolean getMoveLeft(){
        return moveLeft;
    }

    public boolean getDropPiece(){
        return dropPiece;
    }

    public boolean getClockWise(){
        return clockWise;
    }

    public boolean getCounterClockWise(){
        return counterClockWise;
    }

    public void keyPressed(KeyEvent e) {
        // System.out.println(e.getKeyCode());
        if (e.getKeyCode() == 39){
            moveRight = true;
        }
        if (e.getKeyCode() == 37){
            moveLeft = true;
        }
        if (e.getKeyCode() == 40){
            dropPiece = true;
        }
        if (e.getKeyCode() == 38){
            clockWise = true;
        }
        if (e.getKeyCode() == 67){
            engine.holdPiece();
        }
        if (e.getKeyCode() == 32){
            engine.hardDrop();
        }
    }
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 39){
            moveRight = false;
        }
        if (e.getKeyCode() == 37){
            moveLeft = false;
        }
        if (e.getKeyCode() == 40){
            dropPiece = false;
        }
        if (e.getKeyCode() == 38){
            clockWise = false;
        }
    }
    public void keyTyped(KeyEvent e) {}


}