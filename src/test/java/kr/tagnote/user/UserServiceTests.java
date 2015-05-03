package kr.tagnote.user;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import kr.tagnote.Application;
import kr.tagnote.util.CommonUtils;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserServiceTests {
	@Autowired
	UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void deleteByEmail(){
		assertTrue(userService.deleteByEmail("admin11@naver.com"));
	}
	
	@Test
	public void saveUser(){
		User user = new User();
		
		user.setEmail("admin11@naver.com");
		user.setPassword(passwordEncoder.encode("1234"));
		user.setAuth(new Auth(){{ setAuthId(1);}}); 
		// authRepository.findOne(1)
		user = userService.saveUser(user);
		System.out.println(user);
//		assertNotEquals(0, user.getUserId());
	}
	
	@Test
	public void findByUid(){
		assertNull(userService.findByUid("1"));
	}
	
	@Test
	public void isExistsByUid(){
		assertNull(userService.isExistsByUid("1DbTeNcrdI"));
	}
	
	@Test
	public void getRandomId(){
		assertNotNull(CommonUtils.getRandomId());
	}
}
