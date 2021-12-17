package src;

class Node<K, V> {
  K key;
  V value;
  int hashCode;
  Node<K, V> next;

  public Node(K key, V value, int hashCode) {
    this.key = key;
    this.value = value;
    this.hashCode = hashCode;
  }

  public K getKey() {
    return this.key;
  }

  public V getValue() {
    return this.value;
  }

  public Node<K,V> getNext() {
    return this.next;
  }
}