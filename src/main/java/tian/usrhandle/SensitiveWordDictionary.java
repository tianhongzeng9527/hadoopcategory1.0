package tian.usrhandle;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tian on 15-12-22.
 */
public class SensitiveWordDictionary {
    public static Set<String> sensitiveWordsDictionary = null;
    public static boolean isNeedChange = false;
    public SensitiveWordDictionary(){

    }
    public void init(){
        synchronized (this){
            if(sensitiveWordsDictionary == null || isNeedChange == true){
                sensitiveWordsDictionary = new HashSet<String>();
                isNeedChange = false;
            }
        }
    }
}
