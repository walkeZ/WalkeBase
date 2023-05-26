package walke.demolibrary.pinpu.queue;

import androidx.arch.core.executor.TaskExecutor;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

// 某机构。
public class TaskQueue {

    private AtomicInteger mAtomicInteger = new AtomicInteger();
    // 某机构排的队，队里面是办事的人。
    private BlockingQueue<ITask> mTaskQueue;
    // 好多窗口。
    private TaskExecutor[] mTaskExecutors;

    // 在开发者new队列的时候，要指定窗口数量。
    public TaskQueue(int size) {
        mTaskQueue = new PriorityBlockingQueue<ITask>(32, new Comparator<ITask>() {
            @Override
            public int compare(ITask o1, ITask o2) {
                return o2.getPriority().getLevel() - o1.getPriority().getLevel();
            }
        });
        mTaskExecutors = new TaskExecutor[size];

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });
        }
    }

    public void start() {
        System.out.println("PrintTask TaskQueue start");
        while (mTaskQueue.peek() != null) {
            ITask poll = mTaskQueue.poll();
            poll.run();
        }
    }

    public void stop() {
        System.out.println("TaskQueue stop");
    }

    public <T extends ITask> int add(T task) {
        if (!mTaskQueue.contains(task)) {
//            task.setSequence(mAtomicInteger.incrementAndGet()); // 注意这行。
            mTaskQueue.add(task);
        }
        return mTaskQueue.size();
    }
}