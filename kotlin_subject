// Student 클래스, name과 grades 속성을 가짐
// name: 학생의 이름을 저장하는 문자열 타입의 속성입니다.
// grades: 학생의 과목별 학점을 저장하는 문자열 리스트 타입의 속성입니다.
class Student(val name: String, val grades: List<String>) {
    // rank: 학생의 등수를 저장하는 정수 타입의 속성입니다.
    var rank: Int = 0

    // 문자열 학점을 숫자로 변환하는 convertToFloat 함수
    // convertToFloat("A+")를 호출하면 4.3f를 반환
    private fun convertToFloat(score: String): Float {
        return when (score) {
            "A+" -> 4.3f
            "A0" -> 4.0f
            "A-" -> 3.7f
            "B+" -> 3.3f
            "B0" -> 3.0f
            "B-" -> 2.7f
            "C+" -> 2.3f
            "C0" -> 2.0f
            "C-" -> 1.7f
            "D+" -> 1.3f
            "D0" -> 1.0f
            "D-" -> 0.7f
            else -> 0.0f
        }
    }

    // 입력 받은 학점 리스트를 평균 점수로 계산
    fun avgGrade(): Float {
        // 학점 리스트에 각 학점을 convertToFloat 함수를 사용하여 숫자로 변환한 후,
        // map 함수를 사용하여 변환된 숫자 값들을 리스트로 생성 후 sum() 함수로 총합 계산
        val total = grades.map { convertToFloat(it) }.sum()
        // 총합을 학점 리스트의 크기로 나눈 값을 반환(학점 리스트는 성적 5개가 있음)
        return total / grades.size
    }
}

// 등수 계산 calRank 함수, 매개변수로 학생목록을 받는다.
// 조건문으로 각 학생을 순회하며 등수를 계산하는데
// 현재 학생보다 점수가 높은 학생이 있을 때 마다 순위를 증가시킨다
// 학생의 등수에 계산된 등수를 할당한다.
fun calRank(students: List<Student>) {
    for (student in students) {
        var rank = 1

        for (otherStudent in students) {
            if (otherStudent.avgGrade() > student.avgGrade()) {
                rank++
            }
        }

        student.rank = rank
    }
}

// mutableListOf : 빈 리스트를 생성하고 리스트 // 저장될 요소의 타입을 Student 클래스로 타입 지정
fun main() {
    // Student 객체를 저장할 수 있는 빈 리스트를 생성하는 부분
    val students = mutableListOf<Student>()

    students.add(Student("홍길동", listOf("B+", "A-", "D0", "B0", "A+")))
    students.add(Student("홍지우", listOf("D0", "C-", "C0", "B0", "A-")))
    students.add(Student("홍민준", listOf("D+", "A-", "A0", "B0", "A+")))
    students.add(Student("홍서윤", listOf("C+", "B-", "C0", "C-", "A0")))
    students.add(Student("홍서준", listOf("B-", "B+", "C0", "B+", "A+")))
    students.add(Student("홍서연", listOf("B-", "A-", "A0", "B+", "A-")))
    students.add(Student("홍서현", listOf("A+", "A-", "B0", "B0", "A0")))
    students.add(Student("홍예준", listOf("B-", "D-", "C0", "A+", "A+")))
    students.add(Student("홍도윤", listOf("B+", "B-", "A0", "B0", "C-")))
    students.add(Student("홍현진", listOf("C+", "A-", "C0", "B0", "A+")))
    // 함수 호출하여 Students 등수 계산
    calRank(students)

    for (student in students) {
        println("학생 ${student.name}: 평균 점수=${student.avgGrade()}, 등수=${student.rank}")
    }
}
