import java.io.*;

// 1. Student class implementing Serializable
class Student implements Serializable {

    // 4. serialVersionUID for version control during deserialization
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int age;

    // 5. Sensitive field marked transient (won't be serialized)
    private transient String password;

    // Constructor
    public Student(int id, String name, int age, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.password = password;
    }

    // Validation after deserialization
    public boolean isValid() {
        return id > 0 && age > 0 && name != null && !name.isEmpty();
    }

    @Override
    public String toString() {
        return "Student{id=" + id +
               ", name='" + name + '\'' +
               ", age=" + age +
               ", password=" + password + '}';
    }
}

public class StudentSerializationDemo {

    public static void main(String[] args) {

        Student student = new Student(101, "Aishwarya", 21, "secret@123");

        // File path for persistence
        String fileName = "student_data.ser";

        // 2. Store object state using ObjectOutputStream
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            oos.writeObject(student);
            System.out.println(" Student object serialized successfully.");

        } catch (IOException e) {
            System.err.println(" Serialization failed: " + e.getMessage());
            e.printStackTrace();
        }

        // 3. Retrieve object using ObjectInputStream
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            Student restoredStudent = (Student) ois.readObject();

            // 6. Validate restored object
            if (restoredStudent.isValid()) {
                System.out.println(" Deserialized Student Object:");
                System.out.println(restoredStudent);
            } else {
                System.err.println(" Invalid student data after deserialization.");
            }

        }
        // 7. Handle ClassNotFoundException
        catch (ClassNotFoundException e) {
            System.err.println(" Student class not found during deserialization.");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println(" Deserialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
