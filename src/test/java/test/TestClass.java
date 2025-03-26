package test;

import base.BaseClass;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.*;

public class TestClass extends BaseClass {

    private HomePage homePage;
    private LoginPage loginPage;
    private RegisteringPage registeringPage;
    private ConfirmRegistrationPage confirmRegistrationPage;
    private ResettingPasswordPage resettingPasswordPage;
    private  String [] emailsRegistered = {"petarpetrovic","admin","mirkamiric","bojana"};
    private String [] passwords={"petar123","admin","mirka123","bojana"};
    private final String  emailSufix="@gmail.com";
    private final String[] seeds={"Brazil","Kuba","Etiopija"};
    private final String[] milks={"Obicno mleko","Bademovo mleko","Sojino mleko"};

    @BeforeMethod
    public void beforeAllTests() {
        homePage= new HomePage();
        loginPage = new LoginPage();
        registeringPage = new RegisteringPage();
        confirmRegistrationPage = new ConfirmRegistrationPage();
        resettingPasswordPage = new ResettingPasswordPage();
    }

    @Test

    public void successfullLogin1 () throws Exception {
        for (int i=0;i<4;i++) {
            if (i==1) continue;
            homePage.clickOnLogin();
            Thread.sleep(1000);
            loginPage.fillEmail(emailsRegistered[i]+emailSufix);
            Thread.sleep(1000);
            loginPage.fillPassword(passwords[i]);
            Thread.sleep(1000);
            loginPage.clickOnLogin();
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
        loginPage.fillEmail("wrong_on_purpose");
        Thread.sleep(1000);
        loginPage.clickOnLogin();
        Thread.sleep(1000);
        alertHandling(); // empty password
        loginPage.fillPassword("wrong_on_purpose");
        Thread.sleep(1000);
        loginPage.clickOnLogin();
        alertHandling(); // empty email
        for (int i=0;i<4;i++) {
            loginPage.fillEmail(emailsRegistered[i]+emailSufix);
            Thread.sleep(1000);
            for (int j=0;j<4;j++) {
                if (i==j) continue;
                loginPage.fillPassword(passwords[j]);
                Thread.sleep(1000);
                loginPage.clickOnLogin();
                alertHandling();
            }
        }
    }

    @Test

    public void registeringNotDone() throws Exception{
        homePage.clickOnLogin();
        Thread.sleep(1000);
        loginPage.clickOnRegister();
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
        loginPage.fillEmail(emailsRegistered[0]+emailSufix)
                .fillPassword(passwords[0]);
        Thread.sleep(1000);
        loginPage.clickOnLogin();
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
            milkSeed(i);
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

    public void examineItemCartsSimple() throws Exception {
        double price=0;
        for (int i=0;i< homePage.getSizeSpreadButtons();i++) {
            if (i>=2) homePage.scrollDown();
            homePage.clickOnSpread(i);
            Thread.sleep(2000);
            homePage.addToCart(i);
            price+=homePage.getPriceItem(i);
            Reporter.log("Moment price: "+price,true);
            Reporter.log(homePage.getOverallPriceText(),true);
            Reporter.log("Overall price: "+homePage.getOverallPrice(),true);
            Assert.assertEquals(price,homePage.getOverallPrice());
        }
    }

//    @Test
//
//    public void examineCart() throws Exception {
//        double price=0;
//        for (int i=0;i<homePage.getSizeSpreadButtons();i++) {
//            if (i>=2) homePage.scrollDown();
//            homePage.clickOnSpread(i);
//            switch (i%3) {
//                case 0: {
//                    homePage.clickOnSmallSize(i);
//                    break;
//                }
//                case 1: {
//                    homePage.clickOnMediumSize(i);
//                    price+=10;
//                    break;
//                }
//                case 2: {
//                    homePage.clickOnBigSize(i);
//                    price+=20;
//                    break;
//                }
//            }
//            Thread.sleep(1000);
//            milkSeed(i);
//            price+=homePage.getPriceItem(i);
//            homePage.addToCart(i);
//            Reporter.log("Moment price: "+price,true);
//            Reporter.log(homePage.getOverallPriceText(),true);
//            Reporter.log("Overall price: "+homePage.getOverallPrice(),true);
//            Assert.assertEquals(price,homePage.getOverallPrice());
//                        }
//        }
    @Test

    public void examineSizes() throws Exception {
        for (int i=0;i< homePage.getSizeSpreadButtons();i++) {
            if (i>=2) homePage.scrollDown();
            homePage.clickOnSpread(i);
            double itemPrice= homePage.getPriceItem(i);
            Thread.sleep(500);
            homePage.clickOnSmallSize(i);
            Assert.assertTrue(homePage.isSmallCoffeeSelected(i));
            Thread.sleep(500);
            homePage.clickOnMediumSize(i);
            Assert.assertTrue(homePage.isMediumCoffeeSelected(i));
            Assert.assertEquals(itemPrice+10,homePage.getPriceItem(i));
            Thread.sleep(500);
            homePage.clickOnBigSize(i);
            Assert.assertTrue(homePage.isBigCoffeeSelected(i));
            Assert.assertEquals(itemPrice+20,homePage.getPriceItem(i));
            Thread.sleep(500);
        }
    }

    @Test

    public void examineQuantityButtons() throws Exception {
        int j=1;
        for (int i=0;i< homePage.getSizeSpreadButtons();i++) {
            int k=1;
            homePage.clickOnSpread(i);
            Thread.sleep(500);
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            Assert.assertEquals(++k,homePage.getQuantity(i));
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            Assert.assertEquals(++k,homePage.getQuantity(i));
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            Assert.assertEquals(++k,homePage.getQuantity(i));
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            Assert.assertEquals(++k,homePage.getQuantity(i));
            homePage.clickOnPlusQuantity(i);
            Thread.sleep(500);
            Assert.assertEquals(++k,homePage.getQuantity(i));
            homePage.clickOnMinusesQuantity(i+j);
            Thread.sleep(500);
            Assert.assertEquals(--k,homePage.getQuantity(i));
            homePage.clickOnMinusesQuantity(i+j);
            Thread.sleep(500);
            Assert.assertEquals(--k,homePage.getQuantity(i));
            homePage.clickOnMinusesQuantity(i+j);
            Thread.sleep(500);
            Assert.assertEquals(--k,homePage.getQuantity(i));
            homePage.clickOnMinusesQuantity(i+j);
            Thread.sleep(500);
            Assert.assertEquals(--k,homePage.getQuantity(i));
            homePage.clickOnMinusesQuantity(i+j);
            Thread.sleep(500);
            Assert.assertEquals(--k,homePage.getQuantity(i));
            j++;
        }
    }

    @Test

    public void registerPageTest () throws Exception {
        fillRegistrationForm();
        confirmRegistrationPage.clickOnBackToHome();
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE);
        fillRegistrationForm();
        confirmRegistrationPage.clickOnBack();
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE+"login");
    }

