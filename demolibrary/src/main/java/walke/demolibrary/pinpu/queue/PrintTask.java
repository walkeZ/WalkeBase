package walke.demolibrary.pinpu.queue;

public class PrintTask extends BasicTask {
    public PrintTask(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public void run() {
        System.out.println("PrintTask sequence " + sequence);
    }
}
