package page;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BaseClass {

    @FindBy (css=".loginButton")
    WebElement login;

    @FindBy (css=".footerMain__right button")
    WebElement order;

    @FindBy (css=".footerMain .footerMain__left p")
    WebElement priceSection;

    @FindBy (css=".avatar")
    WebElement avatar;

    @FindBy (css=".coffe-item")
    List<WebElement> coffeItems;

    @FindBy (css=".coffe-item .fas.fa-plus")
    List<WebElement> plusButtons;

    @FindBy (css=".fas.fa-minus")
    List<WebElement> minusButtons;

    @FindBy (css = ".coffe-item-basket")
    List<WebElement> addToCart;

    @FindBy(id = "dropdown-left")
    List<WebElement> seedDropdown;

    @FindBy(id = "dropdown-right")
    List<WebElement> milkDropdown;

    @FindBy (xpath="//div[@class='dropdownMenu']/ul/li[2]")
    WebElement signout;

    @FindBy(xpath = "//div[@class='dropdownMenu']/ul/li[1]")
    WebElement profile;

    @FindBy (css=".coffe-item-basket")
    List<WebElement> baskets;


    List<Select> seedSelect;
    List<Select> milkSelect;

    List<WebElement> spreadButtons = new ArrayList<>();
    List<WebElement> plusQuantityButtons = new ArrayList<>();

    public HomePage () {
        PageFactory.initElements(driver,this);
        spreadButtons=getSpreadButtons();
        plusQuantityButtons=getPlusButtons();
        seedSelect=getSeedSelect();
        milkSelect=getMilkSelect();
    }

    public void clickOnLogin () {
        clickOnElement(login);
    }

    public void clickOnSpread (int i) {
        clickOnElement(spreadButtons.get(i));
    }

    public void selectSeedOption (String option, int i) {
        seedSelect.get(i).selectByVisibleText(option);
    }

    public void selectMilkOption (String option, int i) {
        milkSelect.get(i).selectByVisibleText(option);
    }

    public void addToCart (int i) {
        clickOnElement(addToCart.get(i));
    }

    public boolean isAvatarVisible () {
        return isElementDisplayed(avatar);
    }

    private List<WebElement> fillBaskets (List<WebElement> baskets) {
        List<WebElement> list = new ArrayList<>();
        for (WebElement w:baskets) {
            list.add(w);
        }
        return list;
    }

    public List<WebElement> getCoffeItems() {
        return coffeItems;
    }

    public List<WebElement> getPlusButtons() {
        List<WebElement> list = new ArrayList<>();

        for (int i=1;i< plusButtons.size();i+=2) {
            list.add(plusButtons.get(i));
        }
        return list;
    }

    public List<WebElement> getMinusButtons() {
        return minusButtons;
    }

    public List<Select> getSeedSelect() {
        List<Select> list = new ArrayList<>();
        for (WebElement w:seedDropdown) {
            list.add(new Select(w));
        }
        return list;
    }
    public List<Select> getMilkSelect() {
        List<Select> list = new ArrayList<>();
        for (WebElement w:milkDropdown) {
            list.add(new Select(w));
        }
//        for (WebElement w:milkDropdown) {
//            list.add(new Select(w));
//        }
        return list;
    }


    private List<WebElement> getSpreadButtons() {
        List<WebElement> list = new ArrayList<>();

        for (int i=0;i< plusButtons.size();i+=2) {
            list.add(plusButtons.get(i));
        }
        return list;
    }

    public List<WebElement> getPlusQuantityButtons() {
        return plusQuantityButtons;
    }

    public void clickOnAvatar() {
        clickOnElement(avatar);
    }
    public void clickOnSignout () {
        clickOnElement(signout);
    }
    public void clickOnProfile() {
        clickOnElement(profile);
    }
    public String getLoginText () {
        return getTextFromElement(login);
    }
    public void scrollDown () {
        js.executeScript("window.scrollBy(0,500)");
    }
    public boolean isBasketVisible (int i) {
        return isElementDisplayed(baskets.get(i));
    }
    public String getBasketText (int i) {
        return getTextFromElement(baskets.get(i));
    }

    public String getSeedOptionText (int i,int j) {
        return seedSelect.get(i).getOptions().get(j).getText();
    }
    public String getMilkOptionText (int i,int j) {
        return milkSelect.get(i).getOptions().get(j).getText();
    }
}
