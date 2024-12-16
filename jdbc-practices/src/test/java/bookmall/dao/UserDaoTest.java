package bookmall.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import bookmall.vo.UserVo;
public class UserDaoTest {
    private static UserVo mockUserVo01 = new UserVo("데스트유저01", "test01@test.com", "1234", "010-0000-0000");
    private static UserVo mockUserVo02 = new UserVo("데스트유저02", "test02@test.com", "1234", "010-1111-1111");
    private static UserDao userDao = new UserDao();

    @BeforeAll
    public static void setUp() {
        // 사용자 추가(2명)
        userDao.insert(mockUserVo01);
        userDao.insert(mockUserVo02);
    }

    @Test
    public void testUser() {
        Assertions.assertEquals(2, userDao.findAll().size());
    }

    @AfterAll
    public static void cleanUp() {
        userDao.deleteByNo(mockUserVo01.getNo());
        userDao.deleteByNo(mockUserVo02.getNo());
    }
}
