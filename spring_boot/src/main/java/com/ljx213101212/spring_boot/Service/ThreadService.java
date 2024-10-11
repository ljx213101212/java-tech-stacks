package com.ljx213101212.spring_boot.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ThreadService {

    ObjectNode getThreadProcessInfo();
    ObjectNode getTimedWaitingThreadState() throws InterruptedException;
    ObjectNode runMultiThread() throws InterruptedException;
    ObjectNode runRunnableThread() throws  InterruptedException;
    ObjectNode runDaemonThread();
}