    @Test

    public void resetPasswordExamine() throws Exception {
        homePage.clickOnLogin();
        Thread.sleep(500);
        loginPage.clickOnForgetPassword();
        Thread.sleep(500);
        resettingPasswordPage.clickOnBack();
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE+"login");
        Thread.sleep(500);
        loginPage.clickOnForgetPassword();
        Thread.sleep(500);
        resettingPasswordPage.fillEmail("Any@any.any");
        Thread.sleep(500);
        resettingPasswordPage.clickOnResetButton();
        Thread.sleep(500);
        resettingPasswordPage.clickOnSendAgain();
        Thread.sleep(500);
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE+"password-reset#");
        Thread.sleep(500);
        resettingPasswordPage.fillBoxes("Any");
        Thread.sleep(500);
        resettingPasswordPage.clickOnResetButton();
        Thread.sleep(500);
        resettingPasswordPage.fillNewPassword("New");
        Thread.sleep(500);
        resettingPasswordPage.fillConfirmedPassword("New");
        Thread.sleep(500);
        resettingPasswordPage.clickOnResetButton();
        Thread.sleep(500);
        Assert.assertEquals(resettingPasswordPage.getConfirmationPasswordText(),"Uspešno resetovanje lozinke!");
        resettingPasswordPage.clickOnResetButton();
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE+"login");

    }

    private void alertHandling () {
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"Pogrešan email ili lozinka");
        alert.accept();
    }

    private void fillRegistrationForm () throws Exception {
        homePage.clickOnLogin();
        Thread.sleep(500);
        loginPage.clickOnRegister();
        Thread.sleep(500);
        registeringPage.fillFullName("Any Any")
                .fillEmail("any@any.com")
                .fillPassword("Any Any")
                .fillConfirmPassword("Any Any")
                .clickOnRegister();
        Assert.assertTrue(confirmRegistrationPage.isRegistrationSuccessfull());
        Assert.assertEquals(confirmRegistrationPage.getSuccessfullRegistrationText(),"Uspešno ste se registrovali!");
    }

    private void milkSeed (int i) throws Exception {
        for (int j=0;j<3;j++){
            homePage.selectSeedOption(seeds[j],i);
            Thread.sleep(500);
            for (int k=0;k<3;k++) {
                try {
                    homePage.selectMilkOption(milks[k], i);
                }
                catch (Exception e) {
                    Reporter.log("Milk option isn't available",true);
                }
                finally {
                    Thread.sleep(500);
                }

            }
        }
    }

}
