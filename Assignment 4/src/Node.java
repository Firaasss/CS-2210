public int divide(int a, int b) {
    int c = -1;
    
    try {
        c = a / b;
    } 
    catch (Exception e) {
        System.err.print("Exception ");
    } 
    finally {
        System.err.println("Finally ");
    }
    
    return c;
}