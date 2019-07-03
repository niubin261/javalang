package command.parten;

public class LightOnCommand implements Command {
    Light light;
    public LightOnCommand(Light light) {
        this.light = light;
    }
    @Override
    public void extcute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}
