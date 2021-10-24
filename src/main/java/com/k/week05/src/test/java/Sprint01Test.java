import assemble01.AutoConfig;
import assemble01.School;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 自动装配方式
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:week05/applicationContext.xml"})
@ContextConfiguration(classes = AutoConfig.class)
public class Sprint01Test {
    
    @Autowired
    private School school;
    
    @Test
    public void SchoolTest(){
        school.ding();
    }
}
