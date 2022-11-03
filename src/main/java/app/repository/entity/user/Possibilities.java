package app.repository.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "possibilities")
public class Possibilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "watch_users")
    private boolean watchUsers;

    @Column(name = "delete_users")
    private boolean deleteUsers;

    @Column(name = "change_role_users")
    private boolean changeRoleUsers;

    @Column(name = "change_users_parameters")
    private boolean changeUsersParameters;

    @Column(name = "create_courses")
    private boolean createCourses;

    @Column(name = "delete_courses")
    private boolean deleteCourses;

    @Column(name = "change_courses")
    private boolean changeCourses;

    @Column(name = "watch_deleted_courses")
    private boolean watchDeletedCourses;

    @Column(name = "watch_users_orders")
    private boolean watchUsersOrders;

    @Column(name = "change_orders_status")
    private boolean changeOrderStatus;

    @Column(name = "create_lessons")
    private boolean createLessons;

    @Column(name = "watch_lessons")
    private boolean watchLessons;

    @Column(name = "watch_deleted_lessons")
    private boolean watchDeletedLessons;

    @Column(name = "delete_lessons")
    private boolean deleteLessons;

    @Column(name = "change_lessons")
    private boolean changeLessons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Possibilities that = (Possibilities) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
