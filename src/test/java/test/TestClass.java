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
    private OrderStatusPage orderStatusPage;
    private final String [] emailsRegistered = {"petarpetrovic","admin","mirkamiric","bojana"};
    private final String [] passwords={"petar123","admin","mirka123","bojana"};
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
        orderStatusPage = new OrderStatusPage();
    }

    @Test (priority = 2, description = "login with valid inputs", dependsOnMethods ="examineLoyaltyPage")

    public void successfullLogin () throws Exception {
        for (int i=0;i<4;i++) {
            if (i==1) continue;
            homePage.clickOnLogin();
            Thread.sleep(500);
            loginPage.fillEmail(emailsRegistered[i]+emailSufix);
            Thread.sleep(500);
            loginPage.fillPassword(passwords[i]);
            Thread.sleep(500);
            loginPage.clickOnLogin();
            Assert.assertTrue(homePage.isAvatarVisible());
            Thread.sleep(500);
            homePage.clickOnAvatar();
            Thread.sleep(500);
            homePage.clickOnSignout();
        }
    }

    @Test (priority = 4, description = "invalid credentials")

    public void unsuccessfullLogin () throws Exception {
        homePage.clickOnLogin();
        Thread.sleep(500);
        loginPage.fillEmail("wrong_on_purpose");
        Thread.sleep(500);
        loginPage.clickOnLogin();
        Thread.sleep(500);
        Assert.assertEquals(loginPage.getErrorMessage(),"Uneli ste pogrešnu lozinku. Pokušajte ponovo."); // empty password
        loginPage.fillPassword("wrong_on_purpose");
        Thread.sleep(500);
        loginPage.clickOnLogin();
        Assert.assertEquals(loginPage.getErrorMessage(),"Uneli ste pogrešnu lozinku. Pokušajte ponovo."); // empty email
        for (int i=0;i<4;i++) {
            loginPage.fillEmail(emailsRegistered[i]+emailSufix);
            Thread.sleep(1000);
            for (int j=0;j<4;j++) {
                if (i==j) continue;
                loginPage.fillPassword(passwords[j]);
                Thread.sleep(1000);
                loginPage.clickOnLogin();
                Assert.assertEquals(loginPage.getErrorMessage(),"Uneli ste pogrešnu lozinku. Pokušajte ponovo."); // credentials not matching
            }
        }
    }

    @Test (priority = 3, description = "profile options", dependsOnMethods = "examineLoyaltyPage")

    public void examineProfileHomePage() throws Exception {

        for (int i=0;i<4;i++) {
            homePage.clickOnLogin();
            if (i==1) continue;
            Thread.sleep(500);
            loginPage.fillEmail(emailsRegistered[i] + emailSufix)
                    .fillPassword(passwords[i]);
            Thread.sleep(500);
            loginPage.clickOnLogin();
            Thread.sleep(500);
            homePage.clickOnAvatar();
            Thread.sleep(500);
            homePage.clickOnProfile();
            Thread.sleep(500);
            Assert.assertEquals(driver.getCurrentUrl(), URL_BASE + "profile");
            examineLoyaltyPage();
            registeringPage.clickOnBack();
            Thread.sleep(500);
            homePage.clickOnAvatar();
            Thread.sleep(500);
            homePage.clickOnSignout();
            Thread.sleep(500);
            Assert.assertEquals(homePage.getLoginText(), "Prijavi se");
        }
    }

    @Test (priority = 6, description = "spread item options")

    public void examinePlusButtons() throws Exception {
        for (int i=0;i< homePage.getSizeSpreadButtons();i++) {
            if (i>3) homePage.scrollDown();
            homePage.clickOnSpread(i);
            Thread.sleep(500);
            Assert.assertTrue(homePage.isBasketVisible(i));
            Assert.assertEquals(homePage.getBasketText(i),"Dodaj u korpu");
        }
    }

    @Test (priority = 8 , description = "milk and seed select options")

    public void examineSeedsMilkSelection() throws Exception {
        for (int i=0;i<homePage.getSizeSpreadButtons();i++) {
            if (i>=3) homePage.scrollDown();
            homePage.clickOnSpread(i);
            Thread.sleep(500);
            milkSeed(i);
        }
    }

    @Test (priority = 7, description = "narrow item options",dependsOnMethods = "examinePlusButtons")

    public void examineNarrowButtons() throws Exception {

        examinePlusButtons();
        Thread.sleep(500);
        homePage.scrollUp();
        Thread.sleep(500);
        homePage.scrollUp();
        for (int i=0;i< homePage.getNarrowButtonsSize();i++) {
            homePage.clickOnNarrowButton(i);
            Thread.sleep(500);
        }

    }

    @Test (priority = 11, description = "one item each in cart")

    public void examineItemCartsSimple() throws Exception {
        double price=0;
        for (int i=0;i< homePage.getSizeSpreadButtons();i++) {
            if (i>=2) homePage.scrollDown();
            homePage.clickOnSpread(i);
            Thread.sleep(500);
            homePage.addToCart(i);
            price+=homePage.getPriceItem(i);
            Reporter.log("Moment price: "+price,true);
            Reporter.log(homePage.getOverallPriceText(),true);
            Reporter.log("Overall price: "+homePage.getOverallPrice(),true);
            Assert.assertEquals(price,homePage.getOverallPrice());
        }
    }

    @Test (priority = 12, description = "cancel orders for each item", dependsOnMethods = "examineItemCartsSimple")

    public void cancelOrders() throws Exception {
        examineItemCartsSimple();
        double price=homePage.getOverallPrice();
        Thread.sleep(500);
        for (int i=6;i>=0;i--) {
            homePage.clickOnFooter();
            homePage.scrollDown();
            homePage.clickOnX(i);
            Reporter.log("Overall price at the moment: "+homePage.getOverallPrice(),true);
            Thread.sleep(500);
            homePage.clickOnNo();
            Thread.sleep(500);
            homePage.clickOnFooter();
            homePage.clickOnX(i);
            Thread.sleep(500);
            Reporter.log("Item is removed of price: "+homePage.getPriceItem(i),true);
            Thread.sleep(500);
            homePage.clickOnYes();
            Reporter.log("Overall price at the moment: "+homePage.getOverallPrice(),true);
            price-=homePage.getPriceItem(i);
            Assert.assertEquals(price,homePage.getOverallPrice());
            Thread.sleep(500);
        }

    }

