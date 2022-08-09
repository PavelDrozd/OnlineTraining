package App.controller.factory;

import App.controller.Command;
import App.controller.commands.course.CourseCommand;
import App.controller.commands.course.CoursesCommand;
import App.controller.commands.error.ErrorCommand;
import App.controller.commands.order.OrderCommand;
import App.controller.commands.order.OrdersCommand;
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
    LOGIN_FORM(new LoginFormCommand()),
    LOGIN(new LoginCommand(ServiceFactory.INSTANCE.getService(UserService.class))),
    LOGOUT(new LogoutCommand()),

    //COURSE COMMANDS
    COURSE(new CourseCommand(ServiceFactory.INSTANCE.getService(CourseService.class))),
    COURSES(new CoursesCommand(ServiceFactory.INSTANCE.getService(CourseService.class))),

    //ORDERS COMMAND
    ORDER(new OrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class))),
    ORDERS(new OrdersCommand(ServiceFactory.INSTANCE.getService(OrderService.class))),


    ;//
    private final Command command;

    CommandRegister(Command command){
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }

}
