import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    public static DateTimeFormatter validFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

    public static Task parse(String order, TaskList tl) {
        try {
            int numOfOrders = tl.getNumOfTasks();
            if (order.equals("bye")) {
                return new Exit();
            } else if (order.equals("list")) {
                return new List();
            } else if (order != null) {
                String[] command = order.split(" ", 2);
                if (command[0].equals("done")) {
                    if (command.length == 1) {
                        throw new NoTaskChosenException(command[0]);
                    } else if (!isInteger(command[1]) || Integer.parseInt(command[1]) > numOfOrders) {
                        throw new NoThisNumOfTaskException();
                    } else {
                        return new Done(Integer.parseInt(command[1]) - 1);
                    }
                } else if (command[0].equals("delete")) {
                    if (command.length == 1) {
                        throw new NoTaskChosenException(command[0]);
                    } else if (!isInteger(command[1]) || Integer.parseInt(command[1]) > numOfOrders) {
                        throw new NoThisNumOfTaskException();
                    } else {
                        return new Delete(Integer.parseInt(command[1]) - 1);
                    }
                } else {
                    if (command.length == 1) {
                        throw new NoDescriptionException(command[0]);
                    } else if (command[0].equals("deadline")) {
                        String[] splitAgain = command[1].split("/by ");
                        if (splitAgain.length == 1) {
                            throw new NoTimeException(command[0]);
                        }
                        return new Deadline(splitAgain[0], LocalDateTime.parse(splitAgain[1], validFormat), false);
                    } else if (command[0].equals("event")) {
                        String[] splitAgain = command[1].split("/at ");
                        if (splitAgain.length == 1) {
                            throw new NoTimeException(command[0]);
                        }
                        return new Event(splitAgain[0], LocalDateTime.parse(splitAgain[1], validFormat), false);

                    } else if (command[0].equals("todo")) {
                        return new Todo(command[1], false);
                    } else {
                        throw new NoSuchOrderException();
                    }
                }
            }
        } catch (NoDescriptionException | NoTimeException | NoSuchOrderException | NoTaskChosenException | NoThisNumOfTaskException e) {
            e.printStackTrace();
        }
        return null;
    }

}
