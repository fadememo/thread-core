package com.majoy.multi.thread.design_5.masterworker_2;

public class Task {

    private int id;
    private int count;

    public Task() {
    }

    public Task(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }

}
