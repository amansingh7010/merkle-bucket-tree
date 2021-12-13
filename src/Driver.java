package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Driver {
  public static void main(String[] args) throws IOException, FileNotFoundException {
    HashMap<Integer, String> studentList = new HashMap<>();
    JSONParser parser = new JSONParser();

    try{
      Object obj = parser.parse(new FileReader("C:/Users/amans/OneDrive - University of New Brunswick/UNB Study/Fall 2021/CS-6545 - Big Data/Project/merkle-bucket-tree/resources/data.json"));
      JSONObject json = new JSONObject();
      json = (JSONObject) obj;
      JSONArray jsonArray = (JSONArray) json.get("studentList");
      for (int i = 0; i < jsonArray.size(); i++){
        JSONObject student = (JSONObject) jsonArray.get(i);
        long id = (long) student.get("_id");
        String name = (String) student.get("name");
        studentList.add((int) id, name);
        System.out.println(studentList.get((int) id));
      }

      System.out.println("Number of Buckets: " + studentList.getBucketSize());
      MerkleBucketTree merkleBucketTree = new MerkleBucketTree(studentList.getBucketSize());
      System.out.println(merkleBucketTree.getRoot());
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
