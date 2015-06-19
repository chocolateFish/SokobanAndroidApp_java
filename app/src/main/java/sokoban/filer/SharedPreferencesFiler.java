package sokoban.filer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class SharedPreferencesFiler extends FileHandler {
    private static SharedPreferences sharedPref;
    private static final String PREFERENCE_NAME = "AllMazes";

    public SharedPreferencesFiler(Context sokoContext){
        super();
        sharedPref = sokoContext.getSharedPreferences(
                SharedPreferencesFiler.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
      public String importMap(String key) {
        String defaultMaze = "#######|#.....#|#--.--#|#$-@$-#|#.$$$.#|#-----#|#######|";
        String zippedStr = sharedPref.getString(key, defaultMaze);
        return this.unZip(zippedStr);
    }

    @Override
    public void exportMap(String map, String key){
        String zippedMaze = this.zip(map);
        SharedPreferences.Editor editor  = sharedPref.edit();
        editor.putString(key, zippedMaze);
        editor.apply();
        // editor.commit();
    }

    public String[] loadAll(){
        Set<String> allKeysSet = sharedPref.getAll().keySet();
        String[] allKeys = new String[allKeysSet.size()];
        allKeys = allKeysSet.toArray(allKeys);

        //String[] allKeys = new String[totalMazes];
        //int position = 0;
        //for (Map.Entry<String, ?> e : allMazes.entrySet()){
        //    allKeys[position] = e.getKey();
        //    position ++;
        //}
        Arrays.sort(allKeys);
        return allKeys;
    }

    public String getKeyAvailability(String key){
        String msgString;
        if (sharedPref.contains(key)){
            String existingMaze = this.importMap(key);
            msgString = existingMaze + " exists at key: " + key + "\n Proceed to override, or use a different key";

        } else {
            msgString ="Key is free.";
        }
        return msgString;
    }

    public boolean containsData(){
        return sharedPref.getAll().size() > 0;
    }

    public void removeData(String key){
        SharedPreferences.Editor editor  = sharedPref.edit();
        editor.remove(key);
        editor.apply();
    }
}