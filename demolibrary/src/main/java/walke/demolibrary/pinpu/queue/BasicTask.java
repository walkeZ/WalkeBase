package walke.demolibrary.pinpu.queue;

public abstract class BasicTask implements ITask {

    // 默认优先级。
    private Priority priority = Priority.DEFAULT;
    protected int sequence;

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public int getSequence() {
        return sequence;
    }

    // 做优先级比较。
    @Override
    public int compareTo(ITask another) {
        final Priority me = this.getPriority();
        final Priority it = another.getPriority();
        int i = it.getLevel() - me.getLevel();
        System.out.println("PrintTask BasicTask " + i + ", " + it.getLevel() + ", me " + me.getLevel());
        return i;
    }
}
