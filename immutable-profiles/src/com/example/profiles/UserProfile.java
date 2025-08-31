package com.example.profiles;

/**
 * Immutable UserProfile with Builder pattern.
 * All fields are final and validation is centralized in the Builder.
 */
public final class UserProfile {
    // All fields are private final - immutable
    private final String id;
    private final String email;
    private final String phone;
    private final String displayName;
    private final String address;
    private final boolean marketingOptIn;
    private final String twitter;
    private final String github;

    // Private constructor - only accessible through Builder
    private UserProfile(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.phone = builder.phone;
        this.displayName = builder.displayName;
        this.address = builder.address;
        this.marketingOptIn = builder.marketingOptIn;
        this.twitter = builder.twitter;
        this.github = builder.github;
    }

    // Static factory method to create builder
    public static Builder builder() {
        return new Builder();
    }

    // Only getters - no setters for immutability
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDisplayName() { return displayName; }
    public String getAddress() { return address; }
    public boolean isMarketingOptIn() { return marketingOptIn; }
    public String getTwitter() { return twitter; }
    public String getGithub() { return github; }

    /**
     * Builder class for UserProfile construction with validation.
     * Required fields: id, email
     * Optional fields: phone, displayName, address, marketingOptIn, twitter, github
     */
    public static class Builder {
        // Required fields
        private String id;
        private String email;
        
        // Optional fields with defaults
        private String phone;
        private String displayName;
        private String address;
        private boolean marketingOptIn = false;
        private String twitter;
        private String github;

        private Builder() {}

        // Required field setters
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        // Optional field setters
        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder marketingOptIn(boolean marketingOptIn) {
            this.marketingOptIn = marketingOptIn;
            return this;
        }

        public Builder twitter(String twitter) {
            this.twitter = twitter;
            return this;
        }

        public Builder github(String github) {
            this.github = github;
            return this;
        }

        /**
         * Builds the UserProfile with centralized validation.
         * All validation happens here in one place.
         */
        public UserProfile build() {
            // Validate required fields
            Validation.requireNonBlank(id, "id");
            Validation.requireEmail(email);

            // Validate optional fields with consistent policies
            if (displayName != null && displayName.length() > 100) {
                throw new IllegalArgumentException("displayName must not exceed 100 characters");
            }

            if (phone != null && !Validation.isBlank(phone)) {
                // Basic phone validation - could be enhanced
                String cleanPhone = phone.replaceAll("[^0-9+\\-\\s()]", "");
                if (cleanPhone.length() < 5) {
                    throw new IllegalArgumentException("phone must be a valid phone number");
                }
                this.phone = cleanPhone;
            }

            if (address != null && address.length() > 200) {
                throw new IllegalArgumentException("address must not exceed 200 characters");
            }

            // Social media handles validation
            if (twitter != null && !Validation.isBlank(twitter)) {
                if (!twitter.startsWith("@")) {
                    this.twitter = "@" + twitter;
                }
            }

            if (github != null && !Validation.isBlank(github)) {
                if (github.contains("/") || github.contains("@")) {
                    throw new IllegalArgumentException("github should be username only, not URL");
                }
            }

            return new UserProfile(this);
        }
    }
}
