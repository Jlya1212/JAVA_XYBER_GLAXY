package java_assignment;

import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserManagerTest {
    private UserManager userManager;

    @Before
    public void setUp() {
        userManager = new UserManager();
    }

    // 1. Test adding customer manually
    @Test
    public void testAddCustomerManually() {
        Customer customer = new Customer("alice", "123", "Alice", "alice@example.com");
        userManager.addUser(customer);

        ArrayList<User> users = userManager.getUsers();
        assertEquals(1, users.size());
        assertEquals("alice", users.get(0).getUsername());
    }

    // 2. Test registerCustomer() with simulated Scanner input
    @Test
    public void testRegisterCustomer_ValidData() {
        String input = "bob\npassword123\nBob\nbob@example.com\n";
        Scanner scanner = new Scanner(input);
        userManager.registerCustomer(scanner);

        ArrayList<User> users = userManager.getUsers();
        assertEquals(1, users.size());
        assertTrue(users.get(0) instanceof Customer);
        assertEquals("bob", users.get(0).getUsername());
    }

    // 3. Test registerAdmin() with simulated Scanner input
    @Test
    public void testRegisterAdmin_ValidData() {
        String input = "admin1\npass321\nAdmin One\nadmin1@example.com\n1\n";
        Scanner scanner = new Scanner(input);
        userManager.registerAdmin(scanner);

        ArrayList<User> users = userManager.getUsers();
        assertEquals(1, users.size());
        assertTrue(users.get(0) instanceof Admin);
        assertEquals(AdminType.SUPER_ADMIN, ((Admin) users.get(0)).getAdminType());
    }

    // 4. Test login with correct username/password
    @Test
    public void testLoginSuccess() {
        Customer customer = new Customer("user", "pass", "User", "user@example.com");
        userManager.addUser(customer);

        Scanner input = new Scanner("user\npass\n");
        User result = userManager.login(input);

        assertNotNull(result);
        assertEquals("user", result.getUsername());
    }

    // 5. Test login with incorrect password
    @Test
    public void testLoginFail() {
        Customer customer = new Customer("user", "pass", "User", "user@example.com");
        userManager.addUser(customer);

        Scanner input = new Scanner("user\nwrongpass\n");
        User result = userManager.login(input);

        assertNull(result);
    }

    // 6. Username availability
    @Test
    public void testIsUsernameAvailable() {
        Customer customer = new Customer("testuser", "123", "Test", "test@example.com");
        userManager.addUser(customer);

        // Use reflection to call private method
        boolean result = invokeIsUsernameAvailable("testuser");
        assertFalse(result);
        boolean result2 = invokeIsUsernameAvailable("newuser");
        assertTrue(result2);
    }

    // 7. Email format validation
    @Test
    public void testIsValidEmail() {
        assertTrue(invokeIsValidEmail("good@email.com"));
        assertFalse(invokeIsValidEmail("bad-email"));
        assertFalse(invokeIsValidEmail("bad@domain"));
    }

    // 8. Test listAllUsers() output (won't assert, just run to make sure no crash)
    @Test
    public void testListAllUsers() {
        Customer customer = new Customer("viewer", "pass", "View", "view@example.com");
        userManager.addUser(customer);

        userManager.listAllUsers(); // Expect no error
    }

    // ------------------ Private Method Testing Helpers ------------------

    private boolean invokeIsUsernameAvailable(String username) {
        try {
            java.lang.reflect.Method method = UserManager.class.getDeclaredMethod("isUsernameAvailable", String.class);
            method.setAccessible(true);
            return (boolean) method.invoke(userManager, username);
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
            return false;
        }
    }

    private boolean invokeIsValidEmail(String email) {
        try {
            java.lang.reflect.Method method = UserManager.class.getDeclaredMethod("isValidEmail", String.class);
            method.setAccessible(true);
            return (boolean) method.invoke(userManager, email);
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
            return false;
        }
    }
}
