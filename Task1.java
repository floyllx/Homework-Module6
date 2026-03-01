//Интерфейс стратегии
interface IPaymentStrategy {
    void pay(double amount);
}

//Класс стратегии банковская карта
class CreditCardPayment implements IPaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Оплата банковской картой");
    }

}

//Класс стратегии PayPal
class PayPalPayment implements IPaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Оплата через PayPal");
    }
}

//Класс стратегии криптовалюта
class CryptoPayment implements IPaymentStrategy {

    private String walletAddress;

    public CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Оплата криптовалютой");
    }
}

//Класс контекста
class PaymentContext {

    private IPaymentStrategy strategy;

    public void setStrategy(IPaymentStrategy strategy) {
        this.strategy = strategy;
    }
    public void executePayment(double amount) {
        if (strategy == null) {
            System.out.println("Стратегия оплаты не выбрана!");
            return;
        }
        strategy.pay(amount);
    }

}

//Клиентский код
public class Task1 {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("Выберите способ оплаты: 1 - Карта, 2 - PayPal, 3 - Криптовалюта");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> context.setStrategy(new CreditCardPayment("1122334455667788"));
            case 2 -> context.setStrategy(new PayPalPayment("example@gmail.com"));
            case 3 -> context.setStrategy(new CryptoPayment("0x0000000"));
        }

        context.executePayment(500.00);
        scanner.close();
    }
}
