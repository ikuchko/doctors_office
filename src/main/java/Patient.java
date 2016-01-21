import java.util.List;
import org.sql2o.*;

public class Patient {
  private String firstName;
  private String lastName;
  private String birthday;
  private int id;
  private int doctorId;

  public Patient(String first, String last, String dob) {
    firstName = first;
    lastName = last;
    birthday = dob;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getName() {
    return firstName + " " + lastName;
  }

  public String getBirthDay() {
    return birthday;
  }

  public int getDoctorId() {
    return doctorId;
  }

  @Override
  public boolean equals(Object otherPatient){
    if (!(otherPatient instanceof Patient)){
      return false;
    } else {
      Patient newPatient = (Patient) otherPatient;
      return (this.getName().equals(newPatient.getName()));
          //  (this.getBirthDay().equals(newPatient.getBirthDay()));
    }
  }

  public void save(){
    String sql = "INSERT INTO patients (first_name, last_name, dob) VALUES (:first, :last, TO_DATE(:birthday, 'yyyy-mm-dd'))";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first", firstName)
        .addParameter("last", lastName)
        .addParameter("birthday", birthday)
        .executeUpdate()
        .getKey();
    }
  }

  public void assignDoctor(int id){
    String sql = "UPDATE patients SET doctor_id = :id";
    doctorId = id;
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public Doctor getDoctor(){
    return Doctor.find(doctorId);
  }

}
