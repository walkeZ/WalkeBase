package walke.demolibrary.pinpu;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            PriorityQueue<Student> queue = new PriorityQueue<>(new Comparator<Student>() {
                @Override
                public int compare(Student st1, Student st2) {
                    return st1.getId() - st2.getId();
                }
            });

            queue.add(new Student(2, "王昭君", 18));
            queue.add(new Student(1, "吕布", 19));
            queue.add(new Student(4, "貂蝉", 16));
            queue.add(new Student(3, "赵云", 17));

            System.out.println("PriorityQueueTest poll(原4), " + queue.poll() + ", " + queue.size());
            System.out.println("PriorityQueueTest peek, " + queue.peek() + ", " + queue.size());
            queue.add(new Student(0, "吕布0", 20));
            System.out.println("PriorityQueueTest remove, " + queue.remove() + ", " + queue.size());
            System.out.println("PriorityQueueTest peek, " + queue.peek() + ", " + queue.size());
            //   I  PriorityQueueTest poll(原4), Student{id=1, name='吕布', age=19}, 3
            //  I  PriorityQueueTest peek, Student{id=2, name='王昭君', age=18}, 3
            //  I  PriorityQueueTest remove, Student{id=0, name='吕布0', age=20}, 3
            //  I  PriorityQueueTest peek, Student{id=2, name='王昭君', age=18}, 3
        }
    }

    public static class Student {
        private int id;
        private String name;
        private int age;

        public Student(int i, String name, int age) {
            this.id = i;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
