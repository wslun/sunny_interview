package com.gigaspaces;

import com.gigaspaces.model.Student;
import com.gigaspaces.model.StudentKey;
import com.gigaspaces.model.StudentReport;
import com.j_spaces.core.IJSpace;
import org.junit.Before;
import org.junit.Test;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import java.util.logging.Logger;

/**
 * Created by kobi on 12/10/14.
 */
public class JoinTwoTablesCompositePrimaryKey {

    public static Logger logger = Logger.getLogger("JoinTwoTablesCompositePrimaryKey");
    public static final String url = "/./mySpace";

    static IJSpace space = null;
    static GigaSpace gigaSpace = null;

    @Before
    public void before() throws Exception {
            space = new UrlSpaceConfigurer(url).space();
            gigaSpace = new GigaSpaceConfigurer(space).gigaSpace();

//        +--------------------------------+---------------------------+-------+---------+--------+
//        | NAME                           | TITLE                     | CLASS | SECTION | ROLLID |
//        +--------------------------------+---------------------------+-------+---------+--------+
//        | Deepak                         | Saxana                    | V     | A       |     15 |
//        | Robert                         | Paul                      | VI    | A       |      2 |
//        | Danny                          | Moris                     | V     | B       |     15 |
//        +--------------------------------+---------------------------+-------+---------+--------+
            Student[] students = getStudents();

            gigaSpace.writeMultiple(students);

//        +-------+---------+--------+-------+----------+----------------+
//        | CLASS | SECTION | ROLLID | GRADE | SEMISTER | CLASS_ATTENDED |
//        +-------+---------+--------+-------+----------+----------------+
//        | V     | A       |     15 | A++   | 1St      |             75 |
//        | VI    | A       |      2 | A+    | 2Nd      |             70 |
//        | V     | B       |     15 | AA    | 1St      |             85 |
//        | VI    | A       |      2 | A+    | 1St      |             70 |
//        | V     | A       |     15 | AA    | 2Nd      |             85 |
//        +-------+---------+--------+-------+----------+----------------+

            StudentReport[] studentReports = getStudentReports();

            gigaSpace.writeMultiple(studentReports);
    }

        @Test
        public void test(){
//TODO write the following SQL query eith XAP SQL QUERY - http://docs.gigaspaces.com/xap100net/query-sql.html

//        SELECT a.name,a.title,a.class,a.section,
//                a.rollid,b.grade,b.semester
//        FROM student a, studentreport b
//        WHERE a.class=b.class
//        AND a.section=b.section
//        AND a.rollid=b.rollid;

    }

        private static StudentReport[] getStudentReports() {
                StudentReport[] studentReports = new StudentReport[5];

                StudentReport studentReport = new StudentReport();
                StudentKey key;
                key = new StudentKey();
                key.setClasss("V");
                key.setSection("A");
                key.setRollid(15);
                studentReport.setKey(key);
                studentReport.setGrade("A++");
                studentReport.setSemester("1St");
                studentReport.setClassAttended(75);
                studentReports[0] = studentReport;

                studentReport = new StudentReport();
                key = new StudentKey();
                key.setClasss("VI");
                key.setSection("A");
                key.setRollid(2);
                studentReport.setKey(key);
                studentReport.setGrade("A+");
                studentReport.setSemester("2Nd");
                studentReport.setClassAttended(70);
                studentReports[1] = studentReport;

                studentReport = new StudentReport();
                key = new StudentKey();
                key.setClasss("V");
                key.setSection("B");
                key.setRollid(15);
                studentReport.setKey(key);
                studentReport.setGrade("AA");
                studentReport.setSemester("1St");
                studentReport.setClassAttended(85);
                studentReports[2] = studentReport;

                studentReport = new StudentReport();
                key = new StudentKey();
                key.setClasss("VI");
                key.setSection("A");
                key.setRollid(2);
                studentReport.setKey(key);
                studentReport.setGrade("A+");
                studentReport.setSemester("1St");
                studentReport.setClassAttended(70);
                studentReports[3] = studentReport;

                studentReport = new StudentReport();
                key = new StudentKey();
                key.setClasss("V");
                key.setSection("A");
                key.setRollid(15);
                studentReport.setKey(key);
                studentReport.setGrade("AA");
                studentReport.setSemester("2Nd");
                studentReport.setClassAttended(85);
                studentReports[4] = studentReport;
                return studentReports;
        }

        private static Student[] getStudents() {
                Student[] students = new Student[3];

                Student student = new Student();
                StudentKey key = new StudentKey();
                key.setClasss("V");
                key.setSection("A");
                key.setRollid(15);
                student.setKey(key);
                student.setName("Deepak");
                student.setTitle("Saxana");
                students[0] = student;

                student = new Student();
                key = new StudentKey();
                key.setClasss("VI");
                key.setSection("A");
                key.setRollid(2);
                student.setKey(key);
                student.setName("Robert");
                student.setTitle("Paul");
                students[1] = student;

                student = new Student();
                key = new StudentKey();
                key.setClasss("V");
                key.setSection("B");
                key.setRollid(15);
                student.setKey(key);
                student.setName("Danny");
                student.setTitle("Moris");
                students[2] = student;
                return students;
        }
}
