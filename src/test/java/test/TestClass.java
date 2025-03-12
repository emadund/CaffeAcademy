package test;

import base.BaseClass;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page.HomePage;
import page.Login;

public class TestClass extends BaseClass {

    private HomePage homePage;
    private Login login;
    private  String [] emailsRegistered = {"petarpetrovic","admin","mirkamiric","bojana"};
    private String [] passwords={"petar123","admin","mirka123","bojana"};
    private final String  emailSufix="@gmail.com";

    @BeforeTest
    public void beforeAllTests() {
        homePage= new HomePage();
        login = new Login();
    }

    @Test

    public void successfullLogin1 () {
        for (int i=0;i<4;i++) {
            if (i==1) continue;
            homePage.clickOnLogin();
            login.fillEmail(emailsRegistered[i]+emailSufix)
                    .fillPassword(passwords[i])
                    .clickOnLogin();
            Assert.assertTrue(homePage.isAvatarVisible());
            homePage.clickOnAvatar();
            homePage.clickOnSignout();
        }
    }

    @Test

    public void unsuccessfullLogin () {
        homePage.clickOnLogin();
        login.fillEmail("wrong_on_purpose");
        login.clickOnLogin();
        alertHandling(); // empty password
        login.fillPassword("wrong_on_purpose");
        login.clickOnLogin();
        alertHandling(); // empty email
        for (int i=0;i<4;i++) {
            login.fillEmail(emailsRegistered[i]+emailSufix);
            for (int j=0;j<4;j++) {
                if (i==j) continue;
                login.fillPassword(passwords[j]);
            }
            login.clickOnLogin();
            alertHandling();
        }
    }
    private void alertHandling () {
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"Pogrešan email ili lozinka");
        alert.accept();
    }
}
