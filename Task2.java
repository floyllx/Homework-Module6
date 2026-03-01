import java.util.ArrayList;
import java.util.List;

// Интерфейс наблюдателя
interface IObserver {
    void update(String currency, double rate);
}

// Интерфейс субъекта
interface ISubject {
    void subscribe(IObserver observer);
    void unsubscribe(IObserver observer);
    void notifyObservers(String currency, double rate);
}

// Субъект — Биржа валют
class CurrencyExchange implements ISubject {
    private List<IObserver> observers = new ArrayList<>();

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
        System.out.println("Наблюдатель подписан: " + observer.getClass().getSimpleName());
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove(observer);
        System.out.println("Наблюдатель отписан: " + observer.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers(String currency, double rate) {
        for (IObserver observer : observers) {
            observer.update(currency, rate);
        }
    }

    public void setRate(String currency, double rate) {
        System.out.printf("%nКурс %s изменился: %.2f тенге%n%n", currency, rate);
        notifyObservers(currency, rate);
    }
}

// Наблюдатель 1 — Банк
class BankObserver implements IObserver {
    private String bankName;

    public BankObserver(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public void update(String currency, double rate) {
        System.out.printf("[Банк %s] Обновляю внутренний курс %s = %.2f тенге%n", bankName, currency, rate);
    }
}

// Наблюдатель 2 — Онлайн-обменник
class OnlineExchangerObserver implements IObserver {
    private String exchangerName;

    public OnlineExchangerObserver(String exchangerName) {
        this.exchangerName = exchangerName;
    }

    @Override
    public void update(String currency, double rate) {
        System.out.printf("[%s] Курс %s составил %.2f тенге%n", exchangerName, currency, rate);
    }
}

// Наблюдатель 3 — Новостное агентство
class NewsAgencyObserver implements IObserver {
    @Override
    public void update(String currency, double rate) {
        System.out.printf("[Новости] Курс %s составил %.2f тенге!%n%n", currency, rate);
    }
}

// Клиентский код
public class Task2 {
    public static void main(String[] args) {
        CurrencyExchange exchange = new CurrencyExchange();

        BankObserver bank = new BankObserver("KaspiBank");
        OnlineExchangerObserver exchanger = new OnlineExchangerObserver("BestExchange");
        NewsAgencyObserver news = new NewsAgencyObserver();

        exchange.subscribe(bank);
        exchange.subscribe(exchanger);
        exchange.subscribe(news);

        exchange.setRate("USD", 89.50);
        exchange.setRate("EUR", 97.30);

        exchange.unsubscribe(exchanger);

        exchange.setRate("USD", 92.10);
    }
}