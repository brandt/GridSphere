import java.util.*;

public class test {

private static Hashtable hash = new Hashtable();

public test() {
List l1 = new ArrayList();
List l2 = new ArrayList();
l1.add("Hello");
l2.add("Goodbye");
hash.put("h", l1);
hash.put("g", l2);
}

public static void main(String[] args) {
test t = new test();
List l1 = (ArrayList)hash.get("h");
l1.add("Bonjour");

System.out.println((List)hash.get("h"));
}

}

