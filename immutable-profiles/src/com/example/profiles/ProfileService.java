package com.example.profiles;

/**
 * Assembles immutable profiles using the Builder pattern.
 * No mutation after creation - all validation centralized in Builder.
 */
public class ProfileService {

    /**
     * Creates a minimal profile with just required fields.
     * Returns an immutable UserProfile - no mutation possible.
     */
    public UserProfile createMinimal(String id, String email) {
        return UserProfile.builder()
                .id(id)
                .email(email)
                .build(); // Validation happens here
    }

    /**
     * Creates a profile with additional optional fields.
     * All validation and business rules applied consistently.
     */
    public UserProfile createProfile(String id, String email, String displayName, 
                                   String phone, String address, boolean marketingOptIn) {
        return UserProfile.builder()
                .id(id)
                .email(email)
                .displayName(displayName)
                .phone(phone)
                .address(address)
                .marketingOptIn(marketingOptIn)
                .build(); // All validation happens in one place
    }

    /**
     * Creates a full profile with social media handles.
     */
    public UserProfile createFullProfile(String id, String email, String displayName,
                                       String phone, String address, boolean marketingOptIn,
                                       String twitter, String github) {
        return UserProfile.builder()
                .id(id)
                .email(email)
                .displayName(displayName)
                .phone(phone)
                .address(address)
                .marketingOptIn(marketingOptIn)
                .twitter(twitter)
                .github(github)
                .build(); // Centralized validation
    }

    /**
     * Since UserProfile is now immutable, we return a new instance
     * instead of mutating the existing one.
     */
    public UserProfile withUpdatedDisplayName(UserProfile existing, String newDisplayName) {
        return UserProfile.builder()
                .id(existing.getId())
                .email(existing.getEmail())
                .phone(existing.getPhone())
                .displayName(newDisplayName) // New display name
                .address(existing.getAddress())
                .marketingOptIn(existing.isMarketingOptIn())
                .twitter(existing.getTwitter())
                .github(existing.getGithub())
                .build(); // Validation ensures consistency
    }
}
