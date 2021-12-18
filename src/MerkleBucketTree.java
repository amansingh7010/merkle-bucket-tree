package src;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class MerkleBucketTree<K,V> {

  String root;

  public MerkleBucketTree(HashMap<K, V> studentList) {

    ArrayList<String> buckets = new ArrayList<>();
    ArrayList<Node<K, V>> localBucketArray = studentList.getBucketArray();
    for(int i = 0; i < studentList.getBucketSize(); i++) {
      String hashInput = "";
      if(localBucketArray.get(i) != null) {
        Node<K,V> head = localBucketArray.get(i);
        while(head.next != null) {
          hashInput +=  head.value;
          head = head.next;
        }
      } else {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        hashInput = new String(array, Charset.forName("UTF-8"));
      }

      buckets.add(getSHA(hashInput));
    }
    
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
