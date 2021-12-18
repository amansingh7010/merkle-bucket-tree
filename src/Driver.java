package src;
import java.io.FileReader;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Driver {

  private static final String ORIGINAL_JSON_FILE_PATH = "C:/Users/amans/OneDrive - University of New Brunswick/UNB Study/Fall 2021/CS-6545 - Big Data/Project/merkle-bucket-tree/resources/data.json";
  private static final String VARIABLE_TO_READ = "studentList";
  private static final String KEY = "_id";
  private static final String VALUE = "name"; 

  public static void main(String[] args) {
    HashMap<Integer, String> originalHashMap = read();
    MerkleBucketTree merkleBucketTree = new MerkleBucketTree(originalHashMap);
    System.out.println("Head: " + merkleBucketTree.root);
  }

  private static HashMap<Integer, String> read() {
    HashMap<Integer, String> hashMap = new HashMap<>();
    JSONParser parser = new JSONParser();

    try {
      Object obj = parser.parse(new FileReader(ORIGINAL_JSON_FILE_PATH));
      JSONObject json = new JSONObject();
      json = (JSONObject) obj;
      JSONArray jsonArray = (JSONArray) json.get(VARIABLE_TO_READ);

      for (int i = 0; i < jsonArray.size(); i++) {
        JSONObject object = (JSONObject) jsonArray.get(i);
        long key = (long) object.get(KEY);
        String value = (String) object.get(VALUE);
        hashMap.add((int) key, value);
      }

    } catch(Exception e) {
      e.printStackTrace();
    }
    
    return hashMap;
  }
}
