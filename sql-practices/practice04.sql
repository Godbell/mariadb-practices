-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 단 조회결과는 급여의 내림차순으로 정렬되어 나타나야 합니다. 

-- 문제1.
-- 현재 전체 사원의 평균 급여보다 많은 급여를 받는 사원은 몇 명이나 있습니까?

SELECT COUNT(employees.emp_no) AS "평균급여 초과 수급자 수"
FROM employees
LEFT JOIN salaries
	ON employees.emp_no = salaries.emp_no
WHERE salary > (
		SELECT
			AVG(salary) AS avg_salary
		FROM salaries
		WHERE salaries.to_date = '9999-01-01'
		)
	AND salaries.to_date = '9999-01-01'
ORDER BY salary DESC;

-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.

SELECT
	employees.emp_no AS "사번",
	CONCAT(first_name, ' ', last_name) AS "이름"
FROM dept_emp
LEFT JOIN employees
	ON dept_emp.emp_no = employees.emp_no
LEFT JOIN salaries
	ON dept_emp.emp_no = salaries.emp_no
LEFT JOIN departments
	ON dept_emp.dept_no = departments.dept_no
WHERE dept_emp.to_date = '9999-01-01'
	AND titles.to_date = '9999-01-01'

-- 문제3.
-- 현재, 사원 자신들의 부서의 평균급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요 
        
-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.

-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.

-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.