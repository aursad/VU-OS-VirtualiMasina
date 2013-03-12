package registers;

public class IntRegister {
    private byte data;


    public IntRegister()
    {
        data = 0;
    }
    
    public void set(int value)
    {
        data = (byte) value;
    }


    public int get()
    {
        return data;
    }
    
    public void clear()
    {
        data = 0;
    }
}
