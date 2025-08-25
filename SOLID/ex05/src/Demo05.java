
public class Demo05 {
    // Method for resizable shapes (only Rectangle implements IResizable)
    static int areaAfterResize(IResizable r) {
        r.setWidth(5); 
        r.setHeight(4); 
        return r.area();
    }
    
    // Method for square - respects its nature
    static int areaAfterSquareResize(Square s) {
        s.setSide(4); // Square can only have equal sides
        return s.area();
    }
    
    public static void main(String[] args) {
        // Original Rectangle behavior - now works correctly
        System.out.println(areaAfterResize(new Rectangle())); // 20
        
        // Square behavior - handled appropriately for its nature  
        System.out.println(areaAfterSquareResize(new Square())); // 16
        
        // Demo: Show that LSP is now followed
        System.out.println();
        System.out.println("--- Demo: LSP compliance ---");
        
        // All IShape implementations can be used polymorphically for area calculation
        IShape[] shapes = {new Rectangle(3, 4), new Square(5)};
        for (IShape shape : shapes) {
            System.out.println("Area: " + shape.area());
        }
        
        // Only IResizable shapes can be resized
        IResizable resizable = new Rectangle(1, 1);
        resizable.setWidth(6);
        resizable.setHeight(7);
        System.out.println("Resized rectangle area: " + resizable.area());
    }
}
