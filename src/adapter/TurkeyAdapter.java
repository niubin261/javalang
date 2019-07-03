package adapter;

public class TurkeyAdapter implements Duck {
    Truksy truksy;
    public  TurkeyAdapter(Truksy truksy) {
        this.truksy = truksy;
    }
    @Override
    public void quack() {
        truksy.gobble();
    }

    @Override
    public void fly() {
        truksy.fly();
    }
}

