public class Driver {
  public static void main(String[] args) {
    HashMap<Integer, String> studentList = new HashMap<>();
    studentList.add(22, "Aman");
    studentList.add(443, "Abhishek");
    studentList.add(12, "Rahul");
    studentList.add(40, "Priya");
    studentList.add(44, "newwww");
    
    System.out.println(studentList.getCurrentSize());
    System.out.println(studentList.get(44));
  }
}
