-- 테이블 조인(JOIN) SQL 문제입니다.

-- 문제 1. 
-- 현재 급여가 많은 직원부터 직원의 사번, 이름, 그리고 연봉을 출력 하시오.
SELECT
	employees.emp_no as "사번",
	CONCAT(first_name, ' ', last_name) as "이름",
	salary as "연봉"
FROM employees
LEFT JOIN salaries
	ON employees.emp_no = salaries.emp_no
WHERE salaries.to_date = '9999-01-01'
ORDER BY salary DESC;

-- 문제2.
-- 전체 사원의 사번, 이름, 현재 직책을 이름 순서로 출력하세요.
SELECT
	employees.emp_no as "사번",
	CONCAT(first_name, ' ', last_name) as "이름",
	title as "현재 직책"
FROM employees
LEFT JOIN titles
	ON employees.emp_no = titles.emp_no
WHERE titles.to_date='9999-01-01'
ORDER BY CONCAT(first_name, ' ', last_name) ASC;

-- 문제3.
-- 전체 사원의 사번, 이름, 현재 부서를 이름 순서로 출력하세요.
SELECT
	employees.emp_no as "사번",
	CONCAT(first_name, ' ', last_name) as "이름",
	dept_name as "현재 부서"
FROM dept_emp
LEFT JOIN employees
	ON dept_emp.emp_no = employees.emp_no
LEFT JOIN departments
	ON dept_emp.dept_no = departments.dept_no
WHERE dept_emp.to_date='9999-01-01'
ORDER BY CONCAT(first_name, ' ', last_name) ASC;


-- 문제4.
-- 현재 근무중인 전체 사원의 사번, 이름, 연봉, 직책, 부서를 모두 이름 순서로 출력합니다.
SELECT
	employees.emp_no as "사번",
	CONCAT(first_name, ' ', last_name) as "이름",
	salary as "연봉",
	title as "직책",
	dept_name as "부서"
FROM dept_emp
LEFT JOIN salaries
	ON dept_emp.emp_no = salaries.emp_no
LEFT JOIN employees
	ON dept_emp.emp_no = employees.emp_no
LEFT JOIN titles
	ON dept_emp.emp_no = titles.emp_no
LEFT JOIN departments
	ON dept_emp.dept_no = departments.dept_no
WHERE dept_emp.to_date = '9999-01-01'
	AND salaries.to_date = '9999-01-01'
	AND titles.to_date = '9999-01-01'
ORDER BY CONCAT(first_name, ' ', last_name) ASC;

-- 문제5.
-- 'Technique Leader'의 직책으로 과거에 근무한 적이 있는 모든 사원의 사번과 이름을 출력하세요.
-- (현재 'Technique Leader'의 직책으로 근무하는 사원은 고려하지 않습니다.)
SELECT DISTINCT
	employees.emp_no as "사번",
	CONCAT(first_name, ' ', last_name) as "이름"
FROM employees
LEFT JOIN titles
	ON employees.emp_no = titles.emp_no
WHERE title = "Technique Leader"
	AND NOT titles.to_date = '9999-01-01';

-- 문제6.
-- 직원 이름(last_name) 중에서 S로 시작하는 직원들의 이름, 부서명, 직책을 조회하세요.
SELECT
	employees.emp_no as "사번",
	CONCAT(first_name, ' ', last_name) as "이름",
	dept_name as "부서명",
	title as "직책"
FROM dept_emp
LEFT JOIN employees
	ON dept_emp.emp_no = employees.emp_no
LEFT JOIN titles
	ON dept_emp.emp_no = titles.emp_no
LEFT JOIN departments
	ON dept_emp.dept_no = departments.dept_no
WHERE dept_emp.to_date = '9999-01-01'
	AND titles.to_date = '9999-01-01'
	AND last_name LIKE 'S%';

-- 문제7.
-- 현재, 직책이 Engineer인 사원 중에서 현재 급여가 40,000 이상인 사원들의 사번, 이름, 급여 그리고 타이틀을 급여가 큰 순서대로 출력하세요.

-- 문제8.
-- 현재, 평균급여가 50,000이 넘는 직책을 직책과 평균급여을 평균급여가 큰 순서대로 출력하세요.

-- 문제9.
-- 현재, 부서별 평균급여을 평균급여가 큰 순서대로 부서명과 평균연봉을 출력 하세요.

-- 문제10.
-- 현재, 직책별 평균급여를 평균급여가 큰 직책 순서대로 직책명과 그 평균연봉을 출력 하세요.
