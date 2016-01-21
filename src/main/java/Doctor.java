import java.util.List;
import java.util.TreeMap;
import org.sql2o.*;

public class Doctor{
  private String firstName;
  private String lastName;
  private int id;
  private int specialtyId;

  public Doctor(String firstName, String lastName, int specialtyId){
    this.firstName = firstName;
    this.lastName = lastName;
    this.specialtyId = specialtyId;
  }

  public String getFirstName(){
    return firstName;
  }
  public String getLastName(){
    return lastName;
  }
  public String getName(){
    return firstName + " " + lastName;
  }
  public int getId(){
    return id;
  }
  public int getSpecialtyId(){
    return specialtyId;
  }

  @Override
  public boolean equals(Object otherDoctor){
    if (!(otherDoctor instanceof Doctor)){
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return (this.getName().equals(newDoctor.getName())) &&
             (this.getSpecialtyId() == newDoctor.getSpecialtyId());
    }
  }

  public void save(){
    String sql = "INSERT INTO doctors (first_name, last_name, specialty_id) VALUES (:firstName, :lastName, :id)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("firstName", firstName)
        .addParameter("lastName", lastName)
        .addParameter("id", specialtyId)
        .executeUpdate()
        .getKey();
    }
  }

  public List<Patient> getAllPatients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, first_name AS firstName, last_name AS lastName, dob AS birthday, doctor_id AS doctorId FROM patients WHERE doctor_id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Patient.class);
    }
  }

  public static Doctor find(int id){
    String sql = "SELECT id, first_name AS firstName, last_name AS lastName, specialty_id AS specialtyId FROM doctors WHERE id = :id";
    try(Connection con = DB.sql2o.open()){
      Doctor doctor = (Doctor) con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Doctor.class);
      return doctor;
    }
  }

  public List<Doctor> getAllDoctors() {

  }

  public List<Doctor> getAllDoctors(boolean alphabetical) {

  }

  public int getPatientCountForDoctor() {

  }

  public static List<> doctorPatientCount() {
    String sql = "SELECT doctors.last_name, count(patients.id) FROM patients INNER JOIN doctors ON patients.doctor_id = doctors.id GROUP BY doctors.last_name ORDER BY doctors.last_name;";
    try(Connection con = DB.sql2o.open()){
      TreeMap<String, Integer> results = con.createQuery(sql)
        .executeAndFetch(TreeMap.class);
      return results;
    }
  }
}
