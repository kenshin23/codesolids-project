package codesolids.gui.arena;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.TaskQueueHandle;

public class TimedServerPush implements Runnable {

  private ApplicationInstance app;
  private TaskQueueHandle taskQueueHandle;
  private Runnable clientTasks;
  private long delay;
  private boolean running;

  public TimedServerPush(long delay, ApplicationInstance app, TaskQueueHandle taskQueueHandle, Runnable clientTasks) {
    this.app = app;
    this.taskQueueHandle = taskQueueHandle;
    this.clientTasks = clientTasks;
    this.delay = delay;
  }

  public void beg() {
    Thread thread = new Thread(this);
    running = true;
    thread.start();
  }

  public synchronized void end() {
    running = false;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      return;
    }

    synchronized (this) {
      if (running) {
        ThreadTask threadTask = new ThreadTask(this, clientTasks);
        app.enqueueTask(taskQueueHandle, threadTask);
      }
    }
  }

  private static class ThreadTask implements Runnable {

    private Runnable clientTasks;
    private TimedServerPush timedServerPush;

    public ThreadTask(TimedServerPush timedServerPush, Runnable clientTasks) {
      this.timedServerPush = timedServerPush;
      this.clientTasks = clientTasks;
    }

    @Override
    public void run() {
      clientTasks.run();

      Thread thread = new Thread(timedServerPush);
      thread.start();
    }
  }
}
