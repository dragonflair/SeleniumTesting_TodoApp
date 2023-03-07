import java.util.List;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToDosApp {

    WebDriver driver;

    public void launchTodos() {
        System.getProperty("webdriver.chrome.driver", "C:/Users/userzero/Documents/GoogleChromeDriver");
        driver = new ChromeDriver();

        // Launch the test
        driver.get("https://todo-list-login.firebaseapp.com/");
    }

    public void login() {
        String mainWindow = driver.getWindowHandle();

        // Clicking onto Github button
        driver.findElement(By.xpath("//a[@class='btn btn-social btn-github']")).click();

        // Create a set to hold the different windows
        Set<String> allWindows = driver.getWindowHandles();

        for (String window: allWindows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);

                // Create a scanner so that the tester can enter the username/email and password without revealing it on the code
                Scanner scanner = new Scanner (System.in);
                // this part, I would need the accessor to enter their own github username or email in your IDE terminal
                System.out.println("Enter your username or email");
                String email = scanner.nextLine();

                // this part, I would need the accessor to enter their own github password in your IDE terminal
                System.out.println("Enter your password");
                String password = scanner.nextLine();

                scanner.close();

                // Information is inserted into the input field and click sign in button
                driver.findElement(By.xpath("//input[@id='login_field']")).sendKeys(email);;
                driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
                driver.findElement(By.xpath("//input[@value='Sign in']")).click();

                driver.close();
                break;
            } 
        }
        
        // go back to the main window
        driver.switchTo().window(mainWindow);
    }

    public void toDoLists() {
        List<String> randomLists = new ArrayList<>();
        
        // Creating 10 random lists
        randomLists.add("Morning exercise");
        randomLists.add("Meditation");
        randomLists.add("Prepare breakfast");
        randomLists.add("Read the news");
        randomLists.add("Go to work");
        randomLists.add("Check my email");
        randomLists.add("Attend meeting");
        randomLists.add("Meet my clients after lunch");
        randomLists.add("Prepare dinner");
        randomLists.add("Watch my favorite drama on Netflix");

        // add all the ten lists
        for (String list: randomLists) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement inputTodoItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-model='home.list']")));
            inputTodoItem.sendKeys(list);
            driver.findElement(By.xpath("//button[@ng-click='home.list && home.add()']")).click();
        }

        // click sign out
        driver.findElement(By.xpath("//button[@ng-click='home.signOut()']")).click();
    }

    public void relogin() {
        String mainWindow = driver.getWindowHandle();

        driver.findElement(By.xpath("//a[@class='btn btn-social btn-github']")).click();

        Set<String> allWindows = driver.getWindowHandles();

        // take note, if there is a new window that pop out to ask to authorize the API to access your github, you have to click Authorize within the 20 seconds timeframe
        for (String window: allWindows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                wait.until(ExpectedConditions.numberOfWindowsToBe(1));
            } 
        }

        driver.switchTo().window(mainWindow);
    }

    public void removeSomeLists() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // remove the items from the end and progressing toward number 6
        WebElement deleteItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@ng-click='home.delete($index)'])[10]")));
        deleteItem.click();
        driver.findElement(By.xpath("(//button[@ng-click='home.delete($index)'])[9]")).click();
        driver.findElement(By.xpath("(//button[@ng-click='home.delete($index)'])[8]")).click();
        driver.findElement(By.xpath("(//button[@ng-click='home.delete($index)'])[7]")).click();
        driver.findElement(By.xpath("(//button[@ng-click='home.delete($index)'])[6]")).click();
    }

    public void logout() {
        // click sign out button
        driver.findElement(By.xpath("//button[@ng-click='home.signOut()']")).click();

        driver.quit();
    }

    public static void main(String[] args) throws Exception {
        ToDosApp todos = new ToDosApp();
        todos.launchTodos();
        todos.login();     
        todos.toDoLists();
        todos.relogin(); 
        todos.removeSomeLists();
        todos.logout();
    }
}
