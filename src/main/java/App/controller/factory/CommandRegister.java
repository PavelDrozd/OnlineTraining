package App.controller.factory;

import App.controller.Command;
import App.controller.commands.error.ErrorCommand;

public enum CommandRegister {
    //ERROR COMMANDS
    ERROR(new ErrorCommand()),


    ;//
    private final Command command;

    CommandRegister(Command command){
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }

}
