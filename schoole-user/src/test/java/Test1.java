import org.junit.Test;
import org.schoole.mapper.MenuMapper;
import org.schoole.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
public class Test1 {
    @Resource
    MenuService menuService ;

    @Resource
    MenuMapper menuMapper ;

    @Test
    public void test(){
        System.out.println(menuMapper.getMenuListByUserId(22,0));
    }

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder ;
    @Test
    public void test2(){
        System.out.println(bCryptPasswordEncoder.encode("password2"));
    }
}
