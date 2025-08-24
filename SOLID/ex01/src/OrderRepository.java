public interface OrderRepository {
    void saveOrder(String customerEmail, double total);
}
