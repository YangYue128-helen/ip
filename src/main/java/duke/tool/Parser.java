package duke.tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import duke.exceptions.NoDescriptionException;
import duke.exceptions.NoSuchOrderException;
import duke.exceptions.NoTaskChosenException;
import duke.exceptions.NoThisNumOfTaskException;
import duke.exceptions.NoTimeException;
import duke.tasks.Deadline;
import duke.tasks.Delete;
import duke.tasks.Done;
import duke.tasks.Event;
import duke.tasks.Exit;
import duke.tasks.Find;
import duke.tasks.List;
import duke.tasks.Task;
import duke.tasks.Todo;

/**
 * The class deals with making sense of the user command.
 */
public class Parser {
    public static DateTimeFormatter VALID_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Check whether the characters in the string represents an integer.
     * @param s
     * @return
     */
    public static boolean isInteger(String s) {
        if (s == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Parse the command from user and return the corresponding task.
     * @param order the order from user.
     * @param tl
     * @return the task which can be understood by Duke.
     */
    public static Task parse(String order, TaskList tl) {
        try {
            int numOfOrders = tl.getNumOfTasks();
            if (order != null) {
                String[] command = order.split(" ", 2);
                if (ValidInput.getCmdType(command[0]).name().equals("EXIT")) {
                    return new Exit();
                } else if (ValidInput.getCmdType(command[0]).name().equals("LIST")) {
                    return new List();
                }else if (ValidInput.getCmdType(command[0]).name().equals("DONE")) {
                    if (command.length == 1) {
                        throw new NoTaskChosenException(command[0]);
                    } else if (!isInteger(command[1])
                            || Integer.parseInt(command[1]) > numOfOrders) {
                        throw new NoThisNumOfTaskException();
                    } else {
                        return new Done(Integer.parseInt(command[1]) - 1);
                    }
                } else if (ValidInput.getCmdType(command[0]).name().equals("DELETE")) {
                    if (command.length == 1) {
                        throw new NoTaskChosenException(command[0]);
                    } else if (!isInteger(command[1])
                            || Integer.parseInt(command[1]) > numOfOrders) {
                        throw new NoThisNumOfTaskException();
                    } else {
                        return new Delete((Integer.parseInt(command[1]) - 1));
                    }
                } else if (ValidInput.getCmdType(command[0]).name().equals("FIND")) {
                    return new Find(command[1]);
                } else {
                    if (command.length == 1) {
                        throw new NoDescriptionException(command[0]);
                    } else if (ValidInput.getCmdType(command[0]).name().equals("DEADLINE")) {
                        String[] splitAgain = command[1].split("/by ");
                        if (splitAgain.length == 1) {
                            throw new NoTimeException(command[0]);
                        }
                        assert splitAgain.length == 2;
                        return new Deadline(splitAgain[0],
                                LocalDateTime.parse(splitAgain[1], VALID_FORMAT), false);
                    } else if (ValidInput.getCmdType(command[0]).name().equals("EVENT")) {
                        String[] splitAgain = command[1].split("/at ");
                        if (splitAgain.length == 1) {
                            throw new NoTimeException(command[0]);
                        }
                        assert splitAgain.length == 2;
                        return new Event(splitAgain[0],
                                LocalDateTime.parse(splitAgain[1], VALID_FORMAT), false);
                    } else if (ValidInput.getCmdType(command[0]).name().equals("TODO")) {
                        return new Todo(command[1], false);
                    } else {
                        throw new NoSuchOrderException();
                    }
                }
            }
        } catch (NoDescriptionException | NoTimeException | NoSuchOrderException
                | NoTaskChosenException | NoThisNumOfTaskException e) {
            e.printStackTrace();
        }
        return null;
    }

}
