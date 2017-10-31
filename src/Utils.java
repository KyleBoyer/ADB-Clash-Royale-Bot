import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

public class Utils {
    private static final String[] WIN_RUNTIME = { "cmd.exe", "/C" };
    private static final String[] OS_LINUX_RUNTIME = { "/bin/bash", "-l", "-c" };

    private Utils() {
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    public static String run(String... command) {
        String[] allCommand = null;
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                allCommand = concat(WIN_RUNTIME, command);
            } else {
                allCommand = concat(OS_LINUX_RUNTIME, command);
            }
            ProcessBuilder pb = new ProcessBuilder(allCommand);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor();
            return IOUtils.toString(p.getInputStream(), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}