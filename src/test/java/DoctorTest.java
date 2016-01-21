import java.util.Arrays;
import java.util.TreeMap;

import org.junit.*;
import static org.junit.Assert.*;

public class DoctorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void doctorHasNameAndSpecialty() {
    Specialty newSpecialty = new Specialty("Pediatrician");
    newSpecialty.save();
    Doctor newDoctor = new Doctor("John", "Smith", newSpecialty.getId());
    newDoctor.save();
    assertEquals("John Smith", newDoctor.getName());
    assertEquals(newSpecialty.getId(), newDoctor.getSpecialtyId());
    assertEquals("Pediatrician", Specialty.find(newDoctor
      .getSpecialtyId())
      .getSpecialty());
  }

  @Test
  public void patientHasNameAndBirthday() {
    Patient newPatient = new Patient("Roy", "Petrov", "1888-01-01");
    newPatient.save();
    assertEquals("Roy Petrov", newPatient.getName());
    assertEquals("1888-01-01", newPatient.getBirthDay());
  }

  @Test
  public void assignDoctor_assignsDoctorToPatient() {
    Patient newPatient = new Patient("Roy", "Petrov", "1888-01-01");
    newPatient.save();
    Specialty newSpecialty = new Specialty("Pediatrician");
    newSpecialty.save();
    Doctor newDoctor = new Doctor("John", "Smith", newSpecialty.getId());
    newDoctor.save();
    newPatient.assignDoctor(newDoctor.getId());
    assertTrue(newDoctor.equals(newPatient.getDoctor()));
  }

  @Test
  public void doctor_returnPatientsList(){
    Specialty newSpecialty = new Specialty("Pediatrician");
    newSpecialty.save();
    Doctor newDoctor = new Doctor("John", "Smith", newSpecialty.getId());
    newDoctor.save();
    Patient firstPatient = new Patient("Roys", "Petrov", "1888-01-01");
    firstPatient.save();
    firstPatient.assignDoctor(newDoctor.getId());
    Patient secondPatient = new Patient("Roy", "Petrov", "1888-01-01");
    secondPatient.save();
    secondPatient.assignDoctor(newDoctor.getId());
    Patient[] patients = new Patient[] {firstPatient, secondPatient};
    assertTrue(newDoctor.getAllPatients().containsAll(Arrays.asList(patients)));
  }

  @Test
  public void doctorPatientCount_returnsCorrectDoctorPatientCounts() {
    Specialty newSpecialty = new Specialty("Pediatrician");
    newSpecialty.save();
    Doctor firstDoctor = new Doctor("John", "Smith", newSpecialty.getId());
    firstDoctor.save();
    Doctor secondDoctor = new Doctor("Jane", "Aaron", newSpecialty.getId());
    secondDoctor.save();
    Patient firstPatient = new Patient("Roys", "Petrov", "1888-01-01");
    firstPatient.save();
    firstPatient.assignDoctor(firstDoctor.getId());
    Patient secondPatient = new Patient("Roy", "Petrov", "1888-01-01");
    secondPatient.save();
    secondPatient.assignDoctor(firstDoctor.getId());
    Patient thirdPatient = new Patient("Anna", "King", "1888-01-01");
    thirdPatient.save();
    thirdPatient.assignDoctor(secondDoctor.getId());
    TreeMap<String, int> results = Doctor.doctorPatientCount();
    assertEquals(2, results.get(firstDoctor.getLastName()));
    assertEquals(1, results.get(secondDoctor.getLastName()));
  }

}
