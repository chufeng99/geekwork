import assemble02.Teacher;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml装配方式
 */
public class Sprint02Test {
    
    @Test
    public void Test(){
        ClassPathXmlApplicationContext context =new ClassPathXmlApplicationContext(
                "assemble02/applicationContext.xml");
        Teacher teacher = (Teacher) context.getBean("teacher");
        teacher.teach();
    }
}
