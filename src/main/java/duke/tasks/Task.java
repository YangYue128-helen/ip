package duke.tasks;

import duke.ui.Ui;
import duke.tool.Storage;
import duke.tool.TaskList;

/**
 * The class to Represent a task.
 */
public class Task {
    public boolean isExit;
    protected String name;
    protected boolean isDone;

    /**
     * Constructs a task.
     * @param name
     * @param isDone
     */
    public Task(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
        this.isExit = false;
    }

    /**
     * Get the status icon of the status of the task.
     * @return
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Mark the task as done.
     * @return
     */
    public Task markDone() {
        this.isDone = true;
        return this;
    }

    /**
     * Get the description of the task.
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * String representation of the task.
     * @return
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.name;
    }

    /**
     * Formatted String representation of the task.
     * @return
     */
    public String fileFormattedString() {
        String doneOrNot = isDone ? "1" : "0";
        return "N | " + doneOrNot + " | " + this.name;
    }

    /**
     * Executes the task.
     * @param tasklist
     * @param ui
     * @param storage
     * @return
     */
    public String execute(TaskList tasklist, Ui ui, Storage storage) {
        return null;
    };
}
