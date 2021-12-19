package src;
import java.io.FileReader;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Driver {

  // private static final String ORIGINAL_JSON = "C:/Users/amans/OneDrive - University of New Brunswick/UNB Study/Fall 2021/CS-6545 - Big Data/Project/merkle-bucket-tree/resources/small_data_original.json";
  // private static final String MODIFIED_JSON = "C:/Users/amans/OneDrive - University of New Brunswick/UNB Study/Fall 2021/CS-6545 - Big Data/Project/merkle-bucket-tree/resources/small_data_tampered.json";
  private static final String ORIGINAL_JSON = "C:/Users/amans/OneDrive - University of New Brunswick/UNB Study/Fall 2021/CS-6545 - Big Data/Project/merkle-bucket-tree/resources/data_original.json";
  private static final String MODIFIED_JSON = "C:/Users/amans/OneDrive - University of New Brunswick/UNB Study/Fall 2021/CS-6545 - Big Data/Project/merkle-bucket-tree/resources/data_tampered.json";
  private static final String VARIABLE = "studentList";
  private static final String KEY = "_id";
  private static final String VALUE = "name"; 

  public static void main(String[] args) {
    HashMap<Integer, String> originalHashMap = read(ORIGINAL_JSON);
    HashMap<Integer, String> tamperedHashMap = read(MODIFIED_JSON);
    // HashMap<Integer, String> tamperedHashMap = read(ORIGINAL_JSON);

    MerkleBucketTree merkleBucketTree = new MerkleBucketTree(originalHashMap);
    System.out.println("Generated Merkle bucket tree with head: " + merkleBucketTree.root);

    // Data Validation
    merkleBucketTree.validate(tamperedHashMap, 506);
  }

  private static HashMap<Integer, String> read(String filePath) {
    HashMap<Integer, String> hashMap = new HashMap<>();
    JSONParser parser = new JSONParser();

    try {
      Object obj = parser.parse(new FileReader(filePath));
      JSONObject json = new JSONObject();
      json = (JSONObject) obj;
      JSONArray jsonArray = (JSONArray) json.get(VARIABLE);

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
