package com.example.notifications;

/**
 * Decorator that adds WhatsApp notification capability.
 */
public class WhatsAppDecorator extends NotifierDecorator {
    private final String whatsappId;
    
    public WhatsAppDecorator(Notifier notifier, String whatsappId) {
        super(notifier);
        this.whatsappId = whatsappId;
    }
    
    @Override
    public void notify(String text) {
        // Send WhatsApp message first
        System.out.println("[WHATSAPP -> " + whatsappId + "]: " + text);
        // Then delegate to wrapped notifier
        super.notify(text);
    }
}
