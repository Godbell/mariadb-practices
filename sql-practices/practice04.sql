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
ORDER BY salary DESC
;

-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.

SELECT
	dept_emp.dept_no AS "사번",
    CONCAT(first_name, ' ', last_name) AS "이름",
	salary AS "급여",
    dept_name AS "부서"
FROM dept_emp
LEFT JOIN salaries
	ON dept_emp.emp_no = salaries.emp_no
LEFT JOIN departments
	ON dept_emp.dept_no = departments.dept_no
LEFT JOIN employees
	ON dept_emp.emp_no = employees.emp_no
WHERE
	dept_emp.to_date = '9999-01-01'
	AND salaries.to_date = '9999-01-01'
	AND salary = (
		SELECT MAX(salary) FROM salaries 
	)
GROUP BY dept_emp.dept_no
ORDER BY salary DESC
;

-- 문제3.
-- 현재, 사원 자신들의 부서의 평균급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요 
SELECT
	employees.emp_no AS "사번",
    CONCAT(first_name, ' ', last_name) AS "이름",
	salaries_main.salary AS "급여"
FROM employees
LEFT JOIN dept_emp dept_emp_main
	ON employees.emp_no = dept_emp_main.emp_no
LEFT JOIN salaries salaries_main
	ON employees.emp_no = salaries_main.emp_no
WHERE dept_emp_main.to_date = '9999-01-01'
	AND salaries_main.to_date = '9999-01-01'
	AND dept_emp_main.dept_no = 'd002'
	AND salary > (
		SELECT AVG(salary)
		FROM salaries salaries_sub_0
		LEFT JOIN dept_emp dept_emp_sub_0
			ON dept_emp_sub_0.emp_no = salaries_sub_0.emp_no
		WHERE dept_emp_sub_0.dept_no = dept_emp_main.dept_no
			AND dept_emp_sub_0.to_date = '9999-01-01'
			AND salaries_sub_0.to_date = '9999-01-01'
	)
ORDER BY salary DESC
;

-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.
SELECT
	employees_employee.emp_no AS "사원 사번",
	CONCAT(employees_employee.first_name, ' ', employees_employee.last_name) AS "사원 이름",
	CONCAT(employees_manager.first_name, ' ', employees_manager.last_name) AS "매니저 이름",
	departments.dept_name AS "부서 이름"
FROM employees employees_employee
LEFT JOIN dept_emp dept_emp_employee
	ON employees_employee.emp_no = dept_emp_employee.emp_no
LEFT JOIN departments
	ON dept_emp_employee.dept_no = departments.dept_no
LEFT JOIN dept_manager
	ON dept_emp_employee.dept_no = dept_manager.dept_no
LEFT JOIN employees employees_manager
	ON dept_manager.emp_no = employees_manager.emp_no
WHERE dept_emp_employee.to_date = '9999-01-01'
	AND dept_manager.to_date = '9999-01-01'
;

-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.
SELECT
	employees.emp_no AS "사번",
    CONCAT(first_name, ' ', last_name) AS "이름",
    title AS "직책",
	salaries.salary AS "급여"
FROM employees
LEFT JOIN titles
	ON employees.emp_no = titles.emp_no
LEFT JOIN salaries
	ON employees.emp_no = salaries.emp_no
LEFT JOIN dept_emp
	ON employees.emp_no = dept_emp.emp_no
WHERE titles.to_date = '9999-01-01'
	AND salaries.to_date = '9999-01-01'
	AND dept_emp.to_date = '9999-01-01'
	AND dept_emp.dept_no = (SELECT dept_no FROM (
		SELECT
			AVG(salaries_sub_0.salary) as avg_salary
		FROM dept_emp
			LEFT JOIN salaries salaries_sub_0
				ON dept_emp.emp_no = salaries_sub_0.emp_no
		GROUP BY dept_emp.dept_no
		ORDER BY avg_salary DESC
		LIMIT 0, 1
	) t)
ORDER BY salary DESC
;

-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.
SELECT
	departments.dept_name AS "부서이름",
	AVG(salaries.salary) AS "평균급여"
FROM departments
LEFT JOIN dept_emp
	ON departments.dept_no = dept_emp.dept_no
LEFT JOIN salaries
	ON dept_emp.emp_no = salaries.emp_no
WHERE salaries.to_date = '9999-01-01'
	AND dept_emp.to_date = '9999-01-01'
	AND dept_emp.dept_no = (SELECT dept_emp.dept_no FROM (
		SELECT
			AVG(salaries_sub_0.salary) avg_salary
		FROM dept_emp dept_emp_sub_0
			LEFT JOIN salaries salaries_sub_0
				ON dept_emp_sub_0.emp_no = salaries_sub_0.emp_no
		GROUP BY dept_emp_sub_0.dept_no
		ORDER BY avg_salary DESC
		LIMIT 0, 1
	) t)
ORDER BY salary DESC
;

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.
SELECT
	title AS "타이틀",
	AVG(salary) AS "평균급여"
FROM titles
LEFT JOIN salaries ON titles.emp_no = salaries.emp_no
WHERE titles.to_date = '9999-01-01'
	AND salaries.to_date = '9999-01-01'
	AND title = (SELECT title FROM (
		SELECT
			title,
			AVG(salary) AS avg_salary
		FROM titles
		LEFT JOIN salaries ON titles.emp_no = salaries.emp_no
		GROUP BY title
		ORDER BY avg_salary DESC
		LIMIT 0, 1
	) t)
;