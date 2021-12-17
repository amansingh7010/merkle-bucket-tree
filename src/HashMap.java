package src;

import java.util.ArrayList;
import java.util.Objects;

class HashMap<K, V> {
  private final int DEFAULT_HASH_TABLE_SIZE = 10;
  private final double LOAD_FACTOR = 0.8;

  private ArrayList<Node<K,V>> bucketArray;
  private int numberOfBuckets;
  private int currentSize;

  public HashMap() {
    this.bucketArray = new ArrayList<>();
    this.numberOfBuckets = DEFAULT_HASH_TABLE_SIZE;

    // Current size of hash map
    this.currentSize = 0;

    for (int i = 0; i < numberOfBuckets; i++) {
      bucketArray.add(null);
    }
  }
  
  public int getCurrentSize() { 
    return currentSize; 
  }

  public int getBucketSize() {
    return this.bucketArray.size();
  }
  
  private final int calcHashCode(K key) {
    return Objects.hashCode(key);
  }

  private int getBucketIndex(K key) {
    int hashCode = calcHashCode(key);
    int index = hashCode % numberOfBuckets;
    index = index < 0 ? index * -1 : index;
    return index;
  }

  public ArrayList getBucketArray() {
    return this.bucketArray;
  }

  public void add(K key, V value) {
    int bucketIndex = getBucketIndex(key);
    int hashCode = calcHashCode(key);
    Node<K,V> head = bucketArray.get(bucketIndex);

    while (head != null) {
      // if key already exists then update the value
      if (head.key.equals(key) && head.hashCode == hashCode) {
        head.value = value;
        return;
      }
      head = head.next;
    }

    this.currentSize++;
    head = bucketArray.get(bucketIndex);
    Node<K, V> newNode = new Node<K, V>(key, value, hashCode);
    newNode.next = head;
    bucketArray.set(bucketIndex, newNode);

    // increase hash table size if current size go beyond load factor
    if(getCurrentSize() / numberOfBuckets >= LOAD_FACTOR) {
      ArrayList<Node<K, V>> temp = bucketArray;
      bucketArray = new ArrayList<>();
      numberOfBuckets = 2 * numberOfBuckets;
      currentSize = 0;
      for (int i = 0; i < numberOfBuckets; i++) {
        bucketArray.add(null);
      }

      for (Node<K, V> headNode: temp) {
        while (headNode != null) {
          add(headNode.key, headNode.value);
          headNode = headNode.next;
        }
      }
    }
  }

  public V get(K key) {
    int bucketIndex = getBucketIndex(key);
    int hashCode = calcHashCode(key);
    Node<K, V> head = bucketArray.get(bucketIndex);

    while (head != null) {
      if (head.key.equals(key) && head.hashCode == hashCode) {
        return head.value;
      }
      head = head.next;
    }

    // if key is not found
    return null;
  }

  public V remove(K key) {
    int bucketIndex = getBucketIndex(key);
    int hashCode = calcHashCode(key);

    Node<K, V> head = bucketArray.get(bucketIndex);
    Node<K, V> prev = null;

    while (head != null) {
      if (head.key.equals(key) && head.hashCode == hashCode) {
        break;
      }

      prev = head;
      head = head.next;
    }

    if (head == null) {
      return null;
    }

    this.currentSize--;

    if (prev != null) {
      prev.next = head.next;
    } else {
      bucketArray.set(bucketIndex, head.next);
    }

    return head.value;
  }

}
