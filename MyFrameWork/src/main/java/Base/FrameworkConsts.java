package Base;

public class FrameworkConsts {

	public static    String TESTCASENAME = null;
	public static String CURRENTBROWSERNAME;
	public static final String BROWSEREXE_PATH = "./src/main/java/resources";
	public static final long WEBDRIVER_WAIT_TIMEOUT = 0;
	public static final long TIMEOUTINMILLISECONDS = 5000;
	public static ThreadLocal<String> tlResourceFolder = new ThreadLocal<String>();
}
