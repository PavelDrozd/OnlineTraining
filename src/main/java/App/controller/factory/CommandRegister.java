package App.controller.factory;

import App.controller.commands.Command;
import App.controller.commands.error.ErrorCommand;
import App.controller.commands.user.CreateUserFormCommand;
import App.controller.commands.user.edit.EditAgeFormCommand;
import App.controller.commands.user.edit.EditEmailFormCommand;
import App.controller.commands.user.edit.EditNameFormCommand;
import App.controller.commands.user.edit.EditPasswordFormCommand;
import App.controller.commands.user.profile.ProfileCommand;
import App.controller.commands.user.session.LoginFormCommand;
import App.controller.commands.user.session.LogoutCommand;
import org.springframework.stereotype.Component;

@Component
public enum CommandRegister {
    //ERROR COMMANDS
    ERROR(new ErrorCommand(), SecurityLevel.USER),

    //USER COMMANDS
    CREATE_USER_FORM(new CreateUserFormCommand(), SecurityLevel.USER),
    LOGIN_FORM(new LoginFormCommand(), SecurityLevel.USER),
    LOGOUT(new LogoutCommand(), SecurityLevel.USER),

    //USER PROFILE
    PROFILE(new ProfileCommand(), SecurityLevel.USER),
    EDIT_NAME_FORM(new EditNameFormCommand(), SecurityLevel.USER),
    EDIT_AGE_FORM(new EditAgeFormCommand(), SecurityLevel.USER),
    EDIT_EMAIL_FORM(new EditEmailFormCommand(), SecurityLevel.USER),
    EDIT_PASSWORD_FORM(new EditPasswordFormCommand(), SecurityLevel.USER),

    ;//

    private final Command command;
    private final SecurityLevel securityLevel;

    CommandRegister(Command command, SecurityLevel securityLevel) {
        this.command = command;
        this.securityLevel = securityLevel;
    }

    public enum SecurityLevel {
        USER, EMPLOYEE, ADMIN
    }

    Command getCommand() {
        return command;
    }

    SecurityLevel getSecurityLevel() {
        return securityLevel;
    }

}
