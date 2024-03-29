package command.parten;

public class RemoteControl {
    Command [] onCommands;
    Command [] offCommands;
    Command undoCommand;
    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];
        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }
    public void setCommands(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;

    }
    public void onButtonWasPushed(int slot) {
        onCommands[slot].extcute();
        undoCommand = onCommands[slot];
    }
    public void offButtonWasPushed(int slot) {
        offCommands[slot].extcute();
        undoCommand = offCommands[slot];
    }
    public void undoButtonPushed() {
        undoCommand.undo();
    }
}
