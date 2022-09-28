package App.controller.factory;

import App.controller.commands.Command;

public enum CommandFactory {
    INSTANCE;

    CommandFactory() {
    }

    public CommandRegister.SecurityLevel getSecurityLevel(String command) {

        CommandRegister.SecurityLevel levelInstance = CommandRegister.valueOf(command.toUpperCase()).getSecurityLevel();
        if (levelInstance == null) {
            levelInstance = CommandRegister.valueOf("USER").getSecurityLevel();
        }
        return levelInstance;
    }

    public Command getCommand(String command) {
        Command commandInstance = CommandRegister.valueOf(command.toUpperCase()).getCommand();
        if (commandInstance == null) {
            commandInstance = CommandRegister.valueOf("ERROR").getCommand();
        }
        return commandInstance;
    }

}
