package walke.demolibrary.pinpu;

import androidx.annotation.Nullable;

import java.util.Comparator;
import java.util.PriorityQueue;

import walke.demolibrary.pinpu.queue.PrintTask;
import walke.demolibrary.pinpu.queue.Priority;
import walke.demolibrary.pinpu.queue.TaskQueue;

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
            //I  PriorityQueueTest test12 poll(原4), Student{id=1, name='吕布', age=19}, 3
            //I  PriorityQueueTest test12 peek, Student{id=2, name='王昭君', age=18}, 3
            //I  PriorityQueueTest test12 remove, Student{id=0, name='吕布0', age=20}, 3
            //I  PriorityQueueTest test12 peek, Student{id=2, name='王昭君', age=18}, 3
        }
    }

    public static void test12() {
        PriorityQueue<Student> queue = new PriorityQueue<>(32, new Comparator<Student>() {
            @Override
            public int compare(Student st1, Student st2) {
                return st1.getId() - st2.getId();
            }
        });

        queue.add(new Student(2, "王昭君", 18));
        queue.add(new Student(1, "吕布", 19));
        queue.add(new Student(4, "貂蝉", 16));
        Student zy = new Student(3, "赵云", 17);
        queue.add(zy);

        Student zs = new Student(2, "张三", 33);
        boolean contains = queue.contains(zs);
        System.out.println("PriorityQueueTest test12 contains, " + contains + ", " + queue.contains(new Student(20, "张三", 33)));
//        if (contains) queue.remove(zs); // 移除
        System.out.println("PriorityQueueTest test12 contains, " + queue.contains(zy));
        zy.setId(30);
        System.out.println("PriorityQueueTest test12 contains, " + queue.contains(zy));
        // I  PriorityQueueTest test12 contains, true, false
        // I  PriorityQueueTest test12 contains, true
        // I  PriorityQueueTest test12 contains, true

        System.out.println("PriorityQueueTest test12 poll(原4), " + queue.poll() + ", " + queue.size());
        System.out.println("PriorityQueueTest test12 peek, " + queue.peek() + ", " + queue.size());
        queue.add(new Student(0, "吕布0", 20));
        System.out.println("PriorityQueueTest test12 remove, " + queue.remove() + ", " + queue.size());
        System.out.println("PriorityQueueTest test12 peek, " + queue.peek() + ", " + queue.size());
        //  I  PriorityQueueTest poll(原4), Student{id=1, name='吕布', age=19}, 3
        //  I  PriorityQueueTest peek, Student{id=2, name='王昭君', age=18}, 3
        //  I  PriorityQueueTest remove, Student{id=0, name='吕布0', age=20}, 3
        //  I  PriorityQueueTest peek, Student{id=2, name='王昭君', age=18}, 3

        while (queue.peek() != null) {
            System.out.println("PriorityQueueTest test12 ----> while poll, " + queue.poll());
        }

        //   有 if (contains) queue.remove(zs); // 移除
        //I  PriorityQueueTest test12 contains, true, false
        //I  PriorityQueueTest test12 contains, true
        //I  PriorityQueueTest test12 contains, true
        //I  PriorityQueueTest test12 poll(原4), Student{id=1, name='吕布', age=19}, 2
        //I  PriorityQueueTest test12 peek, Student{id=4, name='貂蝉', age=16}, 2
        //I  PriorityQueueTest test12 remove, Student{id=0, name='吕布0', age=20}, 2
        //I  PriorityQueueTest test12 peek, Student{id=4, name='貂蝉', age=16}, 2
        //I  PriorityQueueTest test12 ----> while poll, Student{id=4, name='貂蝉', age=16}
        //I  PriorityQueueTest test12 ----> while poll, Student{id=30, name='赵云', age=17}

        //  // 注释：  if (contains) queue.remove(zs); // 移除
        //I  PriorityQueueTest poll(原4), Student{id=1, name='吕布', age=19}, 3
        //I  PriorityQueueTest peek, Student{id=2, name='王昭君', age=18}, 3
        //I  PriorityQueueTest remove, Student{id=0, name='吕布0', age=20}, 3
        //I  PriorityQueueTest peek, Student{id=2, name='王昭君', age=18}, 3
        //I  PriorityQueueTest test12 contains, true, false
        //I  PriorityQueueTest test12 contains, true
        //I  PriorityQueueTest test12 contains, true
        //I  PriorityQueueTest test12 poll(原4), Student{id=1, name='吕布', age=19}, 3
        //I  PriorityQueueTest test12 peek, Student{id=2, name='王昭君', age=18}, 3
        //I  PriorityQueueTest test12 remove, Student{id=0, name='吕布0', age=20}, 3
        //I  PriorityQueueTest test12 peek, Student{id=2, name='王昭君', age=18}, 3
        //I  PriorityQueueTest test12 ----> while poll, Student{id=2, name='王昭君', age=18}
        //I  PriorityQueueTest test12 ----> while poll, Student{id=4, name='貂蝉', age=16}
        //I  PriorityQueueTest test12 ----> while poll, Student{id=30, name='赵云', age=17}
    }


    public static void test2() {
        // 开一个窗口，这样会让优先级更加明显。
        TaskQueue taskQueue = new TaskQueue(1);
        taskQueue.start(); //  // 某机构开始工作。

        // 为了显示出优先级效果，我们预添加3个在前面堵着，让后面的优先级效果更明显。
        taskQueue.add(new PrintTask(111));
        taskQueue.add(new PrintTask(112));
        taskQueue.add(new PrintTask(122));

        for (int i = 0; i < 10; i++) { // 从第0个人开始。
            PrintTask task = new PrintTask(i);
            if (1 == i) {
                task.setPriority(Priority.LOW); // 让第2个进入的人最后办事。
            } else if (8 == i) {
                task.setPriority(Priority.HIGH); // 让第9个进入的人第二个办事。
            } else if (9 == i) {
                task.setPriority(Priority.Immediately); // 让第10（i=9）个进入的人第一个办事。
            }
            // ... 其它进入的人，按照进入顺序办事。
            taskQueue.add(task);
        }

        // I  PrintTask TaskQueue start
        // I  PrintTask TaskQueue start
        // I  PrintTask sequence 9
        // I  PrintTask sequence 8
        // I  PrintTask sequence 2
        // I  PrintTask sequence 7
        // I  PrintTask sequence 112
        // I  PrintTask sequence 5
        // I  PrintTask sequence 0
        // I  PrintTask sequence 3
        // I  PrintTask sequence 111
        // I  PrintTask sequence 6
        // I  PrintTask sequence 4
        // I  PrintTask sequence 122
        // I  PrintTask sequence 1
        taskQueue.start();
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
        public boolean equals(@Nullable Object obj) {
//            return super.equals(obj);
            if (this == obj) { //判断一下如果是同一个对象直接返回true，提高效率
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) { //如果传进来的对象为null或者二者为不同类，直接返回false
                return false;
            }
            //也可以以下方法：
//        if (obj == null || !(obj instanceof Rectangle)) { //如果传进来的对象为null或者二者为不同类，直接返回false
//            return false;
//        }
            Student rectangle = (Student) obj; //向下转型
            //比较长宽是否相等，注意：浮点数的比较不能简单地用==，会有精度的误差，用Math.abs或者Double.compare
            return this.id == rectangle.getId();
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
