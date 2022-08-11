package App.controller.factory;

import App.controller.Command;

public enum CommandFactory {
    INSTANCE;

    CommandFactory() {
    }

    public Command getCommand(String command) {
        Command commandInstance = CommandRegister.valueOf(command.toUpperCase()).getCommand();
        if (commandInstance == null) {
            commandInstance = CommandRegister.valueOf("ERROR").getCommand();
        }
        return commandInstance;
    }

}
