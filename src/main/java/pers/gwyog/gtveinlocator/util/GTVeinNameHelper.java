package pers.gwyog.gtveinlocator.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GTVeinNameHelper {
    private static byte id = 0;
    private static Map<String, Byte> map1 = new HashMap<String, Byte>();
    private static Map<Byte, String> map2 = new HashMap<Byte, String>();
    
    public static void registerVeinName(String str) {
        map1.put(str, id);
        map2.put(id++, str);
    }
    
    public static byte getIndex(String str) {
        return map1.get(str);
    }
    
    public static String getName(byte index) {
        return map2.get(index);
    }
    
    
}

