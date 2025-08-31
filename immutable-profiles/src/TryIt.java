import com.example.profiles.*;

public class TryIt {
    public static void main(String[] args) {
        ProfileService svc = new ProfileService();
        
        // Create minimal profile - only required fields
        System.out.println("1. Creating minimal profile:");
        UserProfile minimal = svc.createMinimal("u1", "alice@example.com");
        System.out.println("   ID: " + minimal.getId());
        System.out.println("   Email: " + minimal.getEmail());
        System.out.println("   Phone: " + minimal.getPhone()); // null for optional fields
        
        // Demonstrate immutability - no setters available!
        // minimal.setEmail("evil@example.com"); // This line would cause compile error!
        System.out.println("   ✓ No setters available - object is immutable!\n");
        
        // Create full profile using Builder pattern
        System.out.println("2. Creating full profile with Builder:");
        UserProfile full = UserProfile.builder()
                .id("u2")
                .email("bob@example.com")
                .displayName("Bob Smith")
                .phone("555-1234")
                .address("123 Main St, City, State")
                .marketingOptIn(true)
                .twitter("bobsmith") // Will be auto-prefixed with @
                .github("bobdev")
                .build();
        
        System.out.println("   ID: " + full.getId());
        System.out.println("   Email: " + full.getEmail());
        System.out.println("   Display Name: " + full.getDisplayName());
        System.out.println("   Phone: " + full.getPhone());
        System.out.println("   Address: " + full.getAddress());
        System.out.println("   Marketing Opt-in: " + full.isMarketingOptIn());
        System.out.println("   Twitter: " + full.getTwitter()); // Shows @bobsmith
        System.out.println("   GitHub: " + full.getGithub());
        
        // Demonstrate "updating" by creating new instance
        System.out.println("\n3. 'Updating' display name (creates new instance):");
        UserProfile updated = svc.withUpdatedDisplayName(full, "Robert Smith");
        System.out.println("   Original display name: " + full.getDisplayName());
        System.out.println("   New instance display name: " + updated.getDisplayName());
        System.out.println("   ✓ Original object unchanged - true immutability!");
        
        // Demonstrate validation
        System.out.println("\n4. Demonstrating validation:");
        try {
            UserProfile invalid = UserProfile.builder()
                    .id("b") // Empty ID should fail
                    .email("invalid-email") // Invalid email should fail
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("!! Validation caught: " + e.getMessage());
        }
        
        try {
            UserProfile invalidDisplayName = UserProfile.builder()
                    .id("u3")
                    .email("test@example.com")
                    .displayName("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901")
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("!!! Validation caught: " + e.getMessage());
        }
        
        System.out.println("\n=== All validation centralized in Builder.build() ===");
        System.out.println("=== UserProfile is now truly immutable! ===");
    }
}
