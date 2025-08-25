
public class Square implements IShape {
    private int side;
    
    public Square() {
        this.side = 0;
    }
    
    public Square(int side) {
        this.side = side;
    }
    
    public void setSide(int side) {
        this.side = side;
    }
    
    public int getSide() {
        return side;
    }
    
    public int area() {
        return side * side;
    }
}