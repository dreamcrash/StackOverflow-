/** Original Question : https://stackoverflow.com/questions/65428163/
 *
 * How to combine Multiple Maps with multiple same keys and lists as values?
 *
 * I am new to Java and I am trying to merge multiple maps with
 * string as key and list as values to produce a new Map.
 *
 * public class Student {
 *     private String name;
 *     private String country;
 *
 *     //Setters and Getters
 * }
 * Now I have an util class to add students to the list based on their country.
 *
 * public class MapAdder {
 *     static Map<String, List<Student>> studentMap =
 *             new LinkedHashMap<String, List<Student>>();
 *
 *     public static void addToMap(String key, Student student) {
 *         studentMap.computeIfAbsent(key,
 *                 k -> new LinkedList<Student>()).add(student);
 *     }
 *
 *     public static Map<String, List<Student>> getStudentMap() {
 *         return studentMap;
 *     }
 *
 *     public static void clearStudentMap() {
 *         studentMap.clear();
 *     }
 * }
 * Main Method
 *
 * public static void main(String[] args) {
 *     Map<String, List<Student>> studentMap1;
 *     Map<String, List<Student>> studentMap2;
 *     Map<String, List<Student>> studentMap3;
 *
 *     MapAdder.addToMap("India", new Student("Mounish", "India"));
 *     MapAdder.addToMap("USA", new Student("Zen", "USA"));
 *     MapAdder.addToMap("India", new Student("Ram", "India"));
 *     MapAdder.addToMap("USA", new Student("Ronon", "USA"));
 *     MapAdder.addToMap("UK", new Student("Tony", "UK"));
 *
 *     studentMap1 = MapAdder.getStudentMap();
 *     MapAdder.clearStudentMap();
 *
 *     MapAdder.addToMap("India", new Student("Rivar", "India"));
 *     MapAdder.addToMap("UK", new Student("Loki", "UK"));
 *     MapAdder.addToMap("UK", new Student("Imran", "UK"));
 *     MapAdder.addToMap("USA", new Student("ryan", "USA"));
 *
 *     studentMap2 = MapAdder.getStudentMap();
 *     MapAdder.clearStudentMap();
 *
 *     Map<String, List<Student>> map3 = Stream.of(studentMap1, studentMap2)
 *             .flatMap(map -> map.entrySet().stream())
 *             .collect(Collectors.toMap(
 *                     Entry::getKey,
 *                     Entry::getValue
 *             ));
 * }
 * But when I try to merge both the maps I am getting empty map.
 * Actually, I need to have a map with three keys (India, UK, USA)
 * and their values that are list from multiple maps to be merged w.r.t keys.
 *
 */


package Map_git;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CombineMultipleMapsOfLists {
    public static class Student {
        private final String name;
        private final String country;

        public Student(String name, String country) {
            this.name = name;
            this.country = country;
        }

        @Override
        public String toString(){
            return "("+name+","+country+")";
        }
    }


    public static void addToMap(Map<String, List<Student>> studentMap, String key, Student student) {
        studentMap.computeIfAbsent(key, k -> new LinkedList<>()).add(student);
    }

    public static void main(String[] args) {
        Map<String, List<Student>> studentMap1 = new LinkedHashMap<>();
        Map<String, List<Student>> studentMap2 = new LinkedHashMap<>();

        CombineMultipleMapsOfLists.addToMap(studentMap1, "India", new Student("Mounish", "India"));
        CombineMultipleMapsOfLists.addToMap(studentMap1, "USA", new Student("Zen", "USA"));
        CombineMultipleMapsOfLists.addToMap(studentMap1, "India", new Student("Ram", "India"));
        CombineMultipleMapsOfLists.addToMap(studentMap1, "USA", new Student("Ronon", "USA"));
        CombineMultipleMapsOfLists.addToMap(studentMap1, "UK", new Student("Tony", "UK"));

        CombineMultipleMapsOfLists.addToMap(studentMap2, "India", new Student("Rivar", "India"));
        CombineMultipleMapsOfLists.addToMap(studentMap2, "UK", new Student("Loki", "UK"));
        CombineMultipleMapsOfLists.addToMap(studentMap2, "UK", new Student("Imran", "UK"));
        CombineMultipleMapsOfLists.addToMap(studentMap2, "USA", new Student("ryan", "USA"));

        Map<String, List<Student>> map3 = Stream.of(studentMap1, studentMap2)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new ArrayList<>(e.getValue()),
                        (left, right) -> { left.addAll(right); return left; }
                ));
        System.out.println(map3);
    }
}
