-- 집계(통계) SQL 문제입니다.

-- 문제 1. 
-- 최고임금(salary)과  최저임금을 "최고임금", "최저임금" 프로젝션 타이틀로 함께 출력하세요.
-- 그리고 두 임금의 차이는 얼마인가요? "최고임금 – 최저임금" 이란 타이틀로 출력하세요.

SELECT
	MAX(salary) AS "최고임금",
	MIN(salary) AS "최저임금",
	MAX(salary) - MIN(salary) AS "최고임금 - 최저임금"
FROM salaries;

-- 문제2.
-- 마지막으로 신입사원이 들어온 날은 언제 입니까? 다음 형식으로 출력하세요
-- 예) 2014년 07월 10일
SELECT 
	DATE_FORMAT(hire_date, "%Y년 %m월 %d일") AS "마지막으로 신입사원이 들어온 날"
FROM employees
ORDER BY hire_date DESC
LIMIT 1;

-- 문제3.
-- 가장 오래 근속한 직원의 입사일은 언제인가요? 다음 형식으로 출력하세요.
-- 예) 2014년 07월 10일
SELECT 
	DATE_FORMAT(hire_date, "%Y년 %m월 %d일") AS "최장근속자 입사일"
FROM employees
ORDER BY hire_date ASC
LIMIT 1;

-- 문제4.
-- 현재, 이 회사의 평균 연봉은 얼마입니까?
SELECT AVG(salary) AS "평균 연봉"
FROM salaries
WHERE to_date = '9999-01-01';

-- 문제5.
-- 현재, 이 회사의 최고/최저 연봉은 각각 얼마입니까?
SELECT
	MAX(salary) AS "최고 연봉",
	MIN(salary) AS "최저 연봉"
FROM salaries
WHERE to_date = '9999-01-01';

-- 문제6.
-- 현재, 근무중인 사원 중 나이가 제일 어린 사원과 제일 많은 사원의 나이를 각각 출력하세요.
SELECT
	YEAR(CURRENT_TIMESTAMP) - YEAR(MIN(birth_date)) AS "최연장자 나이",
	YEAR(CURRENT_TIMESTAMP) - YEAR(MAX(birth_date)) AS "최연소자 나이"
FROM employees;
