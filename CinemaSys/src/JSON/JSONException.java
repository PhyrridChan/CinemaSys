package JSON;

/**
 * you can find more method to be overridden ⬇︎
 *
 * @author zhangfan e-mail:phyrrid_chan@outlook.com
 * @see Exception
 * @see java.io.Serializable
 * @see Throwable
 */
public class JSONException extends Exception {
    public JSONException() {
        super();
    }

    /**
     * Constructs a new exception inherits from {@link Exception} with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public JSONException(String message) {
        super(message);
    }

    static class NoSuchJsonFileException extends Throwable {
        /**
         * The filePath is wrong
         * :the file is not exist or the file is not a JSON file
         *
         * @see java.nio.file.NoSuchFileException
         */
        public NoSuchJsonFileException() throws NoSuchFieldException {
            throw new NoSuchFieldException("the file is not exist or the file is not a JSON file");
        }
    }


}
