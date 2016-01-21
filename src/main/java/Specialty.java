import java.util.List;
import org.sql2o.*;

public class Specialty{
  private String specialty;
  private int id;

  public Specialty (String specialty){
    this.specialty = specialty;
  }

  public String getSpecialty(){
    return specialty;
  }

  public int getId(){
    return id;
  }

  public void save(){
    String sql = "INSERT INTO specialties (name) VALUES (:name)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", specialty)
        .executeUpdate()
        .getKey();
    }
  }

  public static Specialty find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name AS specialty FROM specialties WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Specialty.class);
    }
  }

  public List<Doctor> getAllDoctors(){
    String sql = "SELECT doctors.id, first_name AS firstName, last_name AS lastName, specialty_id AS specialtyId FROM doctors INNER JOIN specialties ON doctors.specialty_id = specialties.id WHERE specialties.name = :name";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
      .addParameter("name", this.getSpecialty())
      .executeAndFetch(Doctor.class);
    }
  }

}
