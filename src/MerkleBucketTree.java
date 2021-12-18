package src;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class MerkleBucketTree<K,V> {

  String root;
  private HashMap<K,V> hashMap;
  private ArrayList<String> bucketHashes;

  public MerkleBucketTree(HashMap<K, V> hashMap) {
    this.hashMap = hashMap;
    this.bucketHashes = generateBucketHashes();
    this.root = generateMerkleTree(bucketHashes);
  }

  private ArrayList<String> generateBucketHashes() {
    ArrayList<String> buckets = new ArrayList<>();

    for(int i = 0; i < hashMap.getBucketSize(); i++) {
      String hashInput = "";
      if(hashMap.getBucketArray().get(i) != null) {
        Node<K,V> head = hashMap.getBucketArray().get(i);
        
        while(head != null) {
          hashInput +=  head.value;
          head = head.next;
        }
      } else {
        byte[] array = new byte[i];
        hashInput = new String(array, Charset.forName("UTF-8"));
      }

      buckets.add(getSHA(hashInput));
    }

    return buckets;
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

  public boolean validate(HashMap<Integer, String> newHashMap, K key) {
    V value = hashMap.get(key);
    if(value == null) {
      System.err.println("Key does not exist.");
      return false;
    }

    MerkleBucketTree<K,V> generatedTree = new MerkleBucketTree(newHashMap);
    if(this.root.equals(generatedTree.root)) {
      System.out.println("Data has not been tampered");
      return true;
    }

    System.err.println("Data has been tampered");
    return false;
  }
  
}