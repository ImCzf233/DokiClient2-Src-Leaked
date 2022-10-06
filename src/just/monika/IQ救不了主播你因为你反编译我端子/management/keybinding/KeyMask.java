package just.monika.IQ救不了主播你因为你反编译我端子.management.keybinding;

public enum KeyMask
{
    None((int[])null), 
    Shift(new int[] { 42, 54 }), 
    Control(new int[] { 29, 157 }), 
    Alt(new int[] { 56, 184 });
    
    private final int[] keys;
    
    private KeyMask(final int[] keys) {
        this.keys = keys;
    }
    
    public int[] getKeys() {
        return this.keys;
    }
    
    public static KeyMask getMask(final String name) {
        for (final KeyMask mask : values()) {
            if (mask.name().toLowerCase().startsWith(name.toLowerCase())) {
                return mask;
            }
        }
        return KeyMask.None;
    }
}
