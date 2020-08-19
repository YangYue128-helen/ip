public class NoDescriptionException extends Exception{
    NoDescriptionException(String task) {
        super(String.format("  ☹ OOPS!!! The description of a %s cannot be empty.", task));
    }
}
