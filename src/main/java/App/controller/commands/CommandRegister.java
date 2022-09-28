package App.controller.commands;

import App.controller.commands.course.CourseCommand;
import App.controller.commands.course.CoursesCommand;
import App.controller.commands.error.ErrorCommand;
import App.controller.commands.order.OrderCommand;
import App.controller.commands.order.OrdersCommand;
import App.controller.commands.user.CreateUserCommand;
import App.controller.commands.user.CreateUserFormCommand;
import App.controller.commands.user.UserCommand;
import App.controller.commands.user.UsersCommand;
import App.controller.commands.user.edit.EditAgeCommand;
import App.controller.commands.user.edit.EditAgeFormCommand;
import App.controller.commands.user.edit.EditEmailCommand;
import App.controller.commands.user.edit.EditEmailFormCommand;
import App.controller.commands.user.edit.EditNameCommand;
import App.controller.commands.user.edit.EditNameFormCommand;
import App.controller.commands.user.edit.EditPasswordCommand;
import App.controller.commands.user.edit.EditPasswordFormCommand;
import App.controller.commands.user.profile.ProfileCommand;
import App.controller.commands.user.session.LoginCommand;
import App.controller.commands.user.session.LoginFormCommand;
import App.controller.commands.user.session.LogoutCommand;



public enum CommandRegister {
    //ERROR COMMANDS
    ERROR(ErrorCommand.class, SecurityLevel.USER),

    //USER COMMANDS
    USER(UserCommand.class, SecurityLevel.USER),
    USERS(UsersCommand.class, SecurityLevel.ADMIN),
    CREATE_USER_FORM(CreateUserFormCommand.class, SecurityLevel.USER),
    CREATE_USER(CreateUserCommand.class, SecurityLevel.USER),
    LOGIN_FORM(LoginFormCommand.class, SecurityLevel.USER),
    LOGIN(LoginCommand.class, SecurityLevel.USER),
    LOGOUT(LogoutCommand.class, SecurityLevel.USER),

    //USER PROFILE
    PROFILE(ProfileCommand.class, SecurityLevel.USER),
    EDIT_NAME_FORM(EditNameFormCommand.class, SecurityLevel.USER),
    EDIT_NAME(EditNameCommand.class, SecurityLevel.USER),
    EDIT_AGE_FORM(EditAgeFormCommand.class, SecurityLevel.USER),
    EDIT_AGE(EditAgeCommand.class, SecurityLevel.USER),
    EDIT_EMAIL_FORM(EditEmailFormCommand.class, SecurityLevel.USER),
    EDIT_EMAIL(EditEmailCommand.class, SecurityLevel.USER),
    EDIT_PASSWORD_FORM(EditPasswordFormCommand.class, SecurityLevel.USER),
    EDIT_PASSWORD(EditPasswordCommand.class, SecurityLevel.USER),

    //COURSE COMMANDS
    COURSE(CourseCommand.class, SecurityLevel.USER),
    COURSES(CoursesCommand.class, SecurityLevel.USER),

    //ORDERS COMMAND
    ORDER(OrderCommand.class, SecurityLevel.USER),
    ORDERS(OrdersCommand.class, SecurityLevel.ADMIN);//
    ;//

    private final Class<? extends Command> commands;
    private final SecurityLevel securityLevel;

    CommandRegister(Class<? extends Command> commands, SecurityLevel securityLevel) {
        this.commands = commands;
        this.securityLevel = securityLevel;
    }

    public enum SecurityLevel {
        USER, EMPLOYEE, ADMIN
    }

    public static SecurityLevel getSecurityLevel(String command) {
        CommandRegister.SecurityLevel levelInstance = CommandRegister.valueOf(command.toUpperCase()).getSecurityLevel();
        if (levelInstance == null) {
            levelInstance = CommandRegister.valueOf("USER").getSecurityLevel();
        }
        return levelInstance;
    }

    public static Class<? extends Command> getCommand(String command) {
        Class<? extends Command> commandInstance = CommandRegister.valueOf(command.toUpperCase()).getCommands();
        if (commandInstance == null) {
            commandInstance = CommandRegister.valueOf("ERROR").getCommands();
        }
        return commandInstance;
    }

    private SecurityLevel getSecurityLevel(){
        return securityLevel;
    }

    private Class<? extends Command> getCommands(){
        return commands;
    }
}