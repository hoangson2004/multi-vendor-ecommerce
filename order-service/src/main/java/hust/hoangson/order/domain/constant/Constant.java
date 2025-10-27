package hust.hoangson.order.domain.constant;

public class Constant {

    public static class OrderStatus {
        public static final int PENDING = 1;
        public static final int CONFIRMED = 2;
        public static final int SHIPPING = 3;
        public static final int COMPLETED = 4;
        public static final int CANCELED = 5;
    }

    public static class PaymentStatus {
        public static final int UNPAID = 1;
        public static final int PAID = 2;
        public static final int REFUNDED = 3;
    }
}
