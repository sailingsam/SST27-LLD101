public class Rectangle implements IResizable {
    private int width, height;
    
    public Rectangle() {
        this.width = 0;
        this.height = 0;
    }
    
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int area() {
        return width * height;
    }
}