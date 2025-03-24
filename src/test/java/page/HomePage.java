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

    @FindBy (xpath="//div[@class='dropdownMenu']/ul/li[1]")
    WebElement profile;

    @FindBy (css=".coffe-item-basket")
    List<WebElement> baskets;

    @FindBy(css = ".fas.fa-minus")
    List<WebElement> minuses;

    @FindBy(xpath = "//p/span[2]")
    WebElement overall;

    @FindBy (xpath = "//span/strong")
    List<WebElement> itemPrices;

    @FindBy (css="[alt='Coffe size S']")
    List<WebElement> smalls;

    @FindBy (css="[alt='Coffe size M']")
    List<WebElement> mediums;

    @FindBy (css="[alt='Coffe size L']")
    List<WebElement> bigs;


    List<Select> seedSelect;
    List<Select> milkSelect;

    List<WebElement> spreadButtons = new ArrayList<>();
    List<WebElement> plusQuantityButtons = new ArrayList<>();
    List<WebElement> narrowButtons = new ArrayList<>();
    List<WebElement> minusQuantityButtons = new ArrayList<>();

    public HomePage () {
        PageFactory.initElements(driver,this);
        spreadButtons=getSpreadButtons();
        plusQuantityButtons=getPlusButtons();
        seedSelect=getSeedSelect();
        milkSelect=getMilkSelect();
        narrowButtons=getNarrowButtons();
        minusQuantityButtons=getMinusQuantityButtons();
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
        js.executeScript("window.scrollBy(0,1000)");
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
    public int getSizeSpreadButtons () {
        return spreadButtons.size();
    }
    public int getSizeMilkButtons () {
        return milkSelect.size();
    }
    public int getSizeSeedButtons() {
        return seedSelect.size();
    }

    private List<WebElement> getNarrowButtons() {
        List<WebElement> list = new ArrayList<>();

        for (int i=0;i< minuses.size();i+=2) {
            list.add(minuses.get(i));
        }
        return list;
    }
    public List<WebElement> getMinusQuantityButtons() {
        List<WebElement> list = new ArrayList<>();

        for (int i=1;i< minuses.size();i+=2) {
            list.add(minuses.get(i));
        }
        return list;
    }


    public void clickOnNarrowButton (int i) {
        clickOnElement(narrowButtons.get(i));
    }
    public int getNarrowButtonsSize() {
        return narrowButtons.size();
    }
    public void scrollUp () {
        js.executeScript("window.scrollBy(0,-20000)");
    }
    public int getCartButtonsSize() {
        return addToCart.size();
    }

    public double getOverallPrice() {
        String [] words = getTextFromElement(overall).split(" ");
        String [] price = words[0].split(",");
        if (price[0].length()>3) {
            String [] newPriceWords = price[0].split(".");
            return Double.parseDouble(newPriceWords[0])*1000+Double.parseDouble(newPriceWords[1]);
        }
        else
            return Double.parseDouble(price[0]);
    }
    public double getPriceItem (int i) {
        String [] words = getTextFromElement(itemPrices.get(i)).split(" ");
        String [] price = words[0].split(",");
        return Double.parseDouble(price[0]);
    }

    public String getOverallPriceText() {
        return getTextFromElement(overall);
    }

    public void clickOnSmallSize (int i) {
        clickOnElement(smalls.get(i));
    }
    public void clickOnMediumSize (int i) {
        clickOnElement(mediums.get(i));
    }
    public void clickOnBigSize (int i) {
        clickOnElement(bigs.get(i));
    }

    public void clickOnPlusQuantity (int i) {
        clickOnElement(plusQuantityButtons.get(i));
    }

    public void clickOnMinusesQuantity (int i) {
        clickOnElement(minusButtons.get(i));
    }


}
