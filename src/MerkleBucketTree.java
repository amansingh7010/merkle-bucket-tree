package src;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MerkleBucketTree {

  String root;

  public MerkleBucketTree(int bucketSize) {

    ArrayList<String> buckets = new ArrayList<>();
    for(int i = 0; i < bucketSize; i++) {
      buckets.add(Integer.toString(i));

      // Calculate hash of each bucket here
      //...
      
    }

    //Init
    this.root = generateMerkleTree(buckets);
  }

  public String getRoot() {
    return this.root;
  }

  public static String getSHA(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] messageDigest = md.digest(input.getBytes());
      BigInteger no = new BigInteger(1, messageDigest);
      String hashText = no.toString(16);

      while (hashText.length() < 32) {
          hashText = "0" + hashText;
      }
      return hashText;
    }
    catch (NoSuchAlgorithmException e) {
      System.out.println("Exception thrown for incorrect algorithm: " + e);
      e.printStackTrace();
      return null;
    }
  }

  public String generateMerkleTree(ArrayList<String> bucketArray) {
    ArrayList<String> root = merkleTree(bucketArray);
    return root.get(0);
  }

  // Recursive method to generate Merkle Tree
  private ArrayList<String> merkleTree(ArrayList<String> hashList) {
    if (hashList.size() == 1) {
      return hashList;
    }

    ArrayList<String> parentHashList = new ArrayList<>();

    for (int i = 0; i < hashList.size(); i+=2) {
      // If last index is not odd
      if (i != hashList.size() - 1) {
        String firstValue = hashList.get(i);
        String secondValue = hashList.get(i+1);
        String hashedString = getSHA(firstValue.concat(secondValue));
        parentHashList.add(hashedString);
      }
    }

    // Handle odd number of buckets
    if (hashList.size() % 2 == 1) {
      String lastValue = hashList.get(hashList.size() - 1);
      String hashedString = getSHA(lastValue.concat(lastValue));
      parentHashList.add(hashedString);
    }

    return merkleTree(parentHashList);
  }
  
}
