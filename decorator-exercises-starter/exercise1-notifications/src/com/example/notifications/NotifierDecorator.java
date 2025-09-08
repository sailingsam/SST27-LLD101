package com.example.notifications;

/**
 * Base decorator for Notifier implementations.
 * Provides common structure for all concrete decorators.
 */
public abstract class NotifierDecorator implements Notifier {
    protected Notifier notifier;
    
    public NotifierDecorator(Notifier notifier) {
        this.notifier = notifier;
    }
    
    @Override
    public void notify(String text) {
        notifier.notify(text);
    }
}
