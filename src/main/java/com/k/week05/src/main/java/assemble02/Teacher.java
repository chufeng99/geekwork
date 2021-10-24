package assemble02;

public class Teacher {
    private Course course;

    public Teacher(Course course) {
        this.course = course;
    }

    public void teach() {
        System.out.println("我教的课程：" + course.courseName + " 绩点为：" + course.mark);
    }
}
