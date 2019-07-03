package command.parten;

public class LightOffCommand implements Command {
    Light light;
    public LightOffCommand(Light light) {
        this.light = light;
    }
    @Override
    public void undo() {
        light.on();
    }

    @Override
    public void extcute() {
        light.off();
    }
}
