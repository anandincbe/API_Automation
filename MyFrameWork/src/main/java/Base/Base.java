package Base;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class Base {

	protected WebDriver driver;
	protected static ThreadLocal<RemoteWebDriver> webDriver = new ThreadLocal<RemoteWebDriver>();

	public static WebDriverWait wait;

	// private int IMPLICIT_WAIT_TIMEOUT = 500;

	private String platform = "";
	private String browserName = "";

	public SoftAssert sAssert;
	protected ConcurrentMap<String, String> testStatus;
	protected static ConcurrentMap<String, String> allTestStatus;

	public String getBrowserName() {
		return this.browserName;
	}

	@BeforeTest(alwaysRun = true)
	public void setUpTest(ITestContext context) {
		/*
		 * 
		 * //
		 * *****************************************************************************
		 * ************************************* String testName =
		 * context.getCurrentXmlTest().getName(); MDC.put("threadID", testName); _log =
		 * Logger.getLogger(CommonUtility.getCurrentClassAndMethodNameForLogger());
		 * EllieMaeLog.log(_log,
		 * "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		 * EllieMaeLog.log(_log,
		 * "*********************Before Test Start*********************");
		 * 
		 * allTestStatus = new ConcurrentHashMap<>();
		 * 
		 * startLoggingFromMain =
		 * context.getCurrentXmlTest().getParameter("startLoggingFromMain");
		 * 
		 * if (startLoggingFromMain == null) { System.setProperty("logfolder",
		 * logFolderName); System.setProperty("logfilename", "AutomationLog_" + testName
		 * + "_" + FrameworkConsts.TIMESTAMP);
		 * DOMConfigurator.configure("src/main/resources/log4j.xml"); }
		 * 
		 * if (StringUtils.isBlank(context.getCurrentXmlTest().getParameter(
		 * UPDATE_TEST_STATUS_TO_JIRA)) || !"yes"
		 * .equalsIgnoreCase(context.getCurrentXmlTest().getParameter(
		 * UPDATE_TEST_STATUS_TO_JIRA).trim())) { isUpdateTestStatusToJIRA = false; }
		 * else { isUpdateTestStatusToJIRA = true; }
		 * 
		 * if (StringUtils.isBlank(context.getCurrentXmlTest().getParameter(
		 * CHECK_ZAP_SECURITY)) ||
		 * !"yes".equalsIgnoreCase(context.getCurrentXmlTest().getParameter(
		 * CHECK_ZAP_SECURITY).trim())) { ischeckZAPSecurity = false;
		 * FrameworkConsts.checkZapSecurity = false; } else { ischeckZAPSecurity = true;
		 * FrameworkConsts.checkZapSecurity = true; }
		 * 
		 * if (StringUtils.isNotBlank(context.getCurrentXmlTest().getParameter(
		 * JIRA_PROJECT_NAME))) { jiraProjectName =
		 * context.getCurrentXmlTest().getParameter(JIRA_PROJECT_NAME).trim(); }
		 * 
		 * EllieMaeLog.log(_log, "JiraProjectName: '" + jiraProjectName + "'");
		 * 
		 * if (StringUtils.isNotBlank(context.getCurrentXmlTest().getParameter(
		 * TEST_CYCLE_VERSION))) { testCycleVersionName =
		 * context.getCurrentXmlTest().getParameter(TEST_CYCLE_VERSION).trim(); }
		 * 
		 * if (StringUtils.isNotBlank(context.getCurrentXmlTest().getParameter(
		 * TEST_CYCLE_NAME))) { jiraTestCycleName =
		 * context.getCurrentXmlTest().getParameter(TEST_CYCLE_NAME).trim(); }
		 * 
		 * EllieMaeLog.log(_log, "TestCycleVersion: '" + testCycleVersionName + "'");
		 * 
		 * if
		 * (StringUtils.isBlank(context.getCurrentXmlTest().getParameter(FrameworkConsts
		 * .RETRY_COUNT))) { FrameworkConsts.MAX_RETRY_COUNT = 0; } else { try {
		 * FrameworkConsts.MAX_RETRY_COUNT = new Integer(
		 * context.getCurrentXmlTest().getParameter(FrameworkConsts.RETRY_COUNT).trim())
		 * ; } catch (NumberFormatException exNF) { EllieMaeLog.log(_log,
		 * "Exception occurred while setting Retry Count value. Exception:" +
		 * exNF.getMessage()); FrameworkConsts.MAX_RETRY_COUNT = 0; }1a }
		 * EllieMaeLog.log(_log,
		 * "*********************Before Test End*********************");
		 */}

	/**
	 * <b>Name:</b> setUpClass <b>Description:</b> Create driver before starting
	 * each class
	 * 
	 * <b>Author:</b> Supreet Singh
	 * 
	 * @throws Exception
	 */
	@BeforeClass(alwaysRun = true)
	public void setUpClass(ITestContext context) throws Exception {
		platform = (context.getCurrentXmlTest().getParameter("platform") == null ? ""
				: context.getCurrentXmlTest().getParameter("platform"));

		browserName = (context.getCurrentXmlTest().getParameter("browserName") == null ? ""
				: context.getCurrentXmlTest().getParameter("browserName"));

		if (platform.equalsIgnoreCase("Web")) {
			System.out.println("Creating driver for Web browser");
			driver = createDriver(browserName);

			System.out.println("TestNG TestName: " + context.getCurrentXmlTest().getName());
			System.out.println("Browser Name: " + browserName);
		} else if (platform.equalsIgnoreCase("API")) {
			System.out.println("Running API test");
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void setUpMethod() {

		// Advanced
		// Report****************************************************************************************
		try {
			// System.out.println("Class Name --> " + className + "_" + testMethodName);
			// FrameworkConsts.test.putIfAbsent(className + "_" + testMethodName,
			// FrameworkConsts.extent.createTest(className + "_" + testMethodName));
			// EllieMaeLog.setLogger(FrameworkConsts.test.get(className + "_" +
			// testMethodName));
			// FrameworkConsts.test.get(className + "_" +
			// testMethodName).assignAuthor("ATF------->");
			// *********************************************************************************************************

		} catch (Exception e) {
			e.printStackTrace();
		}

		FrameworkConsts.TESTCASENAME = (testMethodName);

		testStatus = new ConcurrentHashMap<String, String>();
		sAssert = new SoftAssert();

	}

	/**
	 * <b>Name:</b> tearDownMethod <b>Description:</b> Take Screenshots on Failure
	 * from UI automation after each test method <b>Author:</b> Supreet Singh
	 * <b>Author:</b> Vinayak Chalse
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDownMethod(ITestResult testResult, ITestContext context) {
		/*
		 * Set<Map.Entry<String, String>> testStatusEntries = null;
		 * 
		 * 
		 * try { String currentValue;
		 * 
		 * if (null != testStatus && !testStatus.isEmpty()) { testStatusEntries =
		 * testStatus.entrySet(); } else { if (null == testStatus) { testStatus = new
		 * ConcurrentHashMap<String, String>(); }
		 * 
		 * testStatusEntries = testStatus.entrySet(); }
		 * 
		 * 
		 * } finally { if (null != testStatus) { testStatus.clear(); testStatus = null;
		 * } }
		 * 
		 * 
		 */}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {

		try {
			this.driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.driver.quit();
			} catch (Exception ex) {
				System.out.println("issue with driver");
			}

		}
	}

	/**
	 * <b>Name:</b> tearDownTest <b>Description:</b> Method to perform tasks prior
	 * to tear down of Test level. <b>Author:</b> Supreet Singh <b>Author:</b>
	 * Vinayak Chalse
	 */
	@AfterTest(alwaysRun = true)
	public void tearDownTest() {
		try {

		} finally

		{
		}
	}

	public WebDriver createDriver(String browser) throws Exception {
		try {
			browser = browser.trim();
			if (browserName.isEmpty())
				browserName = browser;

			System.out.println("Creating driver for Browser: " + " " + browser);

			switch (browser.toLowerCase()) {
			case "firefox":
				System.setProperty("webdriver.gecko.driver", FrameworkConsts.BROWSEREXE_PATH + "/geckodriver.exe");

				FirefoxProfile fp = new FirefoxProfile();
				fp.setPreference("network.proxy.type", 0);
				FirefoxOptions capf = new FirefoxOptions();
				capf.setProfile(fp);
				capf.setLogLevel(Level.OFF);
				driver = new FirefoxDriver(capf);
				driver.navigate().to("http://www.google.com");
				break;
			case "ie":
				System.setProperty("webdriver.ie.driver", FrameworkConsts.BROWSEREXE_PATH + "/IEDriverServer.exe");
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();

				caps.setCapability("ignoreZoomSetting", true);
				caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

				// caps.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, false);

				driver = new InternetExplorerDriver(caps);
				break;
			case "chrome":
				System.setProperty("webdriver.chrome.driver", FrameworkConsts.BROWSEREXE_PATH + "/chromedriver.exe");

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				LoggingPreferences loggingprefs = new LoggingPreferences();
				loggingprefs.enable(LogType.PERFORMANCE, Level.ALL);

				ChromeOptions optionsChrome = new ChromeOptions();
				optionsChrome.addArguments("--disable-popup-blocking");
				optionsChrome.addArguments("--disable-extensions");
				capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
				capabilities.setCapability(ChromeOptions.CAPABILITY, optionsChrome);

				driver = new ChromeDriver(capabilities);
				EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
				eventDriver = new EventFiringWebDriver(driver);
				driver = eventDriver;
				break;

			case "safari":
				break;
			case "edge":
				break;
			default:
				throw new Exception(
						"The provided browser name: '" + browser + "' is not supported or should not be empty.");
			}

			try {
				// SetScriptTimeout is the asysn script timeout
				driver.manage().timeouts().setScriptTimeout(FrameworkConsts.TIMEOUTINMILLISECONDS,
						TimeUnit.MILLISECONDS);
				driver.manage().window().maximize();
				wait = new WebDriverWait(driver, FrameworkConsts.WEBDRIVER_WAIT_TIMEOUT);
				FrameworkConsts.CURRENTBROWSERNAME = browser.toLowerCase();
			} catch (Exception e) {
				throw new Exception(
						"Exception in createDriver: Incorrect or incomplete value in DriverInfo sheet in TestNG_XMLData excel file");
			}
		} finally {
			System.out.println("############ WebDriver Set End ############");
		}
		return driver;
	}

	/**
	 * <b>Name:</b> getWebDriver <b>Description:</b> This method will return the
	 * webdriver.
	 * 
	 * @return WebDriver
	 *//*
		 * public WebDriver getWebDriver() { EllieMaeLog.log(_log, "WebDriver: " +
		 * webDriver.get()); return webDriver.get(); }
		 */

	/**
	 * <b>Name:</b> getWebDriver <b>Description:</b> This method will return the
	 * session id in the form of string.
	 * 
	 * @return String
	 */
	/*
	 * public String getSessionId() { return sessionId.get(); }
	 */
	// **********************These parameters are for
	// @DataProvider************************
	public String className;
	public String dataFileName;
	private Class<?> fullClassName;
	public String testMethodName;
	public String resourcesFolder;

	/**
	 * <b>Name: EllieMaeBase</b> <b>Description:</b> This is constructor of this
	 * class to instantiate the class name for data provider.
	 */
	public Base() {
		fullClassName = this.getClass();
		className = getClassName(fullClassName);
		dataFileName = className + "_Data";
		testMethodName = "";
	}

	/**
	 * <b>Name:</b> getClassName <b>Description:</b> Get class name string of a
	 * specific class object
	 * 
	 * @param className - class object
	 * @return class name string
	 */
	public static String getClassName(Class<?> className) {
		String fqClassName = className.getName();
		int charIndex;
		charIndex = fqClassName.lastIndexOf('.') + 1;
		if (charIndex > 0) {
			fqClassName = fqClassName.substring(charIndex);
		}
		return fqClassName;
	}

	/**
	 * <b>Name:</b> getResoucesFolderName <b>Description:</b> Get Resouces
	 * FolderName string
	 * 
	 * @param className - class object
	 * @return class name string
	 */
	public static String getResoucesFolderName(Class<?> className) {
		String resourcesFolderName = "";
		int charIndex;
		charIndex = className.getName().split("\\.").length - 2;
		resourcesFolderName = className.getName().split("\\.")[charIndex];
		return resourcesFolderName;
	}

	@DataProvider(name = "get-test-data-method")
	public Object[][] getTestDataMethod(Method testMethod) throws Exception {

		return getDataProvider(dataFileName, className + "_" + testMethod.getName(), false);
	}

	/**
	 * <b>Name:</b> getDataProvider <b>Description:</b> Gets data from the excel
	 * sheet in form of hash map and converts it into 2 dimensional array.
	 * 
	 * @param dataFileName     The data file name.
	 * @param className        The test class name.
	 * @param isGetAllTestData The flag to indicate whether only testData or all of
	 *                         the test related data (e.g. both testData and
	 *                         testCaseData) is to be returned for the requester.
	 * @return Object[][] two dimension array of data
	 * @throws Exception The Exception instance.
	 */
	public Object[][] getDataProvider(String dataFileName, String className, boolean isGetAllTestData)
			throws Exception {
		String dataFilePath = null, strTestDataQuery;
		ArrayList<HashMap<String, String>> testDataSheet = null;
		Map<String, String> testData = null;
		HashMap<String, HashMap<String, String>> testCaseData;
		File f = new File("");
		dataFilePath = f.getAbsolutePath().replace("\\", File.separator);

		dataFilePath = dataFilePath + File.separator + "src" + File.separator + "test" + File.separator + "java"
				+ File.separator + "TestData" + File.separator + dataFileName	+ ".xlsx";

		ExcelParser ep = new ExcelParser(dataFilePath, className);
		testDataSheet = ep.readTestData();

		int numberTestCases = testDataSheet.size();
		Object[][] result = new Object[numberTestCases][];

		for (int i = 0; i < numberTestCases; i++) {
			dataFilePath = null;
			strTestDataQuery = null;
			testCaseData = null;

			try {
				testData = testDataSheet.get(i);

				if (isGetAllTestData) {
					dataFilePath = getRelativeFilePath("data", dataFileName + ".xlsx");
					strTestDataQuery = "Select * from " + testData.get("TestDataSheet") + " where Test_Case_Name = '"
							+ testData.get("Test_Case_Name") + "' order by SequenceID";

					testCaseData = getAdditionalDataInMap(dataFilePath, strTestDataQuery);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (isGetAllTestData) {
					result[i] = new Object[] { testData, testCaseData };
				} else {
					result[i] = new Object[] { testData };
				}
			}
		}

		return result;
	}

	public static String getRelativeFilePath(String folder, String fileName) {
		File f = new File("");
		String relativeFilePath = f.getAbsolutePath();
		if (folder.equalsIgnoreCase("env")) {
			relativeFilePath = relativeFilePath + File.separator + "src" + File.separator + "main" + File.separator
					+ "resources" + File.separator + "com" + File.separator + "elliemae" + File.separator + folder;
			if (new File(relativeFilePath, fileName).exists()) {
				relativeFilePath = relativeFilePath + File.separator + fileName;
			} else {
				// relativeFilePath = getTempFilePath(fileName).toString();
				System.out.println(relativeFilePath);
			}
		}

		else if (folder.equalsIgnoreCase("config")) {
			relativeFilePath = relativeFilePath + File.separator + "src" + File.separator + "test" + File.separator
					+ "resources" + File.separator + "com" + File.separator + "elliemae";
			relativeFilePath = relativeFilePath + File.separator + folder + File.separator + fileName;
		} else {
			relativeFilePath = relativeFilePath + File.separator + "src" + File.separator + "test" + File.separator
					+ "resources" + File.separator + "com" + File.separator + "elliemae";
			relativeFilePath = relativeFilePath + File.separator + FrameworkConsts.tlResourceFolder.get()
					+ File.separator + folder + File.separator + fileName;
		}

		return relativeFilePath;
	}

	public static HashMap<String, HashMap<String, String>> getAdditionalDataInMap(String additionalDataFilePath,
			String strTestDataQuery) throws FilloException {

		Recordset recordSetTestCaseData = getRecordSetUsingFillo(additionalDataFilePath, strTestDataQuery);
		HashMap<String, String> testCaseData = new HashMap<>();

		Object[] list = new Object[recordSetTestCaseData.getFieldNames().size()];

		list = recordSetTestCaseData.getFieldNames().toArray();

		HashMap<String, HashMap<String, String>> allData = new HashMap<String, HashMap<String, String>>();

		while (recordSetTestCaseData.next()) {

			for (Object obj : list) {
				String key = obj.toString();
				testCaseData.put(key, recordSetTestCaseData.getField(key));
			}
			allData.put(testCaseData.get("SequenceID"), new HashMap<>(testCaseData));
			testCaseData.clear();
		}
		return allData;
	}

	public static Recordset getRecordSetUsingFillo(String excelFilePath, String query) {
		Fillo fillo = new Fillo();
		Connection connection = null;
		Recordset recordSet = null;

		try {
			try {
				connection = fillo.getConnection(excelFilePath);
			} catch (FilloException e) {
				StringWriter stackTrace = new StringWriter();
				e.printStackTrace(new PrintWriter(stackTrace));
			}

			try {
				recordSet = connection.executeQuery(query);
			} catch (FilloException e) {
				StringWriter stackTrace = new StringWriter();
				e.printStackTrace(new PrintWriter(stackTrace));
			}
		} finally {
			if (null != connection) {
				connection.close();
			}
		}
		return recordSet;
	}
}