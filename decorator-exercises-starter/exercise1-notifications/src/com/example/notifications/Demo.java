package com.example.notifications;

/**
 * Starter demo that uses only the existing Email notifier.
 * TODOs guide you to add decorators and compose them.
 */
public class Demo {
    public static void main(String[] args) {
        Notifier base = new EmailNotifier("user@example.com");

        // Baseline behavior (already works)
        base.notify("Baseline email only.");

        System.out.println("\n=== Decorator Pattern Compositions ===\n");
        
        // a) Email + SMS
        System.out.println("--- Email + SMS ---");
        Notifier emailAndSms = new SmsDecorator(base, "+91-99999-11111");
        emailAndSms.notify("Build green ✅");
        
        // b) Email + WhatsApp
        System.out.println("\n--- Email + WhatsApp ---");
        Notifier emailAndWhatsApp = new WhatsAppDecorator(base, "user_wa");
        emailAndWhatsApp.notify("Code review ready 📝");
        
        // c) Email + Slack
        System.out.println("\n--- Email + Slack ---");
        Notifier emailAndSlack = new SlackDecorator(base, "deployments");
        emailAndSlack.notify("Tests passed 🧪");
        
        // d) Email + WhatsApp + Slack (multiple decorators chained)
        System.out.println("\n--- Email + WhatsApp + Slack ---");
        Notifier multiChannel = new SlackDecorator(
            new WhatsAppDecorator(base, "user_wa"), 
            "deployments"
        );
        multiChannel.notify("Deployment completed 🚀");
        
        // Bonus: All three channels
        System.out.println("\n--- All Channels: SMS + WhatsApp + Slack + Email ---");
        Notifier allChannels = new SlackDecorator(
            new WhatsAppDecorator(
                new SmsDecorator(base, "+91-99999-11111"), 
                "user_wa"
            ), 
            "alerts"
        );
        allChannels.notify("Critical system alert! 🚨");
    }
}
