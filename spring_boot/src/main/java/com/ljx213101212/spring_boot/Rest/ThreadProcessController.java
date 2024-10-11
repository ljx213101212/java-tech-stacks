package com.ljx213101212.spring_boot.Rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.ljx213101212.spring_boot.Service.ThreadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadProcessController {

    private final ThreadService threadService;

    ThreadProcessController(ThreadService theThreadService) {
        threadService = theThreadService;
    }
    @GetMapping("/os/thread-info")
    public JsonNode getThreadInfo() {
        return threadService.getThreadProcessInfo();
    }

    @GetMapping("/os/thread-lifecycle")
    public JsonNode getThreadLifeCycle() throws InterruptedException {
        return threadService.getTimedWaitingThreadState();
    }

    @GetMapping("/os/run-multitask")
    public JsonNode runMultiTask() throws  InterruptedException {
        return threadService.runMultiThread();
    }

    @GetMapping("os/thread-runnable")
    public JsonNode runTreadRunnable() throws  InterruptedException {
        return threadService.runRunnableThread();
    }

    @GetMapping("os/thread-daemon")
    public JsonNode runDaemonThread() {
        return  threadService.runDaemonThread();
    }
}
