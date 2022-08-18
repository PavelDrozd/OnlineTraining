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
    ERROR(new ErrorCommand()),

    //USER COMMANDS
    USER(new UserCommand(ServiceFactory.INSTANCE.getService(UserService.class))),
    USERS(new UsersCommand(ServiceFactory.INSTANCE.getService(UserService.class))),
    CREATE_USER_FORM(new CreateUserFormCommand()),
    CREATE_USER(new CreateUserCommand(ServiceFactory.INSTANCE.getService(UserService.class))),
    LOGIN_FORM(new LoginFormCommand()),
    LOGIN(new LoginCommand(ServiceFactory.INSTANCE.getService(UserService.class))),
    LOGOUT(new LogoutCommand()),

    //USER PROFILE
    PROFILE(new ProfileCommand()),
    EDIT_NAME_FORM(new EditNameFormCommand()),
    EDIT_NAME(new EditNameCommand(ServiceFactory.INSTANCE.getService(UserService.class))),
    EDIT_AGE_FORM(new EditAgeFormCommand()),
    EDIT_AGE(new EditAgeCommand(ServiceFactory.INSTANCE.getService(UserService.class))),
    EDIT_EMAIL_FORM(new EditEmailFormCommand()),
    EDIT_EMAIL(new EditEmailCommand(ServiceFactory.INSTANCE.getService(UserService.class))),
    EDIT_PASSWORD_FORM(new EditPasswordFormCommand()),
    EDIT_PASSWORD(new EditPasswordCommand(ServiceFactory.INSTANCE.getService(UserService.class))),

    //COURSE COMMANDS
    COURSE(new CourseCommand(ServiceFactory.INSTANCE.getService(CourseService.class))),
    COURSES(new CoursesCommand(ServiceFactory.INSTANCE.getService(CourseService.class))),

    //ORDERS COMMAND
    ORDER(new OrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class))),
    ORDERS(new OrdersCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));//
    private final Command command;

    CommandRegister(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

}
