package generic.problems.apitests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;


public class FilesAPITests {
    private String browser = null;
    private String endPoint = null;
    private String loginUser = null;
    private String loginPassword = null;
    private static String bUrl = null;

    public FilesAPITests() {
       browser = System.getenv("BROWSER");
       loginUser = System.getenv("LOGIN_USER");
       loginPassword = System.getenv("LOGIN_PASSWD");
       endPoint = System.getenv("ENDPOINT");
       bUrl = endPoint + "files/";
    }

    private boolean login(WebDriver driver) {
        boolean result = true;
        try {
            driver.get(endPoint);
            //  Add Login function
            WebElement unElem = driver.findElement(By.className("active-input"));
            System.out.println("Got the login element...");
            unElem.sendKeys(loginUser);
            unElem.sendKeys(Keys.ENTER);
            System.out.println("Sent the user name");
            //  wait for 5 seconds for userid to be entered
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            //  Handle password entry
            WebElement pwElem = driver.findElement(By.name("password"));
            System.out.println("Now sending the password");
            pwElem.sendKeys(loginPassword);
            pwElem.submit();	// Submit button
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            System.out.println("Successfully logged in...");        	
        } catch (Exception ex) {
        	System.out.println("Encountered exception during login [process: ");
        	ex.printStackTrace();
        	result = false;
        }
        return result;
    }

    private boolean logout(WebDriver driver) {
        boolean result = true;
        try {
            //WebElement unElem = driver.findElement(By.className("non-account-option"));
            //unElem.click();
            //WebElement loElem = driver.findElement(By.linkText("Log out"));
            //WebElement loElem = driver.findElement(By.partialLinkText("/logout/"));
            //loElem.click();

            driver.get(endPoint+"logout/");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            System.out.println("Successfully logged out...");
        } catch (Exception ex) {
        	System.out.println("Encountered exception during logout process: ");
        	ex.printStackTrace();
        	result = false;
        }
        return result;
    }

    public void uploadFile (String fileName) {
       WebDriver driver = null;
       String url = bUrl;
       System.out.println ("API url: '" + url + "'");

       //  Instantiate Firefox browser
       if (browser.equals("firefox")) {
          driver = new FirefoxDriver();
       } else if (browser.equals("chrome")) {
          //  Instantiate Chrome Driver
          ChromeOptions options = new ChromeOptions();
          //  Following options are added for the issue related to not interactable exception
          //options.addArguments("--window-size=1920,1080");
          //options.addArguments("--start-maximized");
          //options.addArguments("--headless");
          //
          options.addArguments("--lang=en");	    	
          options.addArguments("--incognito");
          String chromeDriver = "C:\\selenium-java-3.141.59\\chromedriver\\chromedriver.exe";
          System.setProperty("webdriver.chrome.driver", chromeDriver);
          driver = new ChromeDriver(options);
       }

       //driver.get(url);
       
       try {
           //  Add Login function
           if (login(driver)) {
              //  Wait for login to complete; Get 'Files' page
              driver.get(url);
              //driver.findElement(By.id("menuFiles")).click();
              driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
              //  Handle file upload function
              WebElement fiElem = driver.findElement(By.name("file"));
              // enter the file path onto the file-selection input field
              fiElem.sendKeys(fileName);
              driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); 
              
              System.out.println("File: '" + fileName + "' has been uploaded");

              //  Perform file delete function if set

              //  Perform logout function
              logout(driver);
           } else {
              System.out.println("Falied to login to main page. Check user credentials...");
           }
       } catch (Exception ex) {
    	   System.out.println("Exception Encountered:");
    	   ex.printStackTrace();
       } 

       //  Close browser
       driver.close();
    }
}
