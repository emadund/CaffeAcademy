package test;

import base.BaseClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.HomePage;
import page.LoginPage;
import page.RegisteringPage;

public class TestClass extends BaseClass {

    private HomePage homePage;
    private LoginPage login;
    private RegisteringPage registeringPage;
    private  String [] emailsRegistered = {"petarpetrovic","admin","mirkamiric","bojana"};
    private String [] passwords={"petar123","admin","mirka123","bojana"};
    private final String  emailSufix="@gmail.com";
    private final String[] seeds={"Brazil","Kuba","Etiopija"};
    private final String[] milks={"Obicno mleko","Bademovo mleko","Sojino mleko"};

    @BeforeMethod
    public void beforeAllTests() {
        homePage= new HomePage();
        login = new LoginPage();
        registeringPage = new RegisteringPage();
    }

    @Test

    public void successfullLogin1 () throws Exception {
        for (int i=0;i<4;i++) {
            if (i==1) continue;
            homePage.clickOnLogin();
            Thread.sleep(1000);
            login.fillEmail(emailsRegistered[i]+emailSufix);
            Thread.sleep(1000);
            login.fillPassword(passwords[i]);
            Thread.sleep(1000);
            login.clickOnLogin();
            Assert.assertTrue(homePage.isAvatarVisible());
            Thread.sleep(1000);
            homePage.clickOnAvatar();
            Thread.sleep(1000);
            homePage.clickOnSignout();
        }
    }

    @Test

    public void unsuccessfullLogin () throws Exception {
        homePage.clickOnLogin();
        Thread.sleep(1000);
        login.fillEmail("wrong_on_purpose");
        Thread.sleep(1000);
        login.clickOnLogin();
        Thread.sleep(1000);
        alertHandling(); // empty password
        login.fillPassword("wrong_on_purpose");
        Thread.sleep(1000);
        login.clickOnLogin();
        alertHandling(); // empty email
        for (int i=0;i<4;i++) {
            login.fillEmail(emailsRegistered[i]+emailSufix);
            Thread.sleep(1000);
            for (int j=0;j<4;j++) {
                if (i==j) continue;
                login.fillPassword(passwords[j]);
                Thread.sleep(1000);
                login.clickOnLogin();
                alertHandling();
            }
        }
    }

    @Test

    public void registeringNotDone() throws Exception{
        homePage.clickOnLogin();
        Thread.sleep(1000);
        login.clickOnRegister();
        Thread.sleep(1000);
        registeringPage.fillFullName("First Last")
                       .fillEmail("email@gmail.com")
                .fillPassword("P@ssw0rd")
                .fillConfirmPassword("P@ssw0rd");
        Thread.sleep(1000);
        registeringPage.clickOnBack();
        Thread.sleep(1000);
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE+"login");
        driver.navigate().back();
        Thread.sleep(1000);
        registeringPage.clickOnLogin();
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE+"login");

    }

    @Test

    public void examineProfileHomePage() throws Exception {
        homePage.clickOnLogin();
        Thread.sleep(1000);
        login.fillEmail(emailsRegistered[0]+emailSufix)
                .fillPassword(passwords[0]);
        Thread.sleep(1000);
        login.clickOnLogin();
        Thread.sleep(1000);
        homePage.clickOnAvatar();
        Thread.sleep(1000);
        homePage.clickOnProfile();
        Thread.sleep(1000);
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE+"profile");
        registeringPage.clickOnBack();
        Thread.sleep(1000);
        homePage.clickOnAvatar();
        Thread.sleep(1000);
        homePage.clickOnSignout();
        Thread.sleep(1000);
        Assert.assertEquals(homePage.getLoginText(),"Prijavi se");
    }

    @Test

    public void examinePlusButtons() throws Exception {
        for (int i=0;i< homePage.getSizeSpreadButtons();i++) {
            if (i>3) homePage.scrollDown();
            homePage.clickOnSpread(i);
            Thread.sleep(1000);
            Assert.assertTrue(homePage.isBasketVisible(i));
            Assert.assertEquals(homePage.getBasketText(i),"Dodaj u korpu");
        }
    }

    @Test

    public void examineSeedsMilkSelection() throws Exception {
        for (int i=0;i<homePage.getSizeSpreadButtons();i++) {
            if (i>=3) homePage.scrollDown();
            homePage.clickOnSpread(i);
            Thread.sleep(500);
            for (int j=0;j<3;j++){
                homePage.selectSeedOption(seeds[j],i);
                Thread.sleep(500);
                for (int k=0;k<3;k++) {
                    try {
                        homePage.selectMilkOption(milks[k], i);
                    }
                    catch (Exception e) {
                        continue;
                    }
                    finally {
                        Thread.sleep(500);
                    }

                }
            }
        }
    }

    @Test

    public void examineNarrowButtons() throws Exception {

        examinePlusButtons();
        Thread.sleep(1000);
        homePage.scrollUp();
        Thread.sleep(1000);
        homePage.scrollUp();
        for (int i=0;i< homePage.getNarrowButtonsSize();i++) {
            homePage.clickOnNarrowButton(i);
            Thread.sleep(1000);
        }

    }

    @Test

    public void examineCart() throws Exception {
        double price=0;
        for (int i=0;i< homePage.getSizeSpreadButtons();i++) {
            if (i>=2) homePage.scrollDown();
            homePage.clickOnSpread(i);
            Thread.sleep(2000);
            homePage.addToCart(i);
            price+=homePage.getPriceItem(i);
            System.out.println("Moment price: "+price);
            System.out.println(homePage.getOverallPriceText());
            System.out.println("Overall price: "+homePage.getOverallPrice());
            Assert.assertEquals(price,homePage.getOverallPrice());
        }
    }

    @Test

    public void examineSizes() throws Exception {
        for (int i=0;i< homePage.getSizeSpreadButtons();i++) {
            if (i>=2) homePage.scrollDown();
            homePage.clickOnSpread(i);
            Thread.sleep(500);
            homePage.clickOnSmallSize(i);
            Thread.sleep(500);
            homePage.clickOnMediumSize(i);
            Thread.sleep(500);
            homePage.clickOnBigSize(i);
            Thread.sleep(500);
        }
    }

    @Test

    public void examineQuantityButtons() throws Exception {
        for (int i=0;i< homePage.getSizeSpreadButtons();i++) {
            homePage.clickOnSpread(i);
            Thread.sleep(500);
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            homePage.clickOnMinusesQuantity(i);
            Thread.sleep(500);
            homePage.clickOnMinusesQuantity(i);
            Thread.sleep(500);
            homePage.clickOnMinusesQuantity(i);
            Thread.sleep(500);
            homePage.clickOnMinusesQuantity(i);
            Thread.sleep(500);
            homePage.clickOnMinusesQuantity(i);
            Thread.sleep(500);

        }
    }

    private void alertHandling () {
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"Pogrešan email ili lozinka");
        alert.accept();
    }

}
