package com.example.notifications;

/**
 * Decorator that adds Slack notification capability.
 */
public class SlackDecorator extends NotifierDecorator {
    private final String slackChannel;
    
    public SlackDecorator(Notifier notifier, String slackChannel) {
        super(notifier);
        this.slackChannel = slackChannel;
    }
    
    @Override
    public void notify(String text) {
        // Send Slack message first
        System.out.println("[SLACK -> #" + slackChannel + "]: " + text);
        // Then delegate to wrapped notifier
        super.notify(text);
    }
}
