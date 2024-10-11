package com.ljx213101212.spring_boot.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Service;


class ThreadWorker1 extends Thread {
    public void run() {
        try {
            Thread.sleep(3000);//TIMED_WAITING
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class RunnableWorker2 implements Runnable {
    public void run() {
        try {
            Thread.sleep(3000);//TIMED_WAITING
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TaskRunner extends Thread {
    // volatile to ensure visibility to other threads.
    public volatile int task_count = 0;

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            task_count++;
        }
        System.out.println("Run Complete. Final count: " + task_count);
    }
}



@Service
public class ThreadServiceImpl implements ThreadService{

    @Override
    public ObjectNode getThreadProcessInfo() {
        // display current information about this process
        Runtime rt = Runtime.getRuntime();
        long usedKB = (rt.totalMemory() - rt.freeMemory()) / 1024 ;

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode info = mapper.createObjectNode();
        info.put("processId",  ProcessHandle.current().pid());
        info.put("threadCount", Thread.activeCount());
        info.put("memoryUsage", usedKB);

        return info;
    }

    @Override
    public ObjectNode getTimedWaitingThreadState() throws InterruptedException{

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode info = mapper.createObjectNode();
        ThreadWorker1 worker1 = new ThreadWorker1();
        info.put("NEW", worker1.getState().toString());
        worker1.start();
        info.put("RUNNABLE", worker1.getState().toString());
        Thread.sleep(500);
        info.put("TIMED_WAITING", worker1.getState().toString());
        worker1.join();
        info.put("TERMINATED", worker1.getState().toString());

        return info;
    }


    @Override
    public ObjectNode runMultiThread() throws InterruptedException {
        TaskRunner runner1 = new TaskRunner();
        TaskRunner runner2 = new TaskRunner();

        runner1.start();
        runner2.start();
        Thread.sleep(3000);

        runner1.interrupt();
        runner2.interrupt();

        runner1.join();
        runner2.join();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode info = mapper.createObjectNode();

        ArrayNode tasks = mapper.createArrayNode();
        ObjectNode task1Info = mapper.createObjectNode();
        task1Info.put("task1Count", runner1.task_count);
        ObjectNode task2Info = mapper.createObjectNode();
        task2Info.put("task2Count", runner2.task_count);
        tasks.add(task1Info);
        tasks.add(task2Info);
        info.set("runnerTask", tasks);
        return info;
    }

    @Override
    public ObjectNode runRunnableThread() throws InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode info = mapper.createObjectNode();

        Thread worker2 = new Thread(new RunnableWorker2());
        worker2.start();

        worker2.join();

        info.put("Result", "Runnable Thread Complete");
        return info;
    }

    @Override
    public ObjectNode runDaemonThread() {
        Thread worker1 = new ThreadWorker1();
        Thread worker2 = new Thread(new RunnableWorker2());

        //run and forget, and daemon thread will terminate once main thread terminate.
        worker1.setDaemon(true);
        worker2.setDaemon(true);

        worker1.start();
        worker2.start();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode info = mapper.createObjectNode();

        info.put("Result", "new daemon will be terminated after main thread ends");
        return info;
    }
}
