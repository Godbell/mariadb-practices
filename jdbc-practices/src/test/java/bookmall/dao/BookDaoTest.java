package bookmall.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import bookmall.vo.BookVo;
import bookmall.vo.CategoryVo;
public class BookDaoTest {
    private static CategoryVo mockCategoryVo01 = new CategoryVo("인문");
    private static CategoryVo mockCategoryVo02 = new CategoryVo("컴퓨터/IT");
    private static CategoryVo mockCategoryVo03 = new CategoryVo("예술");
    private static CategoryDao categoryDao = new CategoryDao();

    private static BookVo mockBookVo01 = new BookVo("과학혁명의 구조", 20000);
    private static BookVo mockBookVo02 = new BookVo("J2EE Development Without EJB", 32000);
    private static BookVo mockBookVo03 = new BookVo("서양미술사", 50000);
    private static BookDao bookDao = new BookDao();

    @BeforeAll
    public static void setUp() {
        categoryDao.insert(mockCategoryVo01);
        categoryDao.insert(mockCategoryVo02);
        categoryDao.insert(mockCategoryVo03);

        mockBookVo01.setCategoryNo(mockCategoryVo01.getNo());
        bookDao.insert(mockBookVo01);
        mockBookVo02.setCategoryNo(mockCategoryVo02.getNo());
        bookDao.insert(mockBookVo02);
        mockBookVo03.setCategoryNo(mockCategoryVo03.getNo());
        bookDao.insert(mockBookVo03);
    }

    @Test
    public void hollow() {
    }

    @AfterAll
    public static void cleanUp() {
        // 서적
        bookDao.deleteByNo(mockBookVo01.getNo());
        bookDao.deleteByNo(mockBookVo02.getNo());
        bookDao.deleteByNo(mockBookVo03.getNo());

        // 카테고리
        categoryDao.deleteByNo(mockCategoryVo01.getNo());
        categoryDao.deleteByNo(mockCategoryVo02.getNo());
        categoryDao.deleteByNo(mockCategoryVo03.getNo());
    }
}
