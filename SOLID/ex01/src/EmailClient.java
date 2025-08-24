public class EmailClient implements EmailService {
    public void send(String to, String body) {
        System.out.println("[EMAIL to=" + to + "] " + body);
    }
}