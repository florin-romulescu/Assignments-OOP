package fileio.output;

public class Output {
    private String command;

    public Output(String command) {
        this.command = command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public int getPlayerIndex() {
        return 0;
    }
}