//    @Test (priority = 13, description = "one item each in cart", dependOnMethod="examineItemCartsSimple")
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
    @Test (priority = 9 , description = "all size options", dependsOnMethods = "examinePlusButtons")

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

    @Test (priority = 10 , description = "+ and - quantity options", dependsOnMethods = "examinePlusButtons")

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

    @Test (priority = 5, description = "registering")

    public void registerPageTest () throws Exception {
        fillRegistrationForm();
        Thread.sleep(500);
        confirmRegistrationPage.clickOnBackToHome();
        Thread.sleep(500);
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE);
        fillRegistrationForm();
        confirmRegistrationPage.clickOnBack();
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE+"login");
    }

    @Test (priority = 14, description = "reset password page check")

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

    @Test (priority=15, description = "checking animation of coffee preparing and serving")

    public void examineOrderStatus () throws Exception {
        homePage.clickOnOrderButton();
        Assert.assertEquals(orderStatusPage.getStatusMessage(),"Porudzbina primljena");
        Thread.sleep(16000);
        Assert.assertEquals(orderStatusPage.getStatusMessage(),"Kafa se priprema!");
        Thread.sleep(15000);
        Assert.assertEquals(orderStatusPage.getStatusMessage(),"Kafa je spremna!");
        orderStatusPage.clickOnBack();
        Assert.assertEquals(driver.getCurrentUrl(),URL_BASE);
    }

    @Test (priority = 1, description = "loyality page check")

    public void examineLoyaltyPage () throws Exception {
        if (!driver.getCurrentUrl().equals(URL_BASE+"profile"))
            driver.get(URL_BASE+"profile");
        Thread.sleep(500);
        Assert.assertTrue(homePage.isLoyaltyDisplayed());
        Assert.assertEquals(homePage.loyaltyTextDisplayed(),"Loyalty program");
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
                    Reporter.log("Milk option isn't available for item number: "+(i+1),true);
                }
                finally {
                    Thread.sleep(500);
                }

            }
        }
    }

}
