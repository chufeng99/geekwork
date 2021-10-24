package assemble01;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component("school")
public class School implements ISchool {
    
    // Resource 
//    @Autowired(required = true) //primary
//    Klass class1;
    
//    @Resource(name = "student100")
//    Student student100;
    
    @Override
    public void ding(){
    
//        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
        System.out.println("school ding !!!!");
    }
    
}
