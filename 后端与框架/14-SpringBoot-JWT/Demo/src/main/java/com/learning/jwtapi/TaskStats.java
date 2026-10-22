package com.learning.jwtapi;

public class TaskStats {
    private long total;
    private long completed;
    private long inProgress;
    private long notStarted;

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public long getCompleted() { return completed; }
    public void setCompleted(long completed) { this.completed = completed; }
    public long getInProgress() { return inProgress; }
    public void setInProgress(long inProgress) { this.inProgress = inProgress; }
    public long getNotStarted() { return notStarted; }
    public void setNotStarted(long notStarted) { this.notStarted = notStarted; }
}
