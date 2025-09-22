# RGPV_Bajaj_round1_Hrishabh
# BFHL Hiring Task - Hrishabh Gupta

## 📌 Details
- **Name:** Hrishabh Gupta  
- **RegNo:** 0002CD221026  
- **Email:** hrishabh1104@gmail.com  
- **Assigned Question:** Question 2 (Even roll no.)  

---

## 📖 Final SQL Query
```sql
SELECT e.EMP_ID,
       e.FIRST_NAME,
       e.LAST_NAME,
       d.DEPARTMENT_NAME,
       COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT
FROM EMPLOYEE e
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
LEFT JOIN EMPLOYEE e2 ON e.DEPARTMENT = e2.DEPARTMENT
                      AND e2.DOB > e.DOB
GROUP BY e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME
ORDER BY e.EMP_ID DESC;


## 📖 Final SQL Query for Even
```sql
SELECT
    e1.EMP_ID,
    e1.FIRST_NAME,
    e1.LAST_NAME,
    d.DEPARTMENT_NAME,
    COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT
FROM
    EMPLOYEE e1
JOIN
    DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID
LEFT JOIN
    EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e1.DOB < e2.DOB
GROUP BY
    e1.EMP_ID,
    e1.FIRST_NAME,
    e1.LAST_NAME,
    d.DEPARTMENT_NAME
ORDER BY
    e1.EMP_ID DESC;
