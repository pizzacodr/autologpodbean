package com.github.pizzacodr.autologpodbean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class Podbean {
	
	private ChromeDriver driver;
	
	Podbean(String chromeDriveLocation, boolean isHeadless) {
		System.setProperty("webdriver.chrome.driver", chromeDriveLocation);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("use-fake-ui-for-media-stream");
		options.addArguments("use-fake-device-for-media-stream");
			
		if (isHeadless) {
			options.addArguments("--headless");
		}

		options.setLogLevel(ChromeDriverLogLevel.ALL);
		driver = new ChromeDriver(options);
	}
	
	public void loginPage(String username, String password) {
		driver.get("https://www.podbean.com/login");
		new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.elementToBeClickable(By.id("LoginForm_username")));

		driver.findElement(By.id("LoginForm_username")).click();
		driver.findElement(By.id("LoginForm_username")).sendKeys(username);
		driver.findElement(By.id("LoginForm_password")).click();
		driver.findElement(By.id("LoginForm_password")).sendKeys(password);
		driver.findElement(By.name("yt0")).click();
	}
	
	public void selectLiveStreamFromProfile() {
		new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.elementToBeClickable(By.id("profile-info")));
		driver.findElement(By.id("profile-info")).click();

		new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.elementToBeClickable(By.linkText("Live Stream")));
		driver.findElement(By.linkText("Live Stream")).click();
	}
	
	public void startNewLiveShow() {
		new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.elementToBeClickable(By.id("new-live-show-btn")));
		driver.findElement(By.id("new-live-show-btn")).click();
	}
	
	public void newLiveShowConfig(String streamFullTitle, String streamPartialTitleRightAfterDayOfTheWeek) {
		new WebDriverWait(driver, Duration.ofSeconds(20))
			.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".claimed-content")));
		driver.findElement(By.cssSelector(".claimed-content")).click();
		
		StringBuilder title = new StringBuilder();
		if (streamFullTitle == null || streamFullTitle.isEmpty() || streamFullTitle.trim().isEmpty()) {
			
			Date date = new Date();
			Locale locale = new Locale("en","US");
			
			DateFormat formatter = new SimpleDateFormat("EEEE", locale);
		    title.append(formatter.format(date));
		    
		    if (streamPartialTitleRightAfterDayOfTheWeek != null || !streamPartialTitleRightAfterDayOfTheWeek.isEmpty() || 
		    		!streamPartialTitleRightAfterDayOfTheWeek.trim().isEmpty()) {
		    	title.append(streamPartialTitleRightAfterDayOfTheWeek);
		    }
		    
		} else {
			
			title.append(streamFullTitle);
		}
		
		driver.findElement(By.id("LiveTask_title")).clear();
		driver.findElement(By.id("LiveTask_title")).sendKeys(title);
		
		new WebDriverWait(driver, Duration.ofSeconds(20))
	    	.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
		driver.findElement(By.id("submit")).submit();
	}
	
	public void inviteCoHosts() {
		new WebDriverWait(driver, Duration.ofSeconds(20))
	    	.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("iframe")));
	    
	    driver.switchTo().frame(driver.findElements(By.tagName("iframe")).get(0));
	    
	    new WebDriverWait(driver, Duration.ofSeconds(20))
	    	.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".done")));
	    driver.findElement(By.cssSelector(".done")).click();
	}
	
	public void onAir(Long streamingTimeInMinutes) throws InterruptedException {
		driver.switchTo().defaultContent();
	    
	    new WebDriverWait(driver, Duration.ofSeconds(20))
	    	.until(ExpectedConditions.visibilityOfElementLocated(By.className("el-card__body")));
	    
	    new WebDriverWait(driver, Duration.ofSeconds(20))
	    	.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/div[1]")));

	    driver.switchTo().defaultContent();
	    driver.findElement(By.cssSelector("#start-live-btn > span")).click();
	    driver.findElement(By.cssSelector(".bootstrap-show > span")).click();
	    driver.findElement(By.cssSelector(".el-message-box__btns > .el-button--primary")).click();

	    new WebDriverWait(driver, Duration.ofSeconds(20))
    	.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/main/div[1]/div[3]/div/div[2]")));
	    TimeUnit.SECONDS.sleep(1);
	    driver.findElement(By.xpath("/html/body/section/main/div[1]/div[3]/div/div[2]")).click(); //call in

	    new WebDriverWait(driver, Duration.ofSeconds(20))
	    	.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"audio-effects\"]/div/div[1]/div/div/span")));
	    driver.findElement(By.xpath("//*[@id=\"audio-effects\"]/div/div[1]/div/div/span")).click(); //call in switch
	     
	    TimeUnit.SECONDS.sleep(2);
	    
	    driver.findElement(By.cssSelector("#start-live-btn > span")).click();
	    
	    TimeUnit.MINUTES.sleep(streamingTimeInMinutes);
	}
	
	public void offAir() throws InterruptedException {
	    
	    driver.findElement(By.xpath("//*[@id=\"exit-live-btn\"]")).click(); //end it
	    
	    TimeUnit.SECONDS.sleep(1);
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/button[2]")).click(); //yes
	    
	    TimeUnit.SECONDS.sleep(1);
	    driver.findElement(By.xpath("/html/body/section/main/div[9]/div/div[3]/span/div/button[2]")).click(); //exit button
	    
	    TimeUnit.SECONDS.sleep(1);
	    driver.close();
	}
}
