package App.controller.factory;

import App.controller.Command;
import App.controller.commands.course.CourseCommand;
import App.controller.commands.course.CoursesCommand;
import App.controller.commands.error.ErrorCommand;
import App.controller.commands.order.OrderCommand;
import App.controller.commands.order.OrdersCommand;
import App.controller.commands.user.CreateUserCommand;
import App.controller.commands.user.CreateUserFormCommand;
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
import App.controller.commands.user.UserCommand;
import App.controller.commands.user.UsersCommand;
import App.controller.commands.user.session.LogoutCommand;
import App.service.CourseService;
import App.service.OrderService;
import App.service.factory.ServiceFactory;
import App.service.UserService;

public enum CommandRegister {
    //ERROR COMMANDS
    ERROR(new ErrorCommand(), SecurityLevel.USER),

    //USER COMMANDS
    USER(new UserCommand(ServiceFactory.INSTANCE.getService(UserService.class)), SecurityLevel.USER),
    USERS(new UsersCommand(ServiceFactory.INSTANCE.getService(UserService.class)), SecurityLevel.ADMIN),
    CREATE_USER_FORM(new CreateUserFormCommand(), SecurityLevel.USER),
    CREATE_USER(new CreateUserCommand(ServiceFactory.INSTANCE.getService(UserService.class)), SecurityLevel.USER),
    LOGIN_FORM(new LoginFormCommand(), SecurityLevel.USER),
    LOGIN(new LoginCommand(ServiceFactory.INSTANCE.getService(UserService.class)), SecurityLevel.USER),
    LOGOUT(new LogoutCommand(), SecurityLevel.USER),

    //USER PROFILE
    PROFILE(new ProfileCommand(), SecurityLevel.USER),
    EDIT_NAME_FORM(new EditNameFormCommand(), SecurityLevel.USER),
    EDIT_NAME(new EditNameCommand(ServiceFactory.INSTANCE.getService(UserService.class)), SecurityLevel.USER),
    EDIT_AGE_FORM(new EditAgeFormCommand(), SecurityLevel.USER),
    EDIT_AGE(new EditAgeCommand(ServiceFactory.INSTANCE.getService(UserService.class)), SecurityLevel.USER),
    EDIT_EMAIL_FORM(new EditEmailFormCommand(), SecurityLevel.USER),
    EDIT_EMAIL(new EditEmailCommand(ServiceFactory.INSTANCE.getService(UserService.class)), SecurityLevel.USER),
    EDIT_PASSWORD_FORM(new EditPasswordFormCommand(), SecurityLevel.USER),
    EDIT_PASSWORD(new EditPasswordCommand(ServiceFactory.INSTANCE.getService(UserService.class)), SecurityLevel.USER),

    //COURSE COMMANDS
    COURSE(new CourseCommand(ServiceFactory.INSTANCE.getService(CourseService.class)), SecurityLevel.USER),
    COURSES(new CoursesCommand(ServiceFactory.INSTANCE.getService(CourseService.class)), SecurityLevel.USER),

    //ORDERS COMMAND
    ORDER(new OrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class)), SecurityLevel.USER),
    ORDERS(new OrdersCommand(ServiceFactory.INSTANCE.getService(OrderService.class)), SecurityLevel.ADMIN);//

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
