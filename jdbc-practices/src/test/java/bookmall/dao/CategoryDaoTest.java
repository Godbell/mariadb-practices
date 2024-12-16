package bookmall.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import bookmall.vo.CategoryVo;
public class CategoryDaoTest {
    private static CategoryVo mockCategoryVo01 = new CategoryVo("인문");
    private static CategoryVo mockCategoryVo02 = new CategoryVo("컴퓨터/IT");
    private static CategoryVo mockCategoryVo03 = new CategoryVo("예술");
    private static CategoryDao categoryDao = new CategoryDao();

    @BeforeAll
    public static void setUp() {
        categoryDao.insert(mockCategoryVo01);
        categoryDao.insert(mockCategoryVo02);
        categoryDao.insert(mockCategoryVo03);
    }

    @Test
    public void testCategory() {
        assertEquals(3, categoryDao.findAll().size());
    }

    @AfterAll
    public static void cleanUp() {
        categoryDao.deleteByNo(mockCategoryVo01.getNo());
        categoryDao.deleteByNo(mockCategoryVo02.getNo());
        categoryDao.deleteByNo(mockCategoryVo03.getNo());
    }
}
