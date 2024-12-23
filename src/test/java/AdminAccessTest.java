import org.example.AdminAccess;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AdminAccessTest {

    @DataProvider(name = "adminPasswordData")
    public Object[][] adminPasswords() {
        return new Object[][] {
                {"iamadmin", true},    // Valid password
                {"wrongpass", false},  // Invalid password
                {"", false},           // Empty password
        };
    }

    @Test(dataProvider = "adminPasswordData")
    public void testAdminPassword(String password, boolean expected) {
        AdminAccess adminAccess = new AdminAccess(null, null); // Mock dependencies
        boolean result = adminAccess.validatePassword(password);
        Assert.assertEquals(result, expected, "Password validation failed!");
    }
}
