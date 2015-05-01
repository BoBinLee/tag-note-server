package kr.tagnote.user;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);
	@Transactional
	public Long deleteByEmail(String email);
	
	@Query("SELECT u IS NOT NULL FROM User u WHERE u.uid = :uid")
	public Boolean isExistsByUid(@Param("uid") String uid);
}
