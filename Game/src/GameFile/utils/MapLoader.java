package GameFile.utils;

import GameFile.game.GameObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MapLoader {
    private static Map<String, String> maps = new HashMap<>(){{
        put("level1","maps/testMap.csv");
    }};
    public static List<GameObject> loadMapObjects(final String level) {
        InputStreamReader irs = new InputStreamReader(
                AssetManager.class.getClassLoader().getResourceAsStream(maps.get(level))
        );

        List<GameObject> t = new ArrayList<>();
        try (BufferedReader mapReader = new BufferedReader(irs)){
            int row=0;
            while(mapReader.ready()) {
                String[] items = mapReader.readLine().trim().split(",");
                System.out.println(Arrays.toString(items));

                for(int col = 0; col< items.length; col++ ) {
                    String gameObject = items[col];
                    if("0".equals(gameObject)) continue;
                    t.add(GameObject.newInstance(gameObject,col*32,row*32));
                }
                row++;
            }
        }catch( IOException e){
            throw new RuntimeException();

        }
        return t;
    }
}
