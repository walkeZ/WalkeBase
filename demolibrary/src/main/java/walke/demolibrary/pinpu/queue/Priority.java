package walke.demolibrary.pinpu.queue;

/**
 * 优先级,https://blog.csdn.net/yanzhenjie1003/article/details/71773950
 */
public enum Priority {
    LOW(1), // 最低。
    DEFAULT(2), // 默认级别。
    HIGH(3), // 高于默认级别。
    Immediately(4); // 立刻执行。

    private int level;

    Priority(int level) {
        this.level = level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}

// 理解参考
//public enum Priority {
//    1,
//            2,
//            3,
//            4
//}

