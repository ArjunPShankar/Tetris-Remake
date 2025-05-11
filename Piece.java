public class Piece {
    private char type;
    private Location location;
    private int rotation;
    private Location first,second,third,fourth;

    public Piece(char type){
        this.type = type;
        location = new Location(5, 20);
        rotation = 0;

        setPiece();
    }

    public Piece(char type, int x, int y){
        this.type = type;
        location = new Location(x, y);
        rotation = 0;

        setPiece();
    }

    public void setPiece(){
        int x = location.getX();
        int y = location.getY();

        switch (type){
            case 't':
                switch (rotation){
                    case 0:
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        third = new Location(x, y+1);
                        fourth = new Location(x+1, y);
                        break;
                    case 1:
                        first = new Location(x, y-1);
                        second = new Location(x, y);
                        third = new Location(x, y+1);
                        fourth = new Location(x+1, y);
                        break;
                    case 2:
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        third = new Location(x, y-1);
                        fourth = new Location(x+1, y);
                        break;
                    case 3:
                        first = new Location(x, y-1);
                        second = new Location(x, y);
                        third = new Location(x, y+1);
                        fourth = new Location(x-1, y);
                        break;
                }
                break;
            case 'i':
                switch (rotation){
                    case 0:
                        third = new Location(x-2, y);
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        fourth = new Location(x+1, y);
                        break;
                    case 1:
                        third = new Location(x, y-2);
                        first = new Location(x, y-1);
                        second = new Location(x, y);
                        fourth = new Location(x, y+1);
                        break;
                    case 2:
                        third = new Location(x-2, y);
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        fourth = new Location(x+1, y);
                        break;
                    case 3:
                        third = new Location(x, y-2);
                        first = new Location(x, y-1);
                        second = new Location(x, y);
                        fourth = new Location(x, y+1);
                        break;
                }
                break;
            case 'o':
                first = new Location(x-1, y);
                second = new Location(x, y);
                third = new Location(x, y+1);
                fourth = new Location(x-1, y+1);
                break;
            case 'l':
                switch (rotation){
                    case 0:
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        third = new Location(x+1, y);
                        fourth = new Location(x+1, y+1);
                        break;
                    case 1:
                        first = new Location(x, y-1);
                        second = new Location(x, y);
                        third = new Location(x, y+1);
                        fourth = new Location(x+1, y-1);
                        break;
                    case 2:
                        first = new Location(x+1, y);
                        second = new Location(x, y);
                        third = new Location(x-1, y);
                        fourth = new Location(x-1, y-1);
                        break;
                    case 3:
                        first = new Location(x, y+1);
                        second = new Location(x, y);
                        third = new Location(x, y-1);
                        fourth = new Location(x-1, y+1);
                        break;
                }
                break;
            case 'j':
                switch (rotation){
                    case 0:
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        third = new Location(x+1, y);
                        fourth = new Location(x+1, y-1);
                        break;
                    case 1:
                        first = new Location(x, y-1);
                        second = new Location(x, y);
                        third = new Location(x, y+1);
                        fourth = new Location(x-1, y-1);
                        break;
                    case 2:
                        first = new Location(x+1, y);
                        second = new Location(x, y);
                        third = new Location(x-1, y);
                        fourth = new Location(x-1, y+1);
                        break;
                    case 3:
                        first = new Location(x, y+1);
                        second = new Location(x, y);
                        third = new Location(x, y-1);
                        fourth = new Location(x+1, y+1);
                        break;
                }
                break;
            case 's':
                switch (rotation){
                    case 0:
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        third = new Location(x, y+1);
                        fourth = new Location(x+1, y+1);
                        break;
                    case 1:
                        first = new Location(x, y-1);
                        second = new Location(x, y);
                        third = new Location(x-1, y);
                        fourth = new Location(x-1, y+1);
                        break;
                    case 2:
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        third = new Location(x, y+1);
                        fourth = new Location(x+1, y+1);
                        break;
                    case 3:
                        first = new Location(x, y-1);
                        second = new Location(x, y);
                        third = new Location(x-1, y);
                        fourth = new Location(x-1, y+1);
                        break;
                }
                break;
            case 'z':
                switch (rotation){
                    case 0:
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        third = new Location(x, y-1);
                        fourth = new Location(x+1, y-1);
                        break;
                    case 1:
                        first = new Location(x, y+1);
                        second = new Location(x, y);
                        third = new Location(x-1, y);
                        fourth = new Location(x-1, y-1);
                        break;
                    case 2:
                        first = new Location(x-1, y);
                        second = new Location(x, y);
                        third = new Location(x, y-1);
                        fourth = new Location(x+1, y-1);
                        break;
                    case 3:
                        first = new Location(x, y+1);
                        second = new Location(x, y);
                        third = new Location(x-1, y);
                        fourth = new Location(x-1, y-1);
                        break;
                }
                break;
        }
    }

    public int getX(){
        return location.getX();
    }

    public int getY(){
        return location.getY();
    }

    public char getType(){
        return type;
    }

    public int getRotation(){
        return rotation;
    }

    public void rotateClockWise(){
        if (rotation == 3){
            rotation = 0;
        }
        else {
            rotation++;
        }
        setPiece();
    }

    public void rotateCounterClockWise(){
        if (rotation == 0){
            rotation = 3;
        }
        else {
            rotation--;
        }
        setPiece();
    }
    

    public void setY(int y){
        location.setY(y);
        setPiece();
    }

    public void setX(int x){
        location.setX(x);
        setPiece();
    }

    public void moveRight(){
        // if (first.getX() < 9 && second.getX() < 9 && third.getX() < 9 && fourth.getX() < 0){
        location.setX(location.getX()+1);

        first.setX(first.getX()+1);
        second.setX(second.getX()+1);
        third.setX(third.getX()+1);
        fourth.setX(fourth.getX()+1);
    }

    public void moveLeft(){
        // if (first.getX() < 9 && second.getX() < 9 && third.getX() < 9 && fourth.getX() < 0){
        location.setX(location.getX()-1);

        first.setX(first.getX()-1);
        second.setX(second.getX()-1);
        third.setX(third.getX()-1);
        fourth.setX(fourth.getX()-1);
    }

    public void drop(){
        location.setY(location.getY()-1);

        first.setY(first.getY()-1);
        second.setY(second.getY()-1);
        third.setY(third.getY()-1);
        fourth.setY(fourth.getY()-1);

    }

    public Location getFirst(){
        return first;
    }

    public Location getSecond(){
        return second;
    }

    public Location getThird(){
        return third;
    }

    public Location getFourth(){
        return fourth;
    }
}
